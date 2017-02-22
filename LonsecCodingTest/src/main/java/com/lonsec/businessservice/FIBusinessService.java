package com.lonsec.businessservice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.partitioningBy;
import java.util.stream.Collectors;

import java.util.logging.Level;
import com.sun.istack.internal.logging.Logger;

import com.lonsec.constants.FixedConstants;
import com.lonsec.domain.FinanceInstrument;
import com.lonsec.domain.InstrumentReturns;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.exceptions.RecordNotFoundException;
import com.lonsec.impl.FinanceInstrumentImpl;
import com.lonsec.interfaces.FinanceInstrumentFacade;
import com.lonsec.report.entities.FundReport;
import com.lonsec.util.PropertiesCacheUtil;


public class FIBusinessService {
	
	private static final String COMMA_DELIMITER = ",";
	
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    private static final String FILE_HEADER = "FundCode,FundName,Date,Excess,OutPerformance,Return,Rank";

	FinanceInstrumentFacade instrumentFacade;
	
	Logger logger = Logger.getLogger(FIBusinessService.class);

	/**
	 * calculate Excess subtract the benchMark return from the fund return to
	 * get the Excess return Take the excess return result and lookup the out
	 * performance text
	 * 
	 * @throws BusinessException
	 * @throws RecordNotFoundException
	 */
	public void generatePerformanceReport(File fundReturnSeries) throws BusinessException {

		logger.info("Generating performance report from file :"+ fundReturnSeries);
		instrumentFacade = new FinanceInstrumentImpl();
		FinanceInstrument fund = null;
		List<FundReport> fundReportList = new ArrayList<FundReport>();
		InstrumentReturns benchMarkReturns = null;
		String outcome = " ";
		

		List<InstrumentReturns> fundReturns = instrumentFacade.getInstrumentReturnsByFileName(fundReturnSeries);

		Iterator<InstrumentReturns> fiIterator = fundReturns.iterator();
		
		List<InstrumentReturns> rankedFunds = instrumentFacade.rank(new File(FixedConstants.FUNDS_RETURNS));
		
		// iterate through the fund returns file.
		while (fiIterator.hasNext()) {
			
			InstrumentReturns fundReturn = fiIterator.next();
			
			FundReport report = new FundReport();
			//look up the fund by fund file name and the code
			fund = instrumentFacade.lookUpFinancialInstrumentByCode(new File(FixedConstants.FUNDS),
					fundReturn.getCode());
			
			report.setCode(fund.getCode());
			report.setDate(fundReturn.getMonthEnd());
			report.setFundName(fund.getName());
			report.setReturns(fundReturn.getReturns());
			
			//look up the corresponding bench mark  by date and code
			benchMarkReturns = instrumentFacade.lookUpReturnsByDateAndCode(
					new File(FixedConstants.BENCHMARK_RETURNS), fundReturn.getMonthEnd(),fund.getBmCode());
			
			//calculate excess upto 2 decimal
			BigDecimal excess = new BigDecimal(fundReturn.getReturns() - benchMarkReturns.getReturns());
			BigDecimal excessRoundOff = excess.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			
			report.setExcess(excessRoundOff.floatValue());
			
			if(excessRoundOff.doubleValue() < -1) {
				outcome = FixedConstants.UNDERPERFORMED;
			}else if (excessRoundOff.doubleValue() > 1) {
				outcome = FixedConstants.OUTPERFORMED;
				
			}
			report.setPerformance(outcome);
			List<InstrumentReturns> relatedRankedFund = rankedFunds.stream()
				.filter(rank -> rank.getMonthEnd().equals(fundReturn.getMonthEnd()))
				.filter(rank -> rank.getCode().equals(fundReturn.getCode()))
				.collect(Collectors.toList());
			
			report.setRank(relatedRankedFund.get(0).getRank());
			fundReportList.add(report);
			
			logger.info(report.toString());
		}
		
		Map<LocalDate,List<FundReport>> sortedReport = fundReportList.stream().sorted(comparing(FundReport::getRank))
		.collect(Collectors.groupingBy(FundReport::getDate));
		
		List<FundReport> listForReport = new ArrayList<>();
		
		sortedReport.forEach((k,v) -> {
			listForReport.addAll(v);
		});
		System.out.println(listForReport);

		createFile(FixedConstants.MONTHLY_REPORT_FILE,listForReport);
	}
	
	/**
	 * creates a csv file and inserts data into it as received in the input parameter
	 * @param 
	 * @
	 **/
	private boolean createFile(String fileName, List<FundReport> report) throws BusinessException{
		File file = new File("/resource/"+fileName);
		//FileWriter fileWriter = null;
		try {
		 FileWriter fileWriter = new FileWriter(file.getName());
		 fileWriter.append(FILE_HEADER.toString());
		 fileWriter.append(NEW_LINE_SEPARATOR);
		 report.stream().forEach(rep -> {
			 try {
			 fileWriter.append(String.valueOf(rep.getCode()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getFundName()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getDate()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getExcess()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getPerformance()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getReturns()));
			 fileWriter.append(COMMA_DELIMITER);
			 fileWriter.append(String.valueOf(rep.getRank()));
			 fileWriter.append(NEW_LINE_SEPARATOR);
			 }catch (IOException e) {
				 logger.info("There was an exception opening or writing on the report file"+ e.getMessage());
					 
		     } 
		 });
		 	 fileWriter.close();
		 }catch(IOException e) {
			 logger.info("There was an exception opening or writing on the report file"+ e.getMessage());
			 BusinessException businessException = new BusinessException(e.getMessage());
			 throw businessException; 
		 }
			 
		logger.info("Report generated in the resources folder ");
		return true;
		         
		
		
	}

	

}
