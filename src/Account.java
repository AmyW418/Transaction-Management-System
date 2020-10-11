import java.text.DecimalFormat;

/**
 * The abstract class Account defines the common features for all type of accounts.
 * @author Amy Wang, Junhao Shen
 */
public abstract class Account {
	private Profile holder;
	private double balance;
	private Date dateOpen;

	/**
	 * Constructor for a new Account object
	 * @param fname    first name of account holder
	 * @param lname    last name of account holder
	 * @param balance  of account
	 * @param dateOpen date account was opened
	 */
	public Account(String fname, String lname, double balance, Date dateOpen) {
		this.holder = new Profile(fname, lname);
		this.balance = balance;
		this.dateOpen = dateOpen;
	}

	/**
	 * Constructor for a new Account object
	 * @param fname first name of account holder
	 * @param lname last name of account holder
	 */
	public Account(String fname, String lname) {
		this.holder = new Profile(fname, lname);
	}

	/**
	 * This method decreases the balance by the given amount
	 * @param amount to decrease by
	 */
	public void debit(double amount) {
		this.balance -= amount;
	}

	/**
	 * This method increases the balance by the given amount
	 * @param amount to increase by
	 */
	public void credit(double amount) {
		this.balance += amount;
	}

	/**
	 * Get the Profile of the account holder - first and last name
	 * @return Profile object of Account object
	 */
	public Profile getHolder() {
		return this.holder;
	}

	/**
	 * Get the balance of the account
	 * @return balance
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * Set new balance as given balance
	 * @param newbal to change balance to
	 */
	public void setBalance(double newbal) {
		this.balance = newbal;
	}

	/**
	 * Get open date of account
	 * @return open date
	 */
	public Date getDateOpen() {
		return this.dateOpen;
	}

	/**
	 * Changes Account information to String format
	 * @return account in info - name, balance, date
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return this.getHolder().toString() + "* $" + df.format(this.getBalance()) + "*" + this.getDateOpen().toString();
	}

	/**
	 * Checks if object given is same as the Account
	 * @return true if the same, false if not the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account account = (Account) obj;
			if (account.getHolder().equals(this.getHolder())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates the monthly interest for an account
	 * @return monthly interest
	 */
	public abstract double monthlyInterest();

	/**
	 * Calculates the monthly fee for an account
	 * @return monthly fee
	 */
	public abstract double monthlyFee();
}