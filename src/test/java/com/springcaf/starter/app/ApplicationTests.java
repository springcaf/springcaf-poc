package com.springcaf.starter.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTests {

	@Test
    public void baseTest() throws Exception {
		assert("A".equals("A"));
    }

}
