package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

abstract class BankAccount {
	// Variables
		private double balance = 0;
		private double interestRate;
		private java.util.Date accountOpenedOn; 
		private long accountNumber;
		
		List<Transaction> list = new ArrayList<Transaction> ();
		
		//Constructors
		
		/* BankAccount constructors come with DepositTransaction transactions
		 *  transaction added to Transaction List
		 * 	-1 Target Source AccountNumbers are for Withdraw and Deposit methods
		 *  
		 */
		public BankAccount(double balance, double interestRate) {
			this.balance = balance;
			this.interestRate = interestRate;
			this.accountOpenedOn = new java.util.Date();
			this.accountNumber = MeritBank.getNextAccountNumber();
			DepositTransaction newTrans = new DepositTransaction(-1, this.accountNumber, this.balance, this.accountOpenedOn);
			this.addTransaction(newTrans);
		}
		
		public BankAccount(double balance, double interestRate
				, java.util.Date accountOpenedOn) {
			this.balance = balance;
			this.interestRate = interestRate;
			this.accountOpenedOn = accountOpenedOn;
			this.accountNumber = MeritBank.getNextAccountNumber();
			DepositTransaction newTrans = new DepositTransaction(-1, this.accountNumber, this.balance, this.accountOpenedOn);
			this.addTransaction(newTrans);
		}
		
		public BankAccount(long accountNumber, double balance, double interestRate
				, java.util.Date accountOpenedOn) {
			this.accountNumber = accountNumber;
			this.balance = balance;
			this.interestRate = interestRate;
			this.accountOpenedOn = accountOpenedOn;
		}
		
		// Getters and Setters
		public long getAccountNumber() {
			return this.accountNumber;
		}
		
		public double getBalance() {
			return this.balance;
		}
		
		public void setBalance(double balance) {
			this.balance = balance;
		}
		
		public double getInterestRate() {
			return this.interestRate;
		}
		
		public java.util.Date getOpenedOn(){
			return this.accountOpenedOn;
		}
		
		public void setAccountNumber(long accountNum) {
			this.accountNumber = accountNum;
		}
		
		
		//Methods
		
		/* Withdraw method
		 * 	amount is required to be less than the available balance
		 * 	for method to work properly, amount needs to be negative
		 * 	if amount > 1000, processes method AND adds transaction to FQ
		 * 	
		 */
		public boolean withdraw(double amount) {
			try {
				if((this.getBalance() - amount) < 0){      
		            throw new ExceedsAvailableBalanceException("Not enough funds in the account.");
		        }
			} catch(ExceedsAvailableBalanceException e) {
				System.out.println(e);
				return false;
			}
			
			try {
				if (Math.abs(amount) > 1000) {
            		throw new ExceedsFraudSuspicionLimitException("Transaction requires review, thanks for your patience.");
            	}
			} catch (ExceedsFraudSuspicionLimitException e) {
				java.util.Date fDate = new java.util.Date();
		        WithdrawTransaction newTrans = new WithdrawTransaction(-1, this.accountNumber, amount, fDate);
		        MeritBank.processTransaction(newTrans);
				System.out.println(e);
				FraudQueue.addTransaction(newTrans);
				return true;
			}
	        
	        java.util.Date fDate = new java.util.Date();
	        WithdrawTransaction newTrans = new WithdrawTransaction(-1, this.accountNumber, amount, fDate);
	        MeritBank.processTransaction(newTrans);
	        return true;
	    }
		
		/* Deposit method 
		 * 	amount required to be more than a negative
		 * 	if amount > 1000, processes transaction AND adds transaction to FQ
		 * 	if account is not under MeritBank stash, manually deposits money AND
		 *  adds transaction
		 */
		public boolean deposit(double amount) {
			
			
			try {
				if(amount < 0){
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
		        DepositTransaction newTrans = new DepositTransaction(-1, this.accountNumber, amount, fDate);
		        MeritBank.processTransaction(newTrans);
	        	System.out.println(e);
	        	FraudQueue.addTransaction(newTrans);
	        	return true;
	        }
	        	
	        java.util.Date fDate = new java.util.Date();
	        DepositTransaction newTrans = new DepositTransaction(-1, this.accountNumber, amount, fDate);
	        BankAccount tester = MeritBank.getBankAccount(this.accountNumber);
	        if(tester == null) {
	        	this.balance += amount;
	        	this.addTransaction(newTrans);
	        	return true;
	        } else {
	        	MeritBank.processTransaction(newTrans);
		        return true;
	        }
	    }
		
		/* Transfer method
		 * 	amount required to be more than negative
		 * 	transfer amount required to be less than balance
		 * 	if amount > 1000, processes transaction AND adds transaction to FQ
		 * 	transaction required to have target accountNum instead of -1
		 */	
		
		public boolean transfer(double amount, long target) {
			try {
				if(amount < 0) {
					throw new NegativeAmountException("Please Deposit a positive amount.");
				}
			} catch(NegativeAmountException e) {
				System.out.println(e);
				return false;
			}
			try {
				if(amount > this.balance) {
					throw new ExceedsAvailableBalanceException("Not enough funds in the account.");
				}
			} catch(ExceedsAvailableBalanceException e) {
				System.out.println(e);
				return false;
			}
			try {
	        	if(amount > 1000) {
		        	throw new ExceedsFraudSuspicionLimitException("Transaction requires review, thanks for your patience.");
	        	}
	        } catch(ExceedsFraudSuspicionLimitException e) {
	        	java.util.Date fDate = new java.util.Date();
				TransferTransaction newTrans = new TransferTransaction(target, this.accountNumber, amount, fDate);
		        MeritBank.processTransaction(newTrans);
	        	System.out.println(e);
	        	FraudQueue.addTransaction(newTrans);
	        	return true;
	        }
			
			java.util.Date fDate = new java.util.Date();
			TransferTransaction newTrans = new TransferTransaction(target, this.accountNumber, amount, fDate);
	        MeritBank.processTransaction(newTrans);
	        return true;
		}
		
		public double futureValue(int years) {
	        double fv = MeritBank.recursiveFutureValue(this.balance, 3, this.interestRate);
	        return fv;
	    }
		
		/*
		 *  toString method
		 * 	old code, needs to be redone at some point
		 */
		// Has to redo this code
		public String toString(){
	        DecimalFormat format = new DecimalFormat("##.00");
	        return "\nChecking Account Balance: $" + format.format(this.getBalance()) + "\n"
	                + "Checking Account Interest Rate: " + this.getInterestRate() + "\n"
	                + "Checking Account Balance in 3 years: $" + format.format(this.futureValue(3));
	    }
		
		public String writeToString() {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	    	return this.accountNumber + "," + this.balance + "," + this.interestRate
	    			+ "," + dateFormatter.format(this.accountOpenedOn);
	    }
		
		public void addTransaction(Transaction transaction) {
			this.list.add(transaction);
		}
		
		public List<Transaction> getTransaction(){
			return this.list;
		}
}
