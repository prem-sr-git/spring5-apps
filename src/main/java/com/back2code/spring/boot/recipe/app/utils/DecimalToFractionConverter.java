package com.back2code.spring.boot.recipe.app.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DecimalToFractionConverter {

	public static String convert(BigDecimal decimalNumber) {

		int numFromDecimal = 0, denomFromDecimal = 0;

		int intValue = decimalNumber.intValue();

		BigDecimal decimalValue = decimalNumber.remainder(BigDecimal.ONE);

		int decimalScale = decimalValue.scale();

		BigInteger decimalNum = decimalValue.movePointRight(decimalScale).abs().toBigInteger();

		boolean hasDecimalValue = decimalNum.compareTo(BigInteger.ZERO) > 0 ? true : false;

		if (hasDecimalValue) {
			int numerator = decimalValue.scaleByPowerOfTen(decimalScale).intValue();

			int denominator = BigDecimal.ONE.scaleByPowerOfTen(decimalScale).intValue();

			int gcd = getGcd(numerator, denominator);

			numFromDecimal = numerator / gcd;
			denomFromDecimal = denominator / gcd;

			if (numFromDecimal == denomFromDecimal) {
				intValue += 1;
			}

		}

		StringBuffer fractionVal = new StringBuffer();

		if (intValue > 0) {
			fractionVal.append(intValue);
		}

		if (hasDecimalValue) {
			fractionVal.append(" ").append(numFromDecimal).append("/").append(denomFromDecimal);
		}

		return fractionVal.toString();
	}

	private static int getGcd(int a, int b) {

		return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();

	}
}
