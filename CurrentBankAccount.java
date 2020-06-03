/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shehan.bankingSystem;

import java.util.concurrent.Semaphore;

/**
 *
 * @author shehan
 */
public class CurrentBankAccount implements BankAccount{

    private int accountNumber;
	private String accountHolderName;
	private int balance;
	private Statement statement;
	
	public CurrentBankAccount(String accountHolderName,int accountNumber, int balance) {
		super();
		this.accountHolderName = accountHolderName;
		this.accountNumber = accountNumber;
		this.balance = balance;
		statement = new Statement(this.accountHolderName, this.accountNumber);
	}
	
	
	@Override
	public int getBalance() {
		return this.balance;
	}

	@Override
	public int getAccountNumber() {
		return this.accountNumber;
	}

	@Override
	public String getAccountHolder() {
		return this.accountHolderName;
	}

	@Override
	public synchronized void deposit(Transaction t) {
		this.balance += t.getAmount();
		statement.addTransaction(t.getCID(), t.getAmount(), this.balance);
		
		System.out.printf("\u2022 \t [%s] deposited [ Rs.%d ] successfully \n" , 
				Thread.currentThread().getName(), t.getAmount());
		
		notifyAll();
	}

	@Override
	public synchronized void withdrawal(Transaction t) {
		int withdrawAmount = t.getAmount();
		ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup().getParent();
		int retriesLimit = 10;
		
		while(this.balance < withdrawAmount && retriesLimit != 0) {
			int activeCount = mainThreadGroup.activeCount() ;
			try {
                if(activeCount <= 3) {
                	System.out.println("Waiting for funds....");
                	wait(1000);
                	retriesLimit--;
                }  else {
                	System.out.println("\n=========================================================================================");
                    System.out.println("Balance of the current account is not applicable for this transaction "
                    		+ "[withdrawal] of "+ t.getAmount()  + "\n");
                    wait();
                }
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if((this.balance - withdrawAmount) < 0) {
			System.out.printf("SORRY, YOUR TRANSACTION OF RS. [%d] IS UNSUCCESSFUL SINCE NO FUNDS TRANSFERRED TO YOUR ACCOUNT LATELY\n",  t.getAmount());
		}else {
	        this.balance = this.balance - withdrawAmount;
	        statement.addTransaction(t.getCID(), t.getAmount(), this.balance);
	        
	        System.out.printf("\u2022  \t [%s] withdrawed [ Rs.%d ] successfully\n" , 
	        		Thread.currentThread().getName(), t.getAmount());
		}
        
        notifyAll();
	}
	
	@Override
	public boolean isOverdrawn() {
		return this.balance < 0;
	}

	@Override
	public void printStatement() {
		statement.print();
	}
	

	private Semaphore mutex = new Semaphore(1);
	/**
	 * Following two methods hold the code for withdrawing by using semaphores and deposits
	 * @param t
	 */
	public void withdrawWithSemaphores(Transaction t) {
		try {
			int withdrawAmount = t.getAmount();
			int iterations = 0;
			while(this.balance < t.getAmount()) {
				if(iterations < 1) {
					 System.out.println("Balance of the current account is not applicable for this transaction "
		                		+ "[withdrawal] of "+ t.getAmount()  + "\n");
					iterations++;
				}
				mutex.release();	
			}
			mutex.acquire();
			this.balance = this.balance - withdrawAmount;
			statement.addTransaction(t.getCID(), t.getAmount(), this.balance);
			
			System.out.println("\n=========================================================================================");
			System.out.printf("\u2022  \t [%s] withdrawed [ Rs.%d ] successfully\n" , 
					Thread.currentThread().getName(), t.getAmount());
			
			mutex.release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public void depositWithSemaphores(Transaction t) {
		try {
			mutex.acquire();
			this.balance += t.getAmount();
			statement.addTransaction(t.getCID(), t.getAmount(), this.balance);
			
			System.out.printf("\u2022 \t [%s] deposited [ Rs.%d ] successfully \n" , 
					Thread.currentThread().getName(), t.getAmount());
			
			mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    
}
