package com.shehan.bankingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends Thread{
	private String studentName;
	private CurrentBankAccount account;
	
	/**
	 * Accepts the following parameters
	 * @param threadGroup - the thread group in which this particular thread is needed to be assigned to
	 * @param studentName - name of the student
	 * @param account - CurrentBankAccount object representing the details of the account
	 */
	public Student( ThreadGroup threadGroup, String studentName, CurrentBankAccount account) {
		super(threadGroup, studentName);
		this.studentName = studentName;
		this.account = account;
	}
	
	
	@Override
	public void run() {
		System.out.println("***** [BEGIN] Student operations [BEGIN] *****");
		
		/*
		 * As per the spec 6 Transactions are created for each distinct roles
		 * 3 transactions = withdrawals, 3 transactions = deposits
		*/
		
		// withdrawal transactions
		Transaction buyPhone = new Transaction(getName(),30000);
		Transaction buyFood = new Transaction(getName(),5000);
		Transaction payBills = new Transaction(getName(),5000);
		// deposit transactions
		Transaction winLottery = new Transaction(getName(),50000);
		Transaction savings = new Transaction(getName(),5000);
		Transaction soldLaptop = new Transaction(getName(),75000);
		
		// all the transactions for withdrawal are added to an arraylist
		List<Transaction> withdrawalList = new ArrayList<Transaction>() 
		{{
			add(buyFood);
			add(buyPhone);
			add(payBills);
		}};
		
		// all the transactions for deposits are added to an arraylist
		List<Transaction> depositList = new ArrayList<Transaction>() 
		{{
			add(winLottery);
			add(savings);
			add(soldLaptop);
		}};
		
		/*
		 * The below code is responsible for making random transactions with the current bank account
		 * as withdrawals or deposits.
		*/
		
		Random random = new Random();
		
		/*
		 * isWithdrawal represents whether a withdrawal needed to be conducted or a deposit.
		 * IF isWithdrawal = 0 then all the withdrawal related transactions are conducted 
		 * IF isWithdrawal = 1 then all the deposit related transactions are conducted
		*/
		int isWithdrawal = 0;
		
		// The order in which each operation is conducted based on the random value conducted
		int eventOrder = 0;
		
		// A counter for recording the number of transactions conducted
		int count = 0;
		
		// count is less than 6 because the number of transactions are limited to 6
		while (count < 6) {
			
			/*
			 * in each iteration a random value is generated between 0 and 1 where,
			 * 0 means withdraw and
			 * 1 means deposit
			 * */
			isWithdrawal = random.nextInt(2);
			
			if(isWithdrawal == 0 && withdrawalList.size() != 0) {
				
				/*
				 * Another random value is generated with the size of the withdrawallist size (4) 
				 * within the range of 0 to 3.
				 * This is used to randomize the withdrawal operations occurring order
				*/
				eventOrder = random.nextInt(withdrawalList.size());
				performWithdrawals(withdrawalList.get(eventOrder));
				
				/*
				 * remove the element from the list thus, the next generated random number will
				 * be within the range of currentwithdrawallist.size - 1
				 * This is essential inorder to prevent from picking the same item from the list 
				 * for future withdrawals.
				*/
				withdrawalList.remove(eventOrder); 
				count++;
			} else if (isWithdrawal == 1 && depositList.size() != 0) {
				
				/*
				 * Another random value is generated with the size of the depositList size (2) 
				 * within the range of 0 to 1.
				 * This is used to randomize the withdrawal operations occuring order
				*/
				eventOrder = random.nextInt(depositList.size());
				performDeposits(depositList.get(eventOrder));
				
				/*
				 * remove the element from the list thus, the next generated random number will
				 * be within the range of currentdepositlist.size - 1
				 * This is essential inorder to prevent from picking the same item from the list 
				 * for future deposits.
				*/
				depositList.remove(eventOrder);
				count++;
			}
			
			// current thread is sent to sleep for a random time
			try {
				Thread.sleep((int)(Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("***** [TERMINATED] Student operations [TERMINATED] *****");
		
	}
	
	/**
	 * performs the deposit in the current bank account and the status message is displayed
	 * @param transaction - used for depositing the amount
	 */
	public void performDeposits(Transaction transaction) {
		account.deposit(transaction);
	}
	
	/**
	 * performs the withdrawals in the current bank account and the status message is displayed
	 * @param transaction - used for withdrawing the amount
	 */
	public void performWithdrawals(Transaction transaction) {
		account.withdrawal(transaction);
	}
}


