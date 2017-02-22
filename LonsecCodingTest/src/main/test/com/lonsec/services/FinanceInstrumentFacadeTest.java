/**
 * 
 */
package com.lonsec.services;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.lonsec.businessservice.FIBusinessService;
import com.lonsec.constants.FixedConstants;
import com.lonsec.domain.FinanceInstrument;
import com.lonsec.domain.InstrumentReturns;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.exceptions.TechnicalException;
import com.lonsec.impl.FinanceInstrumentImpl;
import com.lonsec.interfaces.FinanceInstrumentFacade;

import junit.framework.TestCase;

/**
 * @author naren
 *
 */
public class FinanceInstrumentFacadeTest extends TestCase {
	
	@Test
	public static void testLookUpExistingFund() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		FinanceInstrument fi = null;
		try {
			fi = fiFacade.lookUpFinancialInstrumentByCode(new File(FixedConstants.FUNDS), "MF-1-4220");
		} catch (TechnicalException | BusinessException e) {
			fail("Test Failed");
		}
		assertNotNull(fi);
		assertEquals(fi.getName(), "Goldman Sachs Emerging Leaders Fund");
		
	}
	
	@Test
	public static void testLookUpNonExistingFund() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		FinanceInstrument fi = null;
		try {
			fi = fiFacade.lookUpFinancialInstrumentByCode(new File(FixedConstants.FUNDS), "NON-Existing");
		} catch (TechnicalException | BusinessException e) {
			assertNull(fi);
		}
		
	}
	
	public static void testLookupDuplicateFund() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		FinanceInstrument fi = null;
		try {
			fi = fiFacade.lookUpFinancialInstrumentByCode(new File(FixedConstants.FUNDS), "MF-1-4220");
		} catch (TechnicalException | BusinessException e) {
			assertNull(fi);
		}
		
	}
	
	@Test
	public static void testLookUpBenchMarkReturns() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		InstrumentReturns instrumentReturns = null;
		try {
			instrumentReturns = fiFacade.lookUpReturnsByDateAndCode(new File(FixedConstants.BENCHMARK_RETURNS), LocalDate.of(2016, 05, 31),"BM-672");
		} catch (TechnicalException | BusinessException e) {
			
		}
		assertEquals(4.756892149, instrumentReturns.getReturns());
	}
	
	@Test
	public static void testLookUpFundsReturns() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		InstrumentReturns instrumentReturns = null;
		try {
			instrumentReturns = fiFacade.lookUpReturnsByDateAndCode(new File(FixedConstants.FUNDS_RETURNS), LocalDate.of(2016, 06, 30),"MF-1-4220");
		} catch (TechnicalException | BusinessException e) {
			
		}
		assertEquals(-4.229084292, instrumentReturns.getReturns());
	}
	
	
	

	@Test
	public static void testLookUpFinancialInstrumentReturnsByFilter() {
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		List<InstrumentReturns> instrumentReturns = null;
		try {
			instrumentReturns = fiFacade.getInstrumentReturnsByFileName(new File("FundReturnSeries.csv"));
		} catch (TechnicalException | BusinessException e) {
			
		}
		assertEquals(36, instrumentReturns.size());
	}
	
	@Test
	public static void testRankFinancialInstrument() {
		
		FinanceInstrumentFacade fiFacade = new FinanceInstrumentImpl();
		List<InstrumentReturns> instrumentReturns = null;
		try {
			instrumentReturns = fiFacade.rank(new File(FixedConstants.FUNDS_RETURNS));
		} catch (TechnicalException | BusinessException e) {
			
		}
		assertEquals(1,instrumentReturns.get(0).getRank());
		assertEquals(2,instrumentReturns.get(1).getRank());
		assertEquals(3,instrumentReturns.get(2).getRank());
		assertEquals(4,instrumentReturns.get(3).getRank());
		assertEquals(5,instrumentReturns.get(4).getRank());
		assertEquals(6,instrumentReturns.get(5).getRank());
	}
	
	@Test
	public static void testRankGenerateInstrument() {
		
		FIBusinessService fiFacade = new FIBusinessService();
		try {
			fiFacade.generatePerformanceReport(new File(FixedConstants.FUNDS_RETURNS));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
