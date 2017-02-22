package com.lonsec.services;

import org.junit.Test;

import com.lonsec.constants.FixedConstants;
import com.lonsec.util.PropertiesCacheUtil;

import junit.framework.TestCase;

public class TestUtility extends TestCase{

	@Test
	public void testpropertyLookup() {
		String pr ="";
		try {
			pr = PropertiesCacheUtil.getInstance().getProperty(FixedConstants.UNDERPERFORMED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("UnderPerformed", pr);
		
	}

}
