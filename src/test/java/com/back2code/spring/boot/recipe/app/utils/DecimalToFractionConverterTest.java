package com.back2code.spring.boot.recipe.app.utils;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecimalToFractionConverterTest {

	@Test
	void testConvertDecimalToFraction() {
		//given
		 BigDecimal decimalValue = new BigDecimal(4.0000);
		 
		//when
		String fractionValue = DecimalToFractionConverter.convert(decimalValue); 
		System.out.println("decimalValue["+decimalValue+"] --> fractionValue["+fractionValue+"]");
		
		//then
		
		assertEquals("3 1/4", fractionValue);
	}

}
