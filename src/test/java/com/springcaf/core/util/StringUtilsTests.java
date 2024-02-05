package com.springcaf.core.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootConfiguration
@SpringBootTest
public class StringUtilsTests {

	@Test
	public void uuidGenTest() throws Exception
	{
		String result = StringUtils.generateUUID();
		assert(result != null && result.length() == 36);
	}
}
