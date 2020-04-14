package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeritBank {
	static AccountHolder accountHolder[] = new AccountHolder[1];
    static CDOffering cdOfferingHolder[] = new CDOffering[1];
    static long lastAccountNumber = 0;

    static void addAccountHolder(AccountHolder accountHolderx){
        accountHolder[accountHolder.length - 1] = accountHolderx;
        AccountHolder arrayTemp[] = new AccountHolder[accountHolder.length + 1];
        for(int x = 0; x < accountHolder.length; x++) {
        	arrayTemp[x] = accountHolder[x];
        }
        accountHolder = arrayTemp;
    }

    static AccountHolder[] getAccountHolders() {
        return accountHolder;
    }

    static CDOffering[] getCDOfferings() {
        return cdOfferingHolder;
    }

    // used for get Best CDOffer inside the cdOfferingHolder array
    static CDOffering getBestCDOffering(double depositAmount){
        int best = 0;
        double tv;
        double fv;
        if (cdOfferingHolder.length < 2){
            System.out.println("Unable to complete action. Need more CD Offers.");
            return cdOfferingHolder[0];
        }
        for(int x = 0; x < cdOfferingHolder.length; x++){
            tv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[x].getTerm(), cdOfferingHolder[x].getInterestRate());
            fv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[best].getTerm(), cdOfferingHolder[best].getInterestRate());
            if (tv > fv){
                best = x;
            }
        }
        return cdOfferingHolder[best];
    }

    // used for get Second Best CDOffer inside the cdOfferingHolder array
    static CDOffering getSecondBestCDOffering(double depositAmount){
        int best = 0;
        int second = 0;
        double tv;
        double fv;
        double sv;
        if (cdOfferingHolder.length < 2){
            System.out.println("Unable to complete action. Need more CD Offers.");
            return null;
        }
        for(int x = 0; x < cdOfferingHolder.length; x++){
            double pv = depositAmount;
            tv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[x].getTerm(), cdOfferingHolder[x].getInterestRate());
            fv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[best].getTerm(), cdOfferingHolder[best].getInterestRate());
            sv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[second].getTerm(), cdOfferingHolder[second].getInterestRate());
            if (tv > fv){
                second = best;
                best = x;
            }
            else if(tv > sv && tv != fv){
                second = x;
            }
        }
        return cdOfferingHolder[second];
    }

    // Clears the cdOfferingHolder array in order to get ready for another set of 
    // CDOfferings
    static void clearCDOfferings(){
        CDOffering newArr[] = null;
        cdOfferingHolder = newArr;
    }

    // Sets a new array of CDOfferings in the cdOfferingHolder 
    static void setCDOfferings(CDOffering[] offerings){
        cdOfferingHolder = offerings;
    }

    // returns the balances of all AccountHolders inside the Merit
    // Bank class
    static double totalBalances(){
        double tBalance = 0;
        for(int x = 0; x < accountHolder.length - 1; x++){
            tBalance += accountHolder[x].getCombinedBalance();
        }
        return tBalance;
    }

    static long getNextAccountNumber(){
    	long newNum = MeritBank.lastAccountNumber;
    	MeritBank.lastAccountNumber++;
    	return newNum;
    }
    
    static void setNextAccountNumber(long nextAccountNumber) {
    	MeritBank.lastAccountNumber = nextAccountNumber;
    }
    
    // sorts AccountHolders based on CompareTo override in AccountHolder class
    // starts at index 0 and ends at the second to last index due to last
    // index being null
    static AccountHolder[] sortAccountHolders() {
    	Arrays.sort(accountHolder, 0, accountHolder.length - 1);
    	return accountHolder;
    }
    
    public static List<Transaction> getFraudQueue(){
    	return FraudQueue.getTransactions();
    }
    
    public static BankAccount getBankAccount(long accountID) {
    	BankAccount found = null;
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				found = checkingArray[y];
    				return found;
    			}
    		}
    		
    		SavingsAccount savingsArray[] = accountHolder[x].getSavingsAccounts();
    		
    		for(int y = 0; y < savingsArray.length - 1; y++) {
    			if(accountID == savingsArray[y].getAccountNumber()) {
    				found = savingsArray[y];
    				return found;
    			}
    		}
    		
    		CDAccount cdAccountArray[] = accountHolder[x].getCDAccounts();
    		
    		for(int y = 0; y < cdAccountArray.length - 1; y++) {
    			if(accountID == cdAccountArray[y].getAccountNumber()) {
    				found = cdAccountArray[y];
    				return found;
    			}
    		}
    		
    	}
    	
    	return found;
    }
    
    public static boolean setBankAccount(BankAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				checkingArray[y] = (CheckingAccount) source;
    				return true;
    			}
    		}
  
    	}
    	
    	return false;
    }
    
    public static boolean setBankAccount(CheckingAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				checkingArray[y] = (CheckingAccount) source;
    				return true;
    			}
    		}
  
    	}
    	
    	return false;
    }
    
    public static boolean setBankAccount(SavingsAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		SavingsAccount savingsArray[] = accountHolder[x].getSavingsAccounts();
    		
    		for(int y = 0; y < savingsArray.length - 1; y++) {
    			if(accountID == savingsArray[y].getAccountNumber()) {
    				savingsArray[y] = (SavingsAccount) source;
    				return true;
    			}
    		}
    		
    	}
    	
    	return false;
    }

	public static boolean setBankAccount(CDAccount source, long accountID) {
		
		for(int x = 0; x < accountHolder.length - 1; x++) {
			
			CDAccount cdAccountArray[] = accountHolder[x].getCDAccounts();
			
			for(int y = 0; y < cdAccountArray.length - 1; y++) {
				if(accountID == cdAccountArray[y].getAccountNumber()) {
					cdAccountArray[y] = (CDAccount) source;
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		amount = amount * (1 + interestRate);
		if(years != 1) {
			return recursiveFutureValue(amount, years - 1, interestRate);
		} else {
			return amount;
		}
	}
    
    public static boolean processTransaction(Transaction transaction) {
    	if(transaction.getTargetAccountNum() == -1) {
    			BankAccount source = transaction.getSourceAccount();
    			if(transaction.getSourceAccount() instanceof CheckingAccount) {
    				source = (CheckingAccount) transaction.getSourceAccount();
    			}
    			
    			if(transaction.getSourceAccount() instanceof SavingsAccount) {
    				source = (SavingsAccount) transaction.getSourceAccount();
    			}
    			
    			if(transaction.getSourceAccount() instanceof CDAccount) {
    				source = (CDAccount) transaction.getSourceAccount();
    			}
    			double currentBal = source.getBalance();
    			source.setBalance(currentBal + transaction.getAmount());
    			source.addTransaction(transaction);
    			MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
    			return true;
    	} else {
    		BankAccount source = transaction.getSourceAccount();
    		BankAccount target = transaction.getTargetAccount();
    		double currentBal = source.getBalance();
    		source.setBalance(currentBal - transaction.getAmount());
    		double tcurrentBal = target.getBalance();
    		target.setBalance(tcurrentBal + transaction.getAmount());
    		source.addTransaction(transaction);
    		MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
    		MeritBank.setBankAccount(target, transaction.getTargetAccountNum());
    		return true;
    	}
    }
    
    /*
    
    public static boolean processTransaction(DepositTransaction transaction) {
    	if(transaction.getTargetAccountNum() == -1) {
			BankAccount source = transaction.getSourceAccount();
			if(transaction.getSourceAccount() instanceof CheckingAccount) {
				source = (CheckingAccount) transaction.getSourceAccount();
			}
			
			if(transaction.getSourceAccount() instanceof SavingsAccount) {
				source = (SavingsAccount) transaction.getSourceAccount();
			}
			
			if(transaction.getSourceAccount() instanceof CDAccount) {
				source = (CDAccount) transaction.getSourceAccount();
			}
			double currentBal = source.getBalance();
			source.setBalance(currentBal + transaction.getAmount());
			source.addTransaction(transaction);
			MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
			return true;
		} else {
    		BankAccount source = transaction.getSourceAccount();
    		BankAccount target = transaction.getTargetAccount();
    		double currentBal = source.getBalance();
    		source.setBalance(currentBal - transaction.getAmount());
    		double tcurrentBal = target.getBalance();
    		source.addTransaction(transaction);
    		target.setBalance(tcurrentBal + transaction.getAmount());
    		MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
    		MeritBank.setBankAccount(target, transaction.getTargetAccountNum());
    		return true;
    	}
    	
    	
    }
    */
    /*
    public static boolean processTransaction(TransferTransaction transaction) {
    	if(transaction.getTargetAccountNum() == -1) {
			BankAccount source = transaction.getSourceAccount();
			if(transaction.getSourceAccount() instanceof CheckingAccount) {
				source = (CheckingAccount) transaction.getSourceAccount();
			}
			
			if(transaction.getSourceAccount() instanceof SavingsAccount) {
				source = (SavingsAccount) transaction.getSourceAccount();
			}
			
			if(transaction.getSourceAccount() instanceof CDAccount) {
				source = (CDAccount) transaction.getSourceAccount();
			}
			double currentBal = source.getBalance();
			source.setBalance(currentBal + transaction.getAmount());
			source.addTransaction(transaction);
			MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
			return true;
		} else {
    		BankAccount source = transaction.getSourceAccount();
    		BankAccount target = transaction.getTargetAccount();
    		double currentBal = source.getBalance();
    		source.setBalance(currentBal - transaction.getAmount());
    		double tcurrentBal = target.getBalance();
    		target.setBalance(tcurrentBal + transaction.getAmount());
    		source.addTransaction(transaction);
    		MeritBank.setBankAccount(source, transaction.getSourceAccountNum());
    		MeritBank.setBankAccount(target, transaction.getTargetAccountNum());
    		return true;
    	}
    }
    */
    
    static boolean readFromFile(String fileName) {
    	
    	MeritBank.accountHolder = new AccountHolder[1];
    	
    	try {
			FileReader fr = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(fr);
			
			String line;
			
			if((line = bf.readLine()) != null)
				MeritBank.setNextAccountNumber(Long.parseLong(line));
			if((line = bf.readLine()) != null) {
				CDOffering tempCDArray[] = new CDOffering[Integer.parseInt(line)];
				for(int x = 0; x < tempCDArray.length; x++) {
					if((line = bf.readLine()) != null)
					tempCDArray[x] = CDOffering.readFromString(line);
				}
				MeritBank.setCDOfferings(tempCDArray);
			}
			if((line = bf.readLine()) != null) {
				AccountHolder tempHolderArray[] = new AccountHolder[Integer.parseInt(line)];
				for(int x = 0; x < tempHolderArray.length; x++) {
					if((line = bf.readLine()) != null) {
						tempHolderArray[x] = AccountHolder.readFromString(line);
					}
					if((line = bf.readLine()) != null) {
						int checkingNum = Integer.parseInt(line);
						for(int y = 0; y < checkingNum; y++) {
							if((line = bf.readLine()) != null) {
								//CheckingAccount newTest = tempHolderArray[x].addCheckingAccount(CheckingAccount.readFromString(line));
								tempHolderArray[x].addCheckingAccount(CheckingAccount.readFromString(line));
								
							if((line = bf.readLine()) != null) {
								int transNum = Integer.parseInt(line);
								
								for (int z = 0; z < transNum; z++) {
									if((line = bf.readLine()) != null)
									tempHolderArray[x].checking[y].addTransaction(Transaction.readFromString(line));
								}
							}
							}
						}
							
					}
					if((line = bf.readLine()) != null) {
						int savingsNum = Integer.parseInt(line);
						for(int y = 0; y < savingsNum; y++) {
							if((line = bf.readLine()) != null)
								tempHolderArray[x].addSavingsAccount(SavingsAccount.readFromString(line));
							
							if((line = bf.readLine()) != null) {
								int transNum = Integer.parseInt(line);
								
								for (int z = 0; z < transNum; z++) {
									if((line = bf.readLine()) != null)
									tempHolderArray[x].savings[y].addTransaction(Transaction.readFromString(line));
								}
							}
						}
					}
					if((line = bf.readLine()) != null) {
						int cdAccountNum = Integer.parseInt(line);
						for(int y = 0; y < cdAccountNum; y++) {
							if((line = bf.readLine()) != null)
								tempHolderArray[x].addCDAccount(CDAccount.readFromString(line));
							
							if((line = bf.readLine()) != null) {
								int transNum = Integer.parseInt(line);
								
								for (int z = 0; z < transNum; z++) {
									if((line = bf.readLine()) != null)
									tempHolderArray[x].cdAccount[y].addTransaction(Transaction.readFromString(line));
								}
							}
						}
					}
				}
				if((line = bf.readLine()) != null) {
					int queueNum = Integer.parseInt(line);
					for(int x = 0; x < queueNum; x++) {
						if((line = bf.readLine()) != null)
							FraudQueue.addTransaction(Transaction.readFromString(line));
					}
				}
				for(int x = 0; x < tempHolderArray.length; x++) {
					MeritBank.addAccountHolder(tempHolderArray[x]);
				}
			}
			fr.close();
			
			return true;
		} catch (IOException e) {
			System.out.println("ERROR!");
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	
    }
    
    static String writeToString() {
    	String offeringList = "";
    	for(int x = 0; x < cdOfferingHolder.length; x++) {
    		offeringList += cdOfferingHolder[x].writeToString() + "\n";
    	}
    	String accountList = "";
    	accountList += accountHolder.length - 1 + "\n";
    	for (int x = 0; x < accountHolder.length - 1; x++) {
    		accountList += accountHolder[x].writeToString() + "\n";
    		
    		CheckingAccount arrayCheck[] = accountHolder[x].getCheckingAccounts();
    		
    		accountList += accountHolder[x].getNumberOfCheckingAccounts() + "\n";
    		
    		for(int y = 0; y < arrayCheck.length - 1; y++) {
    			accountList += arrayCheck[y].writeToString() + "\n";
    			
    			List<Transaction> tempList = arrayCheck[y].getTransaction();
    			accountList += tempList.size() + "\n";
    			
    			for(int z = 0; z < tempList.size(); z ++) {
    				accountList += tempList.get(z).toString() + "\n";
    			}
    		}
    		
    		SavingsAccount arraySave[] = accountHolder[x].getSavingsAccounts();
    		
    		accountList += accountHolder[x].getNumberOfSavingsAccounts() + "\n";
    		
    		for(int y = 0; y < arraySave.length - 1; y++) {
    			accountList += arraySave[y].writeToString() + "\n";
    			
    			List<Transaction> tempList = arraySave[y].getTransaction();
    			accountList += tempList.size() + "\n";
    			
    			for(int z = 0; z < tempList.size(); z++) {
    				accountList += tempList.get(z).toString() + "\n";
    			}
    		}
    		
    		CDAccount arrayCD[] = accountHolder[x].getCDAccounts();
    		
    		accountList += accountHolder[x].getNumberOfCDAccounts() + "\n";
    		
    		for(int y = 0; y < arrayCD.length - 1; y++) {
    			accountList += arrayCD[y].writeToString() + "\n";
    			
    			List<Transaction> tempList = arrayCD[y].getTransaction();
    			accountList += tempList.size() + "\n";
    			
    			for(int z = 0; z < tempList.size(); z++) {
    				accountList += tempList.get(z).toString() + "\n";
    			}
    		}
    	}
    	String fraudList = "";
    	List<Transaction> tempList = FraudQueue.getTransactions();
    	fraudList += tempList.size() + "\n";
    	for(int x = 0; x < tempList.size(); x++) {
    		fraudList += tempList.get(x).toString() + "\n";
    	}
    	String sb = MeritBank.lastAccountNumber + "\n"
    			+ cdOfferingHolder.length + "\n"
    			+ offeringList
    			+ accountList
    			+ fraudList;
    	
    	return sb;
    }
    
    static void writeToFile() {
    	try {
    	FileWriter writer = new FileWriter("src/test/testMeritBank_Reuben.txt", false);
    	BufferedWriter buffered = new BufferedWriter(writer);
    	
    	String nextNumString = Long.toString(MeritBank.lastAccountNumber);
    	//First Line - next account num
    	buffered.write(nextNumString);
    	buffered.newLine();
    	// second line - num of CDOffers in Merit Array
    	String numOfCDO = Integer.toString(MeritBank.cdOfferingHolder.length);
    	buffered.write(numOfCDO);
    	buffered.newLine();
    	
    	for(int x = 0; x < cdOfferingHolder.length; x++) {
    		// lines for CDOfferings
    		String cdoLine;
    		buffered.write(cdOfferingHolder[x].writeToString());
    		buffered.newLine();
    	}
    	
    	// line for account holder count
    	String numOfAccount = Integer.toString(accountHolder.length - 1);
    	buffered.write(numOfAccount);
    	buffered.newLine();
    	// for loop to get information out of each account Holder
    	for (int x = 0; x < accountHolder.length - 1; x++) {
    		// detailed info on the account Holder
    		buffered.write(accountHolder[x].writeToString());
    		buffered.newLine();
    		
    		CheckingAccount arrayCheck[] = accountHolder[x].getCheckingAccounts();
    		
    		// get num of checking accounts in account holder
    		buffered.write(Integer.toString(accountHolder[x].getNumberOfCheckingAccounts()));
    		buffered.newLine();
    		
    		// loop to get details on each checking account
    		for(int y = 0; y < arrayCheck.length - 1; y++) {
    			buffered.write(arrayCheck[y].writeToString());
    			buffered.newLine();
    			
    			List<Transaction> tempList = arrayCheck[y].getTransaction();
    			buffered.write(Integer.toString(tempList.size()));
    			buffered.newLine();
    			
    			for(int z = 0; z < tempList.size(); z ++) {
    				buffered.write(tempList.get(z).toString());
    				buffered.newLine();
    			}
    		}
    		
    		SavingsAccount arraySave[] = accountHolder[x].getSavingsAccounts();
    		
    		// get num of savings accounts in account holder
    		buffered.write(Integer.toString(accountHolder[x].getNumberOfSavingsAccounts()));
    		buffered.newLine();
    		
    		// loop to get details on each savings account
    		for(int y = 0; y < arraySave.length - 1; y++) {
    			buffered.write(arraySave[y].writeToString());
    			buffered.newLine();
    			
    			List<Transaction> tempList = arraySave[y].getTransaction();
    			buffered.write(Integer.toString(tempList.size()));
    			buffered.newLine();
    			
    			for(int z = 0; z < tempList.size(); z ++) {
    				buffered.write(tempList.get(z).toString());
    				buffered.newLine();
    			}
    		}
    		
    		CDAccount arrayCD[] = accountHolder[x].getCDAccounts();
    		
    		// get num of CD accounts in account holder
    		buffered.write(Integer.toString(accountHolder[x].getNumberOfCDAccounts()));
    		buffered.newLine();
    		
    		// loop to get details on each CD account
    		for(int y = 0; y < arrayCD.length - 1; y++) {
    			buffered.write(arrayCD[y].writeToString());
    			buffered.newLine();
    			
    			List<Transaction> tempList = arrayCD[y].getTransaction();
    			buffered.write(Integer.toString(tempList.size()));
    			buffered.newLine();
    			
    			for(int z = 0; z < tempList.size(); z ++) {
    				buffered.write(tempList.get(z).toString());
    				buffered.newLine();
    			}
    		}
    	}
    	
    	List<Transaction> tempList = FraudQueue.getTransactions();
    	buffered.write(Integer.toString(tempList.size()));
    	buffered.newLine();
    	
    	for(int x = 0; x < tempList.size(); x++) {
    		buffered.write(tempList.get(x).toString());
    		buffered.newLine();
    	}
    	
    	buffered.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
