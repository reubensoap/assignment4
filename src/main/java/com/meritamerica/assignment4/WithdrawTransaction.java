package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction {
	
	public WithdrawTransaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		super(targetNum, sourceNum, amountNum, date);
	}
}
