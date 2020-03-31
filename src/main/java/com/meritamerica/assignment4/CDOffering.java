package com.meritamerica.assignment4;

public class CDOffering {
	private int term;
    private double interestRate;

    public CDOffering(int term, double interestRate){
        this.term = term;
        this.interestRate = interestRate;
    }

    public int getTerm(){
        return this.term;
    }

    public double getInterestRate(){
        return this.interestRate;
    }
    
    public void setTerm(int term) {
    	this.term = term;
    }
    
    public void setInterestRate(double interest) {
    	this.interestRate = interest;
    }

    public String toString(){
        return "Term: " + this.getTerm() + " Interest Rate: " + this.getInterestRate();
    }
    
    static CDOffering readFromString(String accountData) {
    	
    	String array1[] = accountData.split(",");
    	int fTerm = Integer.parseInt(array1[0]);
    	double fInterest = Double.parseDouble(array1[1]);
    	
    	CDOffering offeringX = new CDOffering(fTerm, fInterest);
    	return offeringX;
    	
    }
	
	public String writeToString() {
    	return this.term + "," + this.interestRate;
    }
}
