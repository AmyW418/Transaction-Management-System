import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * The TransactionManager class handles input and output to the console. Takes
 * in commands given by user and calls on other classes and methods accordingly.
 * @author Junhao Shen, Amy Wang
 */
public class TransactionManager {
	private AccountDatabase accountDatabase;

	/**
	 * Default constructor that initializes the accountDatabase.
	 */
	public TransactionManager() {
		this.accountDatabase = new AccountDatabase();
	}

	/**
	 * Handles different commands in I/O for the program. Uses scanner class to read
	 * user input and process commands, calling on respective methods to
	 * successfully open, close, deposit, withdraw, print, and quit.
	 */
	public void run() {
		Scanner input = new Scanner(System.in);
		System.out.println("Transaction processing starts.....");
		while (input.hasNextLine()) {
			String operateFlag = input.nextLine();
			if (operateFlag.contentEquals("")) {
				continue;
			} else if (operateFlag.substring(0, 1).equals("O")) { // If first char is O, follow sequence for open
				this.openAccount(operateFlag);
			} else if (operateFlag.substring(0, 1).equals("C")) { // If first char is C, follow sequence for close
				this.closeAccount(operateFlag);
			} else if (operateFlag.substring(0, 1).equals("D")) { // If first char is D, follow sequence for deposit
				this.deposit(operateFlag);
			} else if (operateFlag.substring(0, 1).equals("W")) { // If first char is W, follow sequence for withdraw
				this.withdraw(operateFlag);
			} else if (operateFlag.substring(0, 1).equals("P")) { // If first char is P, follow sequence for print
				this.print(operateFlag);
			} else if (operateFlag.substring(0, 1).equals("Q")) { // If first char is Q, follow sequence for quit
				System.out.println("\nTransaction processing completed.");
				break;
			} else { // If first char is doesn't match, print invalid command
				System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
			}
		}
	}

	/**
	 * This method contains operations to add an account to the database if it doesn't already exist
	 * @param operateFlag input command
	 */
	private void openAccount(String operateFlag) {
		String[] strArrs = operateFlag.split(" ");
		String[] dateArrs = strArrs[4].split("/");
		Date date = new Date(Integer.parseInt(dateArrs[0]), Integer.parseInt(dateArrs[1]),
				Integer.parseInt(dateArrs[2]));
		double amount;
		try {
			amount = Double.parseDouble(strArrs[3]);
		} catch (Exception e) {
			System.out.println("Input data type mismatch.");
			return;
		}
		if (!date.isValid()) {
			System.out.println(date.toString() + " is not a valid date!");
			return;
		}
		boolean result = false;
		if (operateFlag.substring(1, 2).equals("C")) { // If second char is C, follow sequence for Checking Account
			String directDepositStr = strArrs[5];
			boolean directDeposit = false;
			if (directDepositStr.toUpperCase().equals("TRUE")) {
				directDeposit = true;
			} else if (directDepositStr.toUpperCase().equals("FALSE")) {
				directDeposit = false;
			} else {
				System.out.println("Input data type mismatch.");
				return;
			}
			Account account = new Checking(strArrs[1], strArrs[2], Double.parseDouble(strArrs[3]), date, directDeposit);
			result = this.accountDatabase.add(account);
		} else if (operateFlag.substring(1, 2).equals("S")) { // If second char is S, follow sequence for Savings Account
			String isLoyalStr = strArrs[5];
			boolean isLoyal = false;
			if (isLoyalStr.toUpperCase().equals("TRUE")) {
				isLoyal = true;
			} else if (isLoyalStr.toUpperCase().equals("FALSE")) {
				isLoyal = false;
			} else {
				System.out.println("Input data type mismatch.");
				return;
			}
			Account account = new Savings(strArrs[1], strArrs[2], Double.parseDouble(strArrs[3]), date, isLoyal);
			result = this.accountDatabase.add(account);
		} else if (operateFlag.substring(1, 2).equals("M")) { // If second char is M, follow sequence for MoneyMarket Account
			Account account = new MoneyMarket(strArrs[1], strArrs[2], Double.parseDouble(strArrs[3]), date);
			result = this.accountDatabase.add(account);
		} else {
			System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
			return;
		}
		if (result) {
			System.out.println("Account opened and added to the database.");
		} else {
			System.out.println("Account is already in the database.");
		}
	}

