package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferTransaction extends Transaction {
	
	public TransferTransaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		super(targetNum, sourceNum, amountNum, date);
	}
	
	
}
