package com.shehan.bankingSystem;


public class LoanCompany extends Thread {

	private CurrentBankAccount account;
	private String companyName;
	
	/**
	 * Accepts the following parameters
	 * @param groupName - thread group name in which this thread is needed to be assigned to
	 * @param companyName - the name of the loan company name
	 * @param account - CurrentBankAccount object representing the details of the account
	 */
	public LoanCompany(ThreadGroup groupName, String companyName, CurrentBankAccount account) {
		super(groupName,companyName);
		this.companyName = companyName;
		this.account = account;
	}
	
	@Override
	public void run() {
		System.out.println("***** [BEGIN] Loan Company operations [BEGIN] *****");
		
		Transaction loan1 = new Transaction(getName(), 60000);
		Transaction loan2 = new Transaction(getName(), 40000);
		Transaction loan3 = new Transaction(getName(), 55000);
		
		Transaction[] loans = new Transaction[] 
		{
				loan1,
				loan2,
				loan3
		};
		
		/*
		 * Performs deposits to the current bank account iteratively thrice using the 
		 * values in the loan_deposits array.
		 */
		for(int i = 0; i < loans.length; i++) {
			account.deposit(loans[i]);
			
			// Thread send to sleep for a random time
			try {
				Thread.sleep((int)(Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("***** [TERMINATED] Loan Company operations [TERMINATED] *****");
	}
}
