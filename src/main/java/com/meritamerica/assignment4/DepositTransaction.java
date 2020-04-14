package com.meritamerica.assignment4;

public class DepositTransaction extends Transaction {
	
	// used for creation the specific object of Deposit Transaction
	
	public DepositTransaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		super(targetNum, sourceNum, amountNum, date);
	}
	
}
