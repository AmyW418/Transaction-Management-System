/**
 * MoneyMarket class defines a money market account and extends the Account class
 * @author Amy Wang, Junhao Shen
 */
public class MoneyMarket extends Account {
	private int withdrawals;

	/**
	 * Constructor for a new MoneyMarket object
	 * @param fname    first name of account holder
	 * @param lname    last name of account holder
	 * @param balance  of account
	 * @param dateOpen date account was opened
	 */
	public MoneyMarket(String fname, String lname, double balance, Date dateOpen) {
		super(fname, lname, balance, dateOpen);
		this.withdrawals = AccountConstants.INITIAL_WITHDRAW;
	}

	/**
	 * Constructor for a new MoneyMarket object
	 * @param fname first name of account holder
	 * @param lname last name of account holder
	 */
	public MoneyMarket(String fname, String lname) {
		super(fname, lname);
	}

	/**
	 * This method decreases the balance by the given amount and adds a withdrawal
	 */
	@Override
	public void debit(double amount) {
		super.debit(amount);
		this.withdrawals += 1;
	}

	/**
	 * Calculates monthly interest for a money market account
	 * @return monthly interest
	 */
	@Override
	public double monthlyInterest() {
		// TODO Auto-generated method stub
		return this.getBalance() * AccountConstants.MM_INTEREST / AccountConstants.MONTHS;
	}

	/**
	 * Calculates monthly fee for a money market account
	 * @return monthly fee
	 */
	@Override
	public double monthlyFee() {
		// TODO Auto-generated method stub
		if (this.getBalance() < AccountConstants.MM_WAIVE_BAL || this.withdrawals > AccountConstants.WITHDRAW_MAX) {
			return AccountConstants.MM_FEE;
		}
		return AccountConstants.WAIVED_FEE;
	}

	/**
	 * Rewrites money market information in string format
	 * @return money market info
	 */
	@Override
	public String toString() {
		String withdraw = " withdrawals*";
		if (this.withdrawals == 1) {
			withdraw = " withdrawal*";
		}
		String info = super.toString();
		return "*Money Market*" + info + "*" + this.withdrawals + withdraw;
	}

	/**
	 * Checks if given money market accounts have same holder
	 * @return true if same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MoneyMarket) {
			MoneyMarket account = (MoneyMarket) obj;
			if (account.getHolder().equals(this.getHolder())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets number of withdrawals
	 * @return withdrawals
	 */
	public int getWithdrawals() {
		return withdrawals;
	}

	/**
	 * Sets number of withdrawals
	 * @param withdrawals to set
	 */
	public void setWithdrawals(int withdrawals) {
		this.withdrawals = withdrawals;
	}
}
