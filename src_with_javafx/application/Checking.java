package application;

/**
 * Checking class defines a checking account and extends the Account class
 * @author Amy Wang, Junhao Shen
 */
public class Checking extends Account {

	private boolean directDeposit;

	/**
	 * Constructor for a new Checking account
	 * @param fname first name of account holder
	 * @param lname last name of account holder
	 * @param balance of the account
	 * @param dateOpen account open date
	 * @param directDeposit whether or not direct deposit is set up
	 */
	public Checking(String fname, String lname, double balance, Date dateOpen, boolean directDeposit) {
		super(fname, lname, balance, dateOpen);
		this.directDeposit = directDeposit;
	}

	/**
	 * Constructor for a new Checking account
	 * @param fname first name of account holder
	 * @param lname last name of account holder
	 */
	public Checking(String fname, String lname) {
		super(fname, lname);
	}

	/**
	 * Calculates monthly interest for a checking account
	 * @return monthly interest
	 */
	@Override
	public double monthlyInterest() {
		return this.getBalance() * AccountConstants.CHECKING_INTEREST / AccountConstants.MONTHS;
	}

	/**
	 * calculate monthly fee for a checking account
	 * @return monthly fee
	 */
	@Override
	public double monthlyFee() {
		if (this.getBalance() < AccountConstants.C_WAIVE_BAL && !this.directDeposit) {
			return AccountConstants.CHECKING_FEE;
		}
		return AccountConstants.WAIVED_FEE;
	}

	/**
	 * Checks if given checking accounts have the same account holder
	 * @return true if equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Checking) {
			Account account = (Checking) obj;
			if (account.getHolder().equals(this.getHolder())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes checking account information into a string
	 * @return checking info
	 */
	@Override
	public String toString() {
		String endString = "";
		if (this.directDeposit) {
			endString = "*direct deposit account*";
		}
		String info = super.toString();
		return "*Checking*" + info + endString;
	}
	
	/**
	 * Returns if account is direct deposit
	 * @return true if direct deposit, false otherwise
	 */
	@Override
	public boolean getloyaldirect() {
		return this.directDeposit;
	}
}