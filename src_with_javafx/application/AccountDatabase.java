package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * This class defines the AccountDatabase item, which stores Accounts in an
 * array. It contains methods to alter the Accounts or the account database.
 * 
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
	 * 
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
	 * Helper method to grow Account Database array First checks if full and if
	 * full, grow
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @return the accounts of the database in proper form
	 */
	public String printByDateOpen() {
		String accountInfo = "";
		if (this.size <= 0) {
			accountInfo = "Database is empty.";
			return accountInfo;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		sortByDateOpen();
		accountInfo = accountInfo.concat("--Printing statements by date opened--\n");
		for (int i = 0; i < this.size; ++i) {
			accountInfo = accountInfo.concat("\n" + this.accounts[i].toString() + "\n");
			accountInfo = accountInfo.concat("-interest: $ " + df.format(this.accounts[i].monthlyInterest()) + "\n");
			accountInfo = accountInfo.concat("-fee: $ " + df.format(this.accounts[i].monthlyFee()) + "\n");
			double newbal = this.accounts[i].getBalance() + this.accounts[i].monthlyInterest()
					- this.accounts[i].monthlyFee();
			accountInfo = accountInfo.concat("-new balance: $ " + (df.format(newbal)) + "\n");
			this.accounts[i].setBalance(newbal);
		}
		accountInfo = accountInfo.concat("--end of printing--\n");
		return accountInfo;
	}

	/**
	 * Calls on the sortByLastName method to sort and then prints out the accounts
	 * 
	 * @return the accounts of the database in proper form
	 */
	public String printByLastName() {
		String accountInfo = "";
		if (this.size <= 0) {
			accountInfo = "Database is empty.";
			return accountInfo;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		sortByLastName();
		accountInfo = accountInfo.concat("--Printing statements by last name--\n");
		for (int i = 0; i < this.size; ++i) {
			accountInfo = accountInfo.concat("\n" + this.accounts[i].toString() + "\n");
			accountInfo = accountInfo.concat("-interest: $ " + df.format(this.accounts[i].monthlyInterest()) + "\n");
			accountInfo = accountInfo.concat("-fee: $ " + df.format(this.accounts[i].monthlyFee()) + "\n");
			double newbal = this.accounts[i].getBalance() + this.accounts[i].monthlyInterest()
					- this.accounts[i].monthlyFee();
			accountInfo = accountInfo.concat("-new balance: $ " + (df.format(newbal)) + "\n");
			this.accounts[i].setBalance(newbal);
		}
		accountInfo = accountInfo.concat("--end of printing-- \n");
		return accountInfo;
	}

	/**
	 * Lists out the accounts in the database
	 * 
	 * @return the accounts of the database in proper form
	 */
	public String printAccounts() {
		String accountInfo = "";
		if (this.size <= 0) {
			accountInfo = "Database is empty.";
			return accountInfo;
		}
		accountInfo = accountInfo.concat("--Listing accounts in the database--\n");
		for (int i = 0; i < this.size; ++i) {
			accountInfo = accountInfo.concat(this.accounts[i].toString() + "\n");
		}
		accountInfo = accountInfo.concat("--end of listing--");
		return accountInfo;
	}

	/**
	 * Uses scanner class to read given file, creates accounts with the info and adds them to database
	 * @param imported file to be read from
	 * @param accountDatabase to add imported accounts to
	 * @return error statement or success statement
	 */
	public String readImported(File imported, AccountDatabase accountDatabase) {
		Scanner input;
		try {
			input = new Scanner(imported);
		} catch (Exception e) {
			return "An error occurred while scanning file.";
		}
		boolean isAdded = true;
		while (input.hasNextLine()) {
			int month;
			int day;
			int year;
			double balance;

			String[] accountInfo = input.nextLine().split(",");
			String fname = accountInfo[1];
			String lname = accountInfo[2];

			try {
				balance = Double.parseDouble(accountInfo[3]);
			} catch (Exception e) {
				return "The balance of an account is not properly formatted.";
			}
			String[] dateInfo = accountInfo[4].split("/");

			try {
				month = Integer.parseInt(dateInfo[0]);
				day = Integer.parseInt(dateInfo[1]);
				year = Integer.parseInt(dateInfo[2]);
			} catch (Exception e) {
				return "The date of an account is not properly formatted.";
			}
			Date date = new Date(month, day, year);
			if (!date.isValid()) {
				return (date.toString() + "is not a valid date!");
			}

			if (accountInfo[0].equals("S")) {
				boolean isLoyal = Boolean.parseBoolean(accountInfo[5]);
				Account account = new Savings(fname, lname, balance, date, isLoyal);
				isAdded = accountDatabase.add(account);
			} else if (accountInfo[0].contentEquals("C")) {
				boolean isDirect = Boolean.parseBoolean(accountInfo[5]);
				Account account = new Checking(fname, lname, balance, date, isDirect);
				isAdded = accountDatabase.add(account);
			} else if (accountInfo[0].contentEquals("M")) {
				Account account = new MoneyMarket(fname, lname, balance, date);
				account.setWithdrawals(Integer.parseInt(accountInfo[5]));
				isAdded = accountDatabase.add(account);
			} else {
				return "Invalid account type in file.";
			}
		}
		input.close();
		if (isAdded) {
			return "Successfully imported database, duplicates were ignored.";
		} else {
			return "Unable to import database.";
		}
	}

	/**
	 * Uses FileWriter class to create and write the contents of the database to a text file
	 * @param accountDatabase to export
	 * @return error statement or success statement
	 */
	String writeExported(AccountDatabase accountDatabase) {
		try {
			FileWriter writer = new FileWriter("exportedDatabase.txt");
			String exportInfo = "";
			for (int i = 0; i < accountDatabase.size; i++) {
				String type = "";
				String fname = accountDatabase.accounts[i].getHolder().getFname();
				String lname = accountDatabase.accounts[i].getHolder().getLname();
				double balance = accountDatabase.accounts[i].getBalance();
				String date = accountDatabase.accounts[i].getDateOpen().toString();
				boolean tf = false;
				int withdrawals = 0;

				if (accountDatabase.accounts[i] instanceof Savings) {
					type = "S";
					tf = accountDatabase.accounts[i].getloyaldirect();
					exportInfo = exportInfo
							.concat(type + "," + fname + "," + lname + "," + balance + "," + date + "," + tf + "\n");
				} else if (accountDatabase.accounts[i] instanceof Checking) {
					type = "C";
					tf = accountDatabase.accounts[i].getloyaldirect();
					exportInfo = exportInfo
							.concat(type + "," + fname + "," + lname + "," + balance + "," + date + "," + tf + "\n");
				} else if (accountDatabase.accounts[i] instanceof MoneyMarket) {
					type = "M";
					withdrawals = accountDatabase.accounts[i].getWithdrawals();
					exportInfo = exportInfo.concat(
							type + "," + fname + "," + lname + "," + balance + "," + date + "," + withdrawals + "\n");
				}
			}
			writer.write(exportInfo);
			writer.close();

		} catch (IOException e) {
			return "Database was not successfully exported.";
		}
		return "Successfully exported database.";
	}

}