package com.lonsec.utility;

import java.io.BufferedReader;
import java.io.File;

import org.junit.Test;

import com.lonsec.constants.FixedConstants;
import com.lonsec.exceptions.BusinessException;
import com.lonsec.util.FileUtils;

import junit.framework.TestCase;

public class TestFileUtility extends TestCase{

	@Test
	public static void testCreateFile() {
		BufferedReader br = null;
		try {
			br = FileUtils.getFileBufferReaderForFile(new File(FixedConstants.BENCH_MARK));
		} catch (BusinessException e) {
			fail("FileUtil failed to create BufferedReader");
		}
		assertNotNull(br);
		
	}

}