	/**
	 * This method contains operations to remove an account from the database if it exist
	 * @param operateFlag input command
	 */
	private void closeAccount(String operateFlag) {
		String[] strArrs = operateFlag.split(" ");
		boolean result = true;
		if (operateFlag.substring(1, 2).equals("C")) { // If second char is C, follow sequence for Checking Account
			Account account = new Checking(strArrs[1], strArrs[2]);
			result = this.accountDatabase.remove(account);
		} else if (operateFlag.substring(1, 2).equals("S")) { // If second char is S, follow sequence for Savings Account
			Account account = new Savings(strArrs[1], strArrs[2]);
			result = this.accountDatabase.remove(account);
		} else if (operateFlag.substring(1, 2).equals("M")) { // If second char is M, follow sequence for MoneyMarket Account
			Account account = new MoneyMarket(strArrs[1], strArrs[2]);
			result = this.accountDatabase.remove(account);
		} else {
			System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
			return;
		}
		if (!result) {
			System.out.println("Account does not exist.");
		} else {
			System.out.println("Account closed and removed from the database.");
		}
	}

	/**
	 * This method contains operations to make a deposit into an account if it exists
	 * @param operateFlag input command
	 */
	private void deposit(String operateFlag) {
		String[] strArrs = operateFlag.split(" ");
		boolean result = true;
		double amount;
		try {
			amount = Double.parseDouble(strArrs[3]);
		} catch (Exception e) {
			System.out.println("Input data type mismatch.");
			return;
		}
		if (operateFlag.substring(1, 2).equals("C")) { // If second char is C, follow sequence for Checking Account
			Account account = new Checking(strArrs[1], strArrs[2]);
			result = this.accountDatabase.deposit(account, amount);
		} else if (operateFlag.substring(1, 2).equals("S")) { // If second char is S, follow sequence for Savings Account
			Account account = new Savings(strArrs[1], strArrs[2]);
			result = this.accountDatabase.deposit(account, amount);
		} else if (operateFlag.substring(1, 2).equals("M")) { // If second char is M, follow sequence for MoneyMarket Account
			Account account = new MoneyMarket(strArrs[1], strArrs[2]);
			result = this.accountDatabase.deposit(account, amount);
		} else {
			System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
			return;
		}
		if (result) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			System.out.println(df.format(amount) + " deposited to account.");
		} else {
			System.out.println("Account does not exist.");
		}
	}

	/**
	 * This method contains operations to withdraw from an account to the database if it exists
	 * @param operateFlag input command
	 */
	private void withdraw(String operateFlag) {
		String[] strArrs = operateFlag.split(" ");
		int result;
		double amount;
		try {
			amount = Double.parseDouble(strArrs[3]);
		} catch (Exception e) {
			System.out.println("Input data type mismatch.");
			return;
		}
		if (operateFlag.substring(1, 2).equals("C")) { // If second char is C, follow sequence for Checking Account
			Account account = new Checking(strArrs[1], strArrs[2]);
			result = this.accountDatabase.withdrawal(account, amount);
		} else if (operateFlag.substring(1, 2).equals("S")) { // If second char is S, follow sequence for Savings Account
			Account account = new Savings(strArrs[1], strArrs[2]);
			result = this.accountDatabase.withdrawal(account, amount);
		} else if (operateFlag.substring(1, 2).equals("M")) { // If second char is M, follow sequence for MoneyMarket Account
			Account account = new MoneyMarket(strArrs[1], strArrs[2]);
			result = this.accountDatabase.withdrawal(account, amount);
		} else {
			System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
			return;
		}
		if (result == AccountConstants.SUCCESS) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			System.out.println(df.format(amount) + " withdrawn from account.");
		} else if (result == AccountConstants.NOT_EXIST) {
			System.out.println("Account does not exist");
		} else {
			System.out.println("Insufficient funds.");
		}
	}

	/**
	 * This method contains operations to print out account database
	 * @param operateFlag input command
	 */
	private void print(String operateFlag) {
		if (operateFlag.substring(1, 2).equals("A")) { // If second char is A, print list
			this.accountDatabase.printAccounts();
		} else if (operateFlag.substring(1, 2).equals("D")) { // If second char is D, print by date
			this.accountDatabase.printByDateOpen();
		} else if (operateFlag.substring(1, 2).equals("N")) { // If second char is N, print by last name
			this.accountDatabase.printByLastName();
		} else {
			System.out.println("Command '" + operateFlag.split(" ")[0] + "' not supported!");
		}
	}
}
