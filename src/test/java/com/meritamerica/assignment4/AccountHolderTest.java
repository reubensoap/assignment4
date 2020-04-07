package com.meritamerica.assignment4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountHolderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAccountHolderCreation() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		assertNotNull(testAccount);
	}

	@Test
	public void testAddCheckingAccountDouble() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		testAccount.addCheckingAccount(500);
		
		assertEquals(500, testAccount.checking[0].getBalance(), 0);
	}

	@Test
	public void testAddCheckingAccountCheckingAccount() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CheckingAccount check1 = new CheckingAccount(1000);
		testAccount.addCheckingAccount(check1);
		
		assertNotNull(testAccount.checking[0]);
	}

	@Test
	public void testGetCheckingAccounts() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CheckingAccount check1 = new CheckingAccount(1000);
		testAccount.addCheckingAccount(check1);
		testAccount.addCheckingAccount(100);
		testAccount.addCheckingAccount(400);
		
		CheckingAccount arrayTest[] = testAccount.getCheckingAccounts();
		
		// is 1 more than actual since there is always an extra space at the end of array for next account
		assertEquals(4, arrayTest.length, 0);
	}

	@Test
	public void testGetNumberOfCheckingAccounts() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CheckingAccount check1 = new CheckingAccount(1000);
		testAccount.addCheckingAccount(check1);
		testAccount.addCheckingAccount(100);
		testAccount.addCheckingAccount(400);
		
		// length gives 1 more than usual however the method gives the exact number of accounts
		assertEquals(3, testAccount.getNumberOfCheckingAccounts(), 0);
	}

	@Test
	public void testGetCheckingBalance() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CheckingAccount check1 = new CheckingAccount(1000);
		testAccount.addCheckingAccount(check1);
		testAccount.addCheckingAccount(100);
		testAccount.addCheckingAccount(400);
		
		testAccount.addSavingsAccount(500);
		
		assertEquals(1500, testAccount.getCheckingBalance(), 0);
	}

	@Test
	public void testAddCDAccountCDOfferingDouble() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CDOffering offer1 = new CDOffering(3, 0.001);
		try {
			testAccount.addCDAccount(offer1, 500);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		assertNotNull(testAccount.cdAccount[0]);
	}

	@Test
	public void testAddCDAccountCDAccount() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CDOffering offer1 = new CDOffering(3, 0.001);
		CDAccount CDA1 = new CDAccount(offer1, 500);
		
		testAccount.addCDAccount(CDA1);
		
		assertNotNull(testAccount.cdAccount[0]);
	}

	@Test
	public void testGetCDAccounts() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CDOffering offer1 = new CDOffering(3, 0.001);
		CDAccount CDA1 = new CDAccount(offer1, 500);
		try {
			testAccount.addCDAccount(offer1, 500);
			testAccount.addCDAccount(offer1, 600);
		} catch (Exception e) {
			System.out.println(e);
		}
		testAccount.addCDAccount(CDA1);
		
		CDAccount testArray[] = testAccount.getCDAccounts();
		
		double tester = 0;
		
		for(CDAccount item: testArray) {
			if(item == null) {
				tester += 0;
			} else {
				tester += item.getBalance();
			}
		}
		
		assertEquals(1600, tester, 0);
	}

	@Test
	public void testGetCombinedBalance() {
		AccountHolder testAccount = new AccountHolder("Pena", "Ez", "Reuben", "123123123");
		CDOffering offer1 = new CDOffering(3, 0.001);
		CDAccount CDA1 = new CDAccount(offer1, 500);
		testAccount.addCDAccount(CDA1);
		testAccount.addCheckingAccount(400);
		testAccount.addSavingsAccount(100);
		
		assertEquals(1000, testAccount.getCombinedBalance(), 0);
	}

}
