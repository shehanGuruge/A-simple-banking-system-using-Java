package com.shehan.bankingSystem;

import java.util.Scanner;

import javax.script.SimpleScriptContext;

public class BankingSystem {

	private ThreadGroup humans = new ThreadGroup("humans");
	private ThreadGroup organizations = new ThreadGroup("organizations");
	
	private CurrentBankAccount bankAccount;
	
	private Student student;
	private GrandMother grandmother;
	private LoanCompany loanCompany ;
	private University university;
	
	public BankingSystem() {
		 bankAccount = new CurrentBankAccount("Shehan",100109,0);
		 student = new Student(humans,"Shehan",bankAccount);
		 grandmother = new GrandMother(humans, "Shehan's Grandma", bankAccount);
		 loanCompany = new LoanCompany(organizations, "LoansRUs", bankAccount);
		 university = new University(organizations, "UoW", bankAccount);
	}
	
	public static void main(String[] args) {
		BankingSystem bankingSystem = new BankingSystem();
		bankingSystem.student.start();
		bankingSystem.grandmother.start();
		bankingSystem.loanCompany.start();
		bankingSystem.university.start();
		
		try {
            bankingSystem.student.join();
            bankingSystem.grandmother.join();
            bankingSystem.loanCompany.join();
            bankingSystem.university.join();
	   } catch (InterruptedException e) { }
                
                
		System.out.println("\n\n Transactions Report");
		bankingSystem.bankAccount.printStatement();
//		System.out.println(bankingSystem.bankAccount.getBalance());
	}

}