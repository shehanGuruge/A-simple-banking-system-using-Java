package com.shehan.bankingSystem;

public class Transaction {
	private final String CustomerID ;  
    private final int amount ;

     public Transaction( String CID, int amount )
     {
        this.CustomerID  = CID ;
        this.amount 	 = amount ;
     }


    public String getCID( ) { return CustomerID ; }

    public int getAmount( ) { return amount ; }


    public String toString( )
    {
        return  new String( "[ " + "Customer: " + CustomerID + ", " 
                                 + "Amount: "   + amount +
                            "]"  
                          ) ;
    }
}
