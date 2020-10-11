import java.text.DecimalFormat;

/**
 * This class defines the AccountDatabase item, which stores Accounts in an
 * array. It contains methods to alter the Accounts or the account database.
 * @author Junhao Shen, Amy Wang
 */
public class AccountDatabase {
	private Account[] accounts;
	private int size;

	/**
	 * Constructor to initialize Account Database Sets initial bag capacity to five
	 * and amount of accounts in array to zero
	 */
	public AccountDatabase() {
		this.size = AccountConstants.SIZE;
		this.accounts = new Account[AccountConstants.CAPACITY];
	}

	/**
	 * Helper method find index of an account in account database if it exists
	 * @param account to be found
	 * @return index if found, -1 if not found
	 */
	private int find(Account account) {
		for (int i = 0; i < this.size; ++i) {
			if (this.accounts[i].equals(account)) {
				return i;
			}
		}
		return AccountConstants.NOT_EXIST;
	}

	/**
	 * Helper method to grow Account Database array 
	 * First checks if full and if full, grow
	 */
	private void grow() {
		if (this.size >= this.accounts.length) {
			Account[] newAccounts = new Account[this.size + AccountConstants.CAPACITY];
			for (int i = 0; i < this.size; ++i) {
				newAccounts[i] = this.accounts[i];
			}
			this.accounts = newAccounts;
		}
	}

	/**
	 * This method adds an account to the database if it doesn't already exist
	 * @param account to be added
	 * @return true if successfully added, false if account already exists
	 */
	public boolean add(Account account) {
		int index = this.find(account);
		if (index != AccountConstants.NOT_EXIST) {
			return false;
		}
		this.grow();
		this.accounts[this.size++] = account;
		return true;
	}

	/**
	 * This method removes an account from account database if it exists
	 * @param account to be removed
	 * @return true if successfully removed, false if account does not exist
	 */
	public boolean remove(Account account) {
		int index = this.find(account);
		if (index == AccountConstants.NOT_EXIST) {
			return false;
		}
		if (index == this.size - 1) {
			this.accounts[index] = null;
		} else {
			this.size -= 1;
			Account temp = this.accounts[this.size];
			this.accounts[index] = temp;
			this.accounts[this.size] = null;
		}
		return true;
	}

	/**
	 * This method deposits a given amount to a certain Account
	 * @param account to add deposit to
	 * @param amount  to be deposited
	 * @return true if successful, false if account does not exist
	 */
	public boolean deposit(Account account, double amount) {
		int index = this.find(account);
		if (index == AccountConstants.NOT_EXIST) {
			return false;
		}
		this.accounts[index].credit(amount);
		return true;
	}

	/**
	 * This method withdraws a given amount from a certain Account
	 * @param account to withdraw from
	 * @param amount  to be withdrawn
	 * @return -1 if account doesn't exist, 0 if successful, 1 if insufficient funds
	 */
	public int withdrawal(Account account, double amount) {
		int index = this.find(account);
		if (index == AccountConstants.NOT_EXIST) {
			return AccountConstants.NOT_EXIST;
		}
		if (this.accounts[index].getBalance() < amount) {
			return 1;
		}
		this.accounts[index].debit(amount);
		return 0;
	}

	/**
	 * Sorts the accounts in account database by date in ascending order Calls the
	 * compareTo method in Date class to compare the dates
	 */
	private void sortByDateOpen() {
		for (int i = 0; i < this.size - 1; ++i) {
			for (int j = 0; j < this.size - i - 1; ++j) {
				if (this.accounts[j].getDateOpen().compareTo(this.accounts[j + 1].getDateOpen()) > 0) {
					Account tempAccount = this.accounts[j];
					this.accounts[j] = this.accounts[j + 1];
					this.accounts[j + 1] = tempAccount;
				}
			}
		}
	}

	/**
	 * Sorts the accounts in account database by last name in alphabetical order
	 */
	private void sortByLastName() {
		for (int i = 0; i < this.size - 1; ++i) {
			for (int j = 0; j < this.size - i - 1; ++j) {
				if (this.accounts[j].getHolder().getLname()
						.compareTo(this.accounts[j + 1].getHolder().getLname()) > 0) {
					Account tempAccount = this.accounts[j];
					this.accounts[j] = this.accounts[j + 1];
					this.accounts[j + 1] = tempAccount;
				}
			}
		}
	}

	/**
	 * Calls on the sortByDateOpen method to sort and then prints out the accounts
	 */
	public void printByDateOpen() {
		if (this.size <= 0) {
			System.out.println("Database is empty.");
			return;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		sortByDateOpen();
		System.out.println("\n--Printing statements by date opened--");
		for (int i = 0; i < this.size; ++i) {
			System.out.println("\n" + this.accounts[i].toString());
			System.out.println("-interest: $ " + df.format(this.accounts[i].monthlyInterest()));
			System.out.println("-fee: $ " + df.format(this.accounts[i].monthlyFee()));
			double newbal = this.accounts[i].getBalance() + this.accounts[i].monthlyInterest()
					- this.accounts[i].monthlyFee();
			System.out.println("-new balance: $ " + (df.format(newbal)));
			this.accounts[i].setBalance(newbal);
		}
		System.out.println("--end of printing--");
	}

	/**
	 * Calls on the sortByLastName method to sort and then prints out the accounts
	 */
	public void printByLastName() {
		if (this.size <= 0) {
			System.out.println("Database is empty.");
			return;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		sortByLastName();
		System.out.println("\n--Printing statements by last name--");
		for (int i = 0; i < this.size; ++i) {
			System.out.println("\n" + this.accounts[i].toString());
			System.out.println("-interest: $ " + df.format(this.accounts[i].monthlyInterest()));
			System.out.println("-fee: $ " + df.format(this.accounts[i].monthlyFee()));
			double newbal = this.accounts[i].getBalance() + this.accounts[i].monthlyInterest()
					- this.accounts[i].monthlyFee();
			System.out.println("-new balance: $ " + (df.format(newbal)));
			this.accounts[i].setBalance(newbal);
		}
		System.out.println("--end of printing--");
	}


	/**
	 *  Lists out the accounts in the database
	 */
	public void printAccounts() {
		if (this.size <= 0) {
			System.out.println("Database is empty.");
			return;
		}
		System.out.println("--Listing accounts in the database--");
		for (int i = 0; i < this.size; ++i) {
			System.out.println(this.accounts[i].toString());
		}
		System.out.println("--end of listing--");
	}
}