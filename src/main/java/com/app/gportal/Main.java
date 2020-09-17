package com.app.gportal;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigDecimal a = new BigDecimal(10).setScale(2);
		BigDecimal b = new BigDecimal(20).setScale(2);
		System.out.println(a.subtract(b));
	}

}
