package com.lonsec.client;

import java.io.File;
import java.util.logging.Level;

import com.lonsec.businessservice.FIBusinessService;
import com.lonsec.constants.FixedConstants;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.impl.FinanceInstrumentImpl;
import com.sun.istack.internal.logging.Logger;

public class LonsecClient {
	
	static Logger logger = Logger.getLogger(FinanceInstrumentImpl.class);

	public static void main(String[] args) {
		FIBusinessService businessService = new FIBusinessService();
		logger.info("Generating performance Report for Funds");
		try {
			businessService.generatePerformanceReport(new File(FixedConstants.FUNDS_RETURNS));
		} catch (BusinessException e) {
			logger.log(Level.INFO, "Report Generation failed");
		}
	}

}
