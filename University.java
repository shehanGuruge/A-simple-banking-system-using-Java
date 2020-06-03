package com.shehan.bankingSystem;

public class University extends Thread{

	private String universityName; 
	private CurrentBankAccount account;
	
	/**
	 * Accepts the following parameters
	 * @param group - the threadgroup in which this particular thread is needed to be assigned to 
	 * @param name - the name of the university
	 * @param account - CurrentBankAccount object representing the details of the account
	 */
	public University(ThreadGroup group,String name, CurrentBankAccount account) {
		super(group,name);
		this.universityName = name;
		this.account = account;
	}
	
	@Override
	public void run() {
		System.out.println("***** [BEGIN] University operations [BEGIN] *****");
    	
    	Transaction year1Payment = new Transaction (getName(), 80000);
    	Transaction year2Payment = new Transaction (getName(), 85000);
    	Transaction year3Payment = new Transaction (getName(), 110000);
    	
    	Transaction[] transactions = new Transaction[] 
    	{
			year1Payment,
			year2Payment,
			year3Payment
    	};
		
    	/*
    	 * iteratively withdraws money from the current bank account and shows 
    	 * the messages corresponding to each withdrawal.
    	 */
		for(int i = 0; i < transactions.length; i++) {
			account.withdrawal(transactions[i]);
			
			// Thread goes to sleep for a random time 
			try {
				Thread.sleep((int) Math.random() * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("***** [TERMINATED] University operations [TERMINATED] *****");
	}
}
