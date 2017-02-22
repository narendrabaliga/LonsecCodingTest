package com.lonsec.interfaces;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.lonsec.domain.FinanceInstrument;
import com.lonsec.domain.InstrumentReturns;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.exceptions.RecordNotFoundException;
import com.lonsec.exceptions.TechnicalException;

public interface FinanceInstrumentFacade {

	/**
	 * lookup fund or Benchmark through file Name and code
	 */
	public FinanceInstrument lookUpFinancialInstrumentByCode(File file, String code) throws BusinessException;

	/**
	 * Looks up returns of Fund or BenchMark by fileName, further filtering it out on date and the code.
	 * @param fileName
	 * @param date
	 * @param bmCode
	 * @return
	 * @throws BusinessException
	 * @throws RecordNotFoundException
	 */
	public InstrumentReturns lookUpReturnsByDateAndCode(File fileName, LocalDate date, String code) throws BusinessException, RecordNotFoundException;
	
	/**  
	 * Returns all the records in the file in the parameter fileName
	 * @param fileName
	 * @return
	 * @throws BusinessException
	 * @throws RecordNotFoundException
	 */
	public List<InstrumentReturns> getInstrumentReturnsByFileName(File fileName) throws BusinessException, RecordNotFoundException;

	/**
	 * rank the fund
	 */
	public List<InstrumentReturns> rank(File fundReturnseries) throws BusinessException;

}
