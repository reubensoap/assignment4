package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {
	//variables
	java.util.Date date;
	private double amount;
	private long sourceAccountNum = 0;
	private long targetAccountNum = 0;
	
	public Transaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		this.sourceAccountNum = sourceNum;
		this.targetAccountNum = targetNum;
		this.amount = amountNum;
		this.date = date;
	}
	
	public BankAccount getSourceAccount() {
		BankAccount sourceOb = MeritBank.getBankAccount(sourceAccountNum);
		return sourceOb;
	}
	
	public void setSourceAccount(long source) {
		this.sourceAccountNum = source;
	}
	
	public BankAccount getTargetAccount() {
		BankAccount targetOb = MeritBank.getBankAccount(targetAccountNum);
		return targetOb;
	}
	
	public void setTargetAccount(long target) {
		this.targetAccountNum = target;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getTargetAccountNum() {
		return this.targetAccountNum;
	}
	
	public long getSourceAccountNum() {
		return this.sourceAccountNum;
	}
	
	public java.util.Date getTransactionDate(){
		return this.date;
	}
	
	public void setTransactionDate(java.util.Date date) {
		this.date = date;
	}
	
	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    	return this.targetAccountNum + "," + this.sourceAccountNum + "," + this.amount
    			+ "," + dateFormatter.format(this.date);
	}
	
	public String writeToString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    	return this.targetAccountNum + "," + this.sourceAccountNum + "," + this.amount
    			+ "," + dateFormatter.format(this.date);
    }
	
	public static Transaction readFromString(String transactionDataString) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    	String array1[] = transactionDataString.split(",");
    	long targetAccountNum = Integer.parseInt(array1[0]);
    	long sourceAccountNum = Integer.parseInt(array1[1]);
    	double amountNum = Double.parseDouble(array1[2]);
    	Date fDate = dateFormatter.parse(array1[3]);
    	
    	if(targetAccountNum == -1) {
    		if(amountNum < 0) {
    			WithdrawTransaction newTrans = new WithdrawTransaction(targetAccountNum, sourceAccountNum,  amountNum, fDate);
    			return newTrans;
    		}
    		
    		DepositTransaction newTrans = new DepositTransaction(targetAccountNum, sourceAccountNum,  amountNum, fDate);
    		return newTrans;
    	}
    	
    	TransferTransaction newTrans = new TransferTransaction(targetAccountNum, sourceAccountNum,  amountNum, fDate);
    	return newTrans;
    	
    	} catch (ParseException e) {
    		return null;
    	}
	}
		
}
