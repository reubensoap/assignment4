package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckingAccount extends BankAccount {
	//Variables
    
    //Constructors
    public CheckingAccount(){
        super(0.00, 0.0001);
    }

    public CheckingAccount(double checkingB){
        super(checkingB, 0.0001);
    }
    
    public CheckingAccount(long accountNumber, double balance, double interestRate
    		, java.util.Date accountOpenedOn) {
    	super(accountNumber, balance, interestRate, accountOpenedOn);
    }
    
    //Methods

    // set amount of years.
    public String toString(){
        DecimalFormat format = new DecimalFormat("##.00");
        return "\nChecking Account Balance: $" + format.format(this.getBalance()) + "\n"
                + "Checking Account Interest Rate: " + this.getInterestRate() + "\n"
                + "Checking Account Balance in 3 years: $" + format.format(this.futureValue(3));
    }
    
    // used for creating objects from file
    static CheckingAccount readFromString(String accountData) {
    	try {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String array1[] = accountData.split(",");
    	int fAccount = Integer.parseInt(array1[0]);
    	double fBalance = Double.parseDouble(array1[1]);
    	double fInterest = Double.parseDouble(array1[2]);
    	Date fDate = dateFormatter.parse(array1[3]);
    	
    	CheckingAccount checking = new CheckingAccount(fAccount, fBalance
    			, fInterest, fDate);
    	return checking;
    	} catch(ParseException e) {
    		return null;
    	}
}
}
