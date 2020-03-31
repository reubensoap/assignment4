package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CDAccount extends BankAccount {
	public CDOffering offering = new CDOffering(1, 0.01);

    public CDAccount(CDOffering offering, double openingBalance){
        super(openingBalance, offering.getInterestRate());
        this.offering = offering;
    }
    
    public CDAccount(int accountNum, double balance, double interest
    		, java.util.Date accountOpenedOn, int term) {
    	super(accountNum, balance, interest, accountOpenedOn);
    	this.offering.setTerm(term);
    	this.offering.setInterestRate(interest);
    }

    public int getTerm(){
        return this.offering.getTerm();
    }
    
    // used for creating objects from file
    static CDAccount readFromString(String accountData) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    	String array1[] = accountData.split(",");
    	int fAccount = Integer.parseInt(array1[0]);
    	double fBalance = Double.parseDouble(array1[1]);
    	double fInterest = Double.parseDouble(array1[2]);
    	Date fDate = dateFormatter.parse(array1[3]);
    	int fTerm = Integer.parseInt(array1[4]);
    	
    	CDAccount cda = new CDAccount(fAccount, fBalance
    			, fInterest, fDate, fTerm);
    	return cda;
    	} catch (ParseException e) {
    		return null;
    	}
    }
    
    // override from BankAccount withdraw , deposit , futureValue
    public boolean withdraw(double amount){
        return false;
    }
	
	public boolean deposit(double amount) {
		if(this.getBalance() == 0) {
			try {
				if(amount <= 0){
		        	throw new NegativeAmountException("Please Deposit a positive amount.");
		        }
			} catch (NegativeAmountException e) {
				System.out.println(e);
				return false;
			}
	    
	        try {
	        	if(amount > 1000) {
		        	throw new ExceedsFraudSuspicionLimitException("Transaction requires review, thanks for your patience.");
	        	}
	        } catch(ExceedsFraudSuspicionLimitException e) {
	        	java.util.Date fDate = new java.util.Date();
		        DepositTransaction newTrans = new DepositTransaction(-1, this.getAccountNumber(), amount, fDate);
		        MeritBank.processTransaction(newTrans);
	        	System.out.println(e);
	        	FraudQueue.addTransaction(newTrans);
	        	return true;
	        }
	        java.util.Date fDate = new java.util.Date();
	        DepositTransaction newTrans = new DepositTransaction(-1, this.getAccountNumber(), amount, fDate);
	        MeritBank.processTransaction(newTrans);
	        return true;
		}
        return false;
    }
	
	public double futureValue() {
        double fv = MeritBank.recursiveFutureValue(this.getBalance(), this.offering.getTerm(), this.getInterestRate());
        return fv;
	}
	@Override
	public String writeToString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    	return this.getAccountNumber() + "," + this.getBalance() + "," + this.getInterestRate()
    			+ "," + dateFormatter.format(this.getOpenedOn()) + "," + this.getTerm();
    }
	
	public String toString(){
        DecimalFormat format = new DecimalFormat("##.00");
        return "\nCDAccount Balance: $" + format.format(this.getBalance()) + "\n"
                + "CDAccount Interest Rate: " + this.getInterestRate() + "\n"
                + "CDAccount Balance in 3 years: $" + format.format(this.futureValue(3));
    }
}
