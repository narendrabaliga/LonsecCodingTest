package com.lonsec.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.lonsec.domain.FinanceInstrument;
import com.lonsec.domain.InstrumentReturns;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.exceptions.MultipleRecordsFound;
import com.lonsec.exceptions.RecordNotFoundException;
import com.lonsec.exceptions.TechnicalException;
import com.lonsec.interfaces.FinanceInstrumentFacade;
import com.lonsec.util.FileUtils;
import com.sun.istack.internal.logging.Logger;

public class FinanceInstrumentImpl implements FinanceInstrumentFacade {
	
	int rank = 0;
	double currReturns = 0;
	double prevReturns = 0;
	Logger logger = Logger.getLogger(FinanceInstrumentImpl.class);

	@Override
	public FinanceInstrument lookUpFinancialInstrumentByCode(File file, String code) throws BusinessException {
		
		logger.log(Level.INFO, "Searching financial instrument from file : "+file+" and code"+ code);

		List<FinanceInstrument> financeInstrument = null;
		BufferedReader bufferedReader = null;

		try {

			bufferedReader = FileUtils.getFileBufferReaderForFile(file);
			if(null != bufferedReader) {
				
				financeInstrument = bufferedReader.lines().substream(1).map(toFinanceInstrument)
					.filter(fund -> fund.getCode().equals(code)).limit(1).collect(Collectors.toList());

				if (financeInstrument.isEmpty()) {
					logger.log(Level.INFO, "Bench mark of fund could not be found");
					throw new RecordNotFoundException("100",
							"Fund or Bench mark with code " + code + " does not exist in the csv file.");
				} else if (financeInstrument.size() > 1) {
					logger.log(Level.INFO, "Multiple Bench mark or fund exists in the file");
					throw new MultipleRecordsFound("101",
							"Multiple funds or Bench mark with the code " + code + " exists in the csv file.");
				}
			}
	
				bufferedReader.close();
		} catch (IOException ioException) {
			throw new BusinessException("102", ioException.getMessage());
		}

		return financeInstrument.get(0);
	}

	@Override
	public InstrumentReturns lookUpReturnsByDateAndCode(File fileName, LocalDate date, String code)
			throws BusinessException {

		List<InstrumentReturns> instrumentReturns;
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = FileUtils.getFileBufferReaderForFile(fileName);
			instrumentReturns = bufferedReader.lines().substream(1).map(toReturns)
					.filter(returns -> returns.getMonthEnd().equals(date))
					.filter(returns -> returns.getCode().equals(code)).collect(Collectors.toList());

			if (instrumentReturns.isEmpty()) {
				throw new RecordNotFoundException("200",
						"Fund or Bench mark with code " + date + " does not exist in the csv file.");
			}
			bufferedReader.close();
		} catch (IOException ioException) {
			throw new TechnicalException("400", ioException.getMessage());
		}

		return instrumentReturns.get(0);

	}

	@Override
	public List<InstrumentReturns> getInstrumentReturnsByFileName(File fileName)
			throws BusinessException, RecordNotFoundException {

		List<InstrumentReturns> instrumentReturns;
		BufferedReader bufferedReader = FileUtils.getFileBufferReaderForFile(fileName);

		try {
			instrumentReturns = bufferedReader.lines().substream(1).map(toReturns).collect(Collectors.toList());

			if (instrumentReturns.isEmpty()) {
				throw new RecordNotFoundException("200", "file " + fileName + " does not have any records");
			}
			bufferedReader.close();
		} catch (IOException ioException) {
			throw new TechnicalException("400", ioException.getMessage());
		}
		return instrumentReturns;

	}

	@Override
	public List<InstrumentReturns> rank(File fileName) throws BusinessException {
		
		BufferedReader br = FileUtils.getFileBufferReaderForFile(fileName);
		List<InstrumentReturns> returns = br.lines().substream(1)
				.map(toReturns)
				.collect(Collectors.toList());
		
		Map<LocalDate,List<InstrumentReturns>> sortedMap = returns.stream()
				.sorted()
				.collect(Collectors.groupingBy(InstrumentReturns::getMonthEnd));
		
		Iterator<LocalDate> sortedIterator = sortedMap.keySet().iterator();
		
		List<InstrumentReturns> rankedReturns = new ArrayList<InstrumentReturns>();
		
		while(sortedIterator.hasNext()) {
			rank = 0;  currReturns = prevReturns = 0;
			List<InstrumentReturns> listInstrument = sortedMap.get(sortedIterator.next());
			
			listInstrument.forEach(ind -> {
				currReturns = ind.getReturns();
				if(currReturns == prevReturns) {
					ind.setRank(rank);
				}else {
					ind.setRank(++rank);
				}
				prevReturns = currReturns;
				rankedReturns.add(ind);
				System.out.println(ind.getMonthEnd()+"  "+ind.getCode()+" "+ ind.getReturns()+" "+ind.getRank());
				
			});			
		}
		
		return rankedReturns;
	}

	private static Function<String, FinanceInstrument> toFinanceInstrument = (record) -> {
		String[] fund = record.split(",");
		return new FinanceInstrument(fund[0], fund[1].trim(), fund[2]);
	};

	/**
	 * private Function to extract the returns for Fund or BenchMark
	 **/

	private static Function<String, InstrumentReturns> toReturns = (record) -> {
		String[] returns = record.split(",");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return new InstrumentReturns(returns[0], LocalDate.parse(returns[1], formatter),
				Float.valueOf(returns[2]).floatValue());
	};

}
