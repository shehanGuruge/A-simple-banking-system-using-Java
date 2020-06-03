package com.shehan.bankingSystem;

public class GrandMother extends Thread {

	private String name;
	private CurrentBankAccount account;
	
	/**
	 * Accepts the following parameters
	 * @param groupName - the thread group this particular thread is needed to assigned to
	 * @param name - name of the grandmother
	 * @param account - CurrentBankAccount object representing the details of the account
	 */
	public GrandMother(ThreadGroup groupName, String name, CurrentBankAccount account) {
		super(groupName, name);
		this.name = name;
		this.account = account;
	}
	
	@Override
	public void run() {
		System.out.println("***** [BEGIN] Grandmother operations [BEGIN] *****");
		
		// represents a transaction for the birthday gift
		Transaction birthdayGift = new Transaction(getName(), 20000);
		account.deposit(birthdayGift);
		
		// send the thread to sleep for a random amount of time
		try {
			Thread.sleep((int)(Math.random() * 100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// transaction representing the christmas gift
		Transaction christmasGift = new Transaction(getName(), 10000);
		account.deposit(christmasGift);
		System.out.printf("***** [TERMINATED] %s - Grandmother [TERMINATED] *****\n", getName());
	}
}
