package com.meritamerica.assignment4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MeritBankTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddAccountHolder() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		MeritBank.addAccountHolder(testAccount);
		
		assertNotNull(MeritBank.accountHolder[0]);
	}

	@Test
	public void testGetAccountHolders() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		AccountHolder testAccount2 = new AccountHolder("Alicia", "Lu", "Gutierrez", "123123122");
		MeritBank.addAccountHolder(testAccount);
		MeritBank.addAccountHolder(testAccount2);
		
		AccountHolder testArray[] = MeritBank.getAccountHolders();
		
		assertEquals(3, testArray.length, 0);
	}

	@Test
	public void testGetCDOfferings() {
		CDOffering cdo1 = new CDOffering(3, 0.01);
		CDOffering cdo2 = new CDOffering(2, 0.001);
		CDOffering cdo3 = new CDOffering(3, 0.015);
		CDOffering cdo4 = new CDOffering(1, 0.02);
		
		CDOffering cdArray[] = {cdo1, cdo2, cdo3, cdo4};
		
		MeritBank.setCDOfferings(cdArray);
		
		CDOffering cdArray2[] = MeritBank.getCDOfferings();
		
		assertEquals(cdArray[3].getInterestRate(), cdArray2[3].getInterestRate(), 0);
	}

	@Test
	public void testSetCDOfferings() {
		CDOffering cdo1 = new CDOffering(3, 0.01);
		CDOffering cdo2 = new CDOffering(2, 0.001);
		CDOffering cdo3 = new CDOffering(3, 0.015);
		CDOffering cdo4 = new CDOffering(1, 0.02);
		
		CDOffering cdArray[] = {cdo1, cdo2, cdo3, cdo4};
		
		MeritBank.setCDOfferings(cdArray);
		
		assertNotNull(MeritBank.cdOfferingHolder[3]);
	}

	@Test
	public void testTotalBalances() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		AccountHolder testAccount2 = new AccountHolder("Alicia", "Lu", "Gutierrez", "123123122");
		
		MeritBank.addAccountHolder(testAccount);
		MeritBank.addAccountHolder(testAccount2);
		
		testAccount.addCheckingAccount(1000);
		testAccount2.addSavingsAccount(2000);
		
		assertEquals(3000, MeritBank.totalBalances(), 0);
	}

	@Test
	public void testProcessTransactionDepositTransaction() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		
		MeritBank.addAccountHolder(testAccount);
		
		testAccount.addCheckingAccount(CheckingAccount.readFromString("4,100,0.0001,12/01/2019"));
		testAccount.addCheckingAccount(CheckingAccount.readFromString("5,200,0.0001,12/15/2019"));
		
		testAccount.checking[0].transfer(50, 5);
		
		assertEquals(50.0, testAccount.checking[0].getBalance(), 0);
		
	}

}
