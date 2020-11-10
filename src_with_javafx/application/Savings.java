package application;

/**
 * Savings class defines a savings account and extends the Account class
 * @author Amy Wang, Junhao Shen
 */
public class Savings extends Account {
	private boolean isLoyal;

	/**
	 * Constructor for a new Savings account
	 * @param fname    first name of account holder
	 * @param lname    last name of account holder
	 * @param balance  of the account
	 * @param dateOpen account open date
	 * @param isLoyal  whether or not customer is loyal
	 */
	public Savings(String fname, String lname, double balance, Date dateOpen, boolean isLoyal) {
		super(fname, lname, balance, dateOpen);
		this.isLoyal = isLoyal;
	}

	/**
	 * Constructor for a new Savings account
	 * @param fname first name of account holder
	 * @param lname last name of account holder
	 */
	public Savings(String fname, String lname) {
		super(fname, lname);
	}

	/**
	 * Calculates monthly interest for a savings account
	 * @return monthly interest
	 */
	@Override
	public double monthlyInterest() {
		// TODO Auto-generated method stub
		if (isLoyal) {
			return this.getBalance() * AccountConstants.SAVINGS_LOYAL_INTEREST / AccountConstants.MONTHS;
		}
		return this.getBalance() * AccountConstants.SAVINGS_INTEREST / AccountConstants.MONTHS;
	}

	/**
	 * calculate monthly fee for a savings account
	 * @return monthly fee
	 */
	@Override
	public double monthlyFee() {
		// TODO Auto-generated method stub
		if (this.getBalance() < AccountConstants.S_WAIVE_BAL) {
			return AccountConstants.SAVINGS_FEE;
		}
		return AccountConstants.WAIVED_FEE;
	}

	/**
	 * Changes savings account information into a string
	 * @return savings info
	 */
	@Override
	public String toString() {
		String endString = "";
		if (this.isLoyal) {
			endString = "*special Savings account*";
		}
		String info = super.toString();
		return "*Savings*" + info + endString;
	}

	/**
	 * Checks if given savings accounts have the same account holder
	 * @return true if equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Savings) {
			Savings account = (Savings) obj;
			if (account.getHolder().equals(this.getHolder())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns if account is a loyal customer
	 * @return true if loyal customer, false otherwise
	 */
	@Override
	public boolean getloyaldirect() {
		return this.isLoyal;
	}
}