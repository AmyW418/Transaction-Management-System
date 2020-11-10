package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Controller for Project 3 / Transaction Management System
 * Connects the view and model
 * @author Amy Wang, Junhao Shen
 */
public class Project3Controller {

	private AccountDatabase accountDatabase = new AccountDatabase();

	@FXML
	private TextField ocFirstName, ocLastName, ocMonth, ocDay, ocYear, ocBalance;

	@FXML
	private Button ocOpenButton, ocCloseButton, ocClearButton;

	@FXML
	private RadioButton ocSavings, ocMoneyMarket, ocCheckings;

	@FXML
	private ToggleGroup accountType;

	@FXML
	private CheckBox ocDirectDeposit, ocLoyalCustomer;

	@FXML
	private RadioButton wdCheckings, wdSavings, wdMoneyMarket;

	@FXML
	private ToggleGroup wdAccount;

	@FXML
	private Button wdDepositButton, wdWithdrawButton;

	@FXML
	private TextField wdFirstName, wdLastName, wdAmount;

	@FXML
	private MenuItem accAccounts, accByDate, accByLastName;

	@FXML
	private Button accImport, accExport;

	@FXML
	private TextArea textArea;

	/**
	 * enables check box for direct deposit, disables loyal customer
	 * 
	 * @param event when selected
	 */
	@FXML
	void checking(ActionEvent event) {
		ocDirectDeposit.setDisable(false);
		ocLoyalCustomer.setDisable(true);
	}

	/**
	 * enables check box for loyal customer, disables direct deposit
	 * 
	 * @param event when selected
	 */
	@FXML
	void savings(ActionEvent event) {
		ocDirectDeposit.setDisable(true);
		ocLoyalCustomer.setDisable(false);
	}

	/**
	 * disables check box for both direct deposit and loyal customer
	 * 
	 * @param event when selected
	 */
	@FXML
	void moneymarket(ActionEvent event) {
		ocDirectDeposit.setDisable(true);
		ocLoyalCustomer.setDisable(true);
	}

	/**
	 * clears text fields and text area
	 * 
	 * @param event when clicked
	 */
	@FXML
	void clear(ActionEvent event) {
		clearoc();
	}

	/**
	 * checks text fields for proper inputs and calls remove method to close
	 * accounts
	 * 
	 * @param event when clicked
	 */
	@FXML
	void close(ActionEvent event) {
		if (ocFirstName.getText().trim().equals("") || ocLastName.getText().trim().equals("")) {
			textArea.appendText("Please enter a first name and last name!\n");
			return;
		}
		String fname = ocFirstName.getText();
		String lname = ocLastName.getText();
		boolean isRemoved = true;
		if (ocSavings.isSelected()) {
			Account account = new Savings(fname, lname);
			isRemoved = accountDatabase.remove(account);
		} else if (ocCheckings.isSelected()) {
			Account account = new Checking(fname, lname);
			isRemoved = accountDatabase.remove(account);
		} else if (ocMoneyMarket.isSelected()) {
			Account account = new MoneyMarket(fname, lname);
			isRemoved = accountDatabase.remove(account);
		} else {
			textArea.appendText("Please select account type!\n");
			return;
		}
		clearoc();
		if (!isRemoved) {
			textArea.appendText("Account does not exist.\n");
			return;
		} else {
			textArea.appendText("Account closed and removed from the database.\n");
			return;
		}
	}

	/**
	 * checks text fields for proper inputs and calls withdrawal method to withdraw
	 * 
	 * @param event when clicked
	 */
	@FXML
	void withdraw(ActionEvent event) {
		if (wdFirstName.getText().trim().equals("") || wdLastName.getText().trim().equals("")) {
			textArea.appendText("Please enter a first name and last name!\n");
			return;
		}
		String fname = wdFirstName.getText();
		String lname = wdLastName.getText();
		double amount;
		try {
			amount = Double.parseDouble(wdAmount.getText());
		} catch (Exception e) {
			textArea.appendText("Given amount is not in improper format!\n");
			return;
		}
		int result;
		if (wdSavings.isSelected()) {
			Account account = new Savings(fname, lname);
			result = accountDatabase.withdrawal(account, amount);
		} else if (wdCheckings.isSelected()) {
			Account account = new Checking(fname, lname);
			result = accountDatabase.withdrawal(account, amount);
		} else if (wdMoneyMarket.isSelected()) {
			Account account = new MoneyMarket(fname, lname);
			result = accountDatabase.withdrawal(account, amount);
		} else {
			textArea.appendText("Please select account type! \n");
			return;
		}
		clearwd();
		if (result == AccountConstants.SUCCESS) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			textArea.appendText("$" + df.format(amount) + " withdrawn from account.\n");
		} else if (result == AccountConstants.NOT_EXIST) {
			textArea.appendText("Account does not exist.\n");
		} else {
			textArea.appendText("Insufficient funds.\n");
		}

	}

	/**
	 * checks text fields for proper inputs and calls add method to open accounts
	 * 
	 * @param event when clicked
	 */
	@FXML
	void open(ActionEvent event) {
		if (ocFirstName.getText().trim().equals("") || ocLastName.getText().trim().equals("")) {
			textArea.appendText("Please enter a first name and last name!\n");
			return;
		}
		String fname = ocFirstName.getText();
		String lname = ocLastName.getText();
		int month;
		int day;
		int year;
		double balance;
		try {
			balance = Double.parseDouble(ocBalance.getText());
		} catch (Exception e) {
			textArea.appendText("Given balance is not properly formatted!\n");
			return;
		}
		try {
			month = Integer.parseInt(ocMonth.getText());
			day = Integer.parseInt(ocDay.getText());
			year = Integer.parseInt(ocYear.getText());
		} catch (Exception e) {
			textArea.appendText("Given date is not properly formatted! \n");
			return;
		}
		Date date = new Date(month, day, year);
		if (!date.isValid()) {
			textArea.appendText(date.toString() + " is not a valid date! \n");
			return;
		}
		boolean isAdded = false;
		if (ocSavings.isSelected()) {
			boolean isLoyal;
			if (ocLoyalCustomer.isSelected()) {
				isLoyal = true;
			} else {
				isLoyal = false;
			}
			Account account = new Savings(fname, lname, balance, date, isLoyal);
			isAdded = accountDatabase.add(account);

		} else if (ocCheckings.isSelected()) {
			boolean isDirect;
			if (ocDirectDeposit.isSelected()) {
				isDirect = true;
			} else {
				isDirect = false;
			}
			Account account = new Checking(fname, lname, balance, date, isDirect);
			isAdded = accountDatabase.add(account);

		} else if (ocMoneyMarket.isSelected()) {
			Account account = new MoneyMarket(fname, lname, balance, date);
			isAdded = accountDatabase.add(account);

		} else {
			textArea.appendText("Please select account type! \n");
			return;
		}
		clearoc();
		if (!isAdded) {
			textArea.appendText("Account is already in the database.\n");
			return;
		} else {
			textArea.appendText("Account opened and added to the database. \n");
			return;
		}
	}

	/**
	 * calls printAccounts method to print account statements
	 * 
	 * @param event when clicked
	 */
	@FXML
	void printAccount(ActionEvent event) {
		String accountInfo = accountDatabase.printAccounts();
		textArea.appendText(accountInfo + "\n");
	}

	/**
	 * calls printByDateOpen method to print database sorted by date
	 * 
	 * @param event when clicked
	 */
	@FXML
	void printByDate(ActionEvent event) {
		String accountInfo = accountDatabase.printByDateOpen();
		textArea.appendText(accountInfo + "\n");
	}

	/**
	 * calls printByLastName method to print database sorted by last name
	 * 
	 * @param event when clicked
	 */
	@FXML
	void printByLastName(ActionEvent event) {
		String accountInfo = accountDatabase.printByLastName();
		textArea.appendText(accountInfo + "\n");
	}

	/**
	 * checks text fields for proper inputs and calls deposit method to deposit to
	 * accounts
	 * 
	 * @param event when clicked
	 */
	@FXML
	void deposit(ActionEvent event) {
		if (wdFirstName.getText().trim().equals("") || wdLastName.getText().trim().equals("")) {
			textArea.appendText("Please enter a first name and last name! \n");
			return;
		}
		String fname = wdFirstName.getText();
		String lname = wdLastName.getText();
		double amount;
		try {
			amount = Double.parseDouble(wdAmount.getText());
		} catch (Exception e) {
			textArea.appendText("Given amount is not properly formatted! \n");
			return;
		}
		boolean result = true;
		if (wdSavings.isSelected()) {
			Account account = new Savings(fname, lname);
			result = accountDatabase.deposit(account, amount);
		} else if (wdCheckings.isSelected()) {
			Account account = new Checking(fname, lname);
			result = accountDatabase.deposit(account, amount);
		} else if (wdMoneyMarket.isSelected()) {
			Account account = new MoneyMarket(fname, lname);
			result = accountDatabase.deposit(account, amount);
		} else {
			textArea.appendText("Please select account type! \n");
			return;
		}
		clearwd();
		if (result) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			textArea.appendText("$" + df.format(amount) + " deposited to account. \n");
		} else {
			textArea.appendText("Account does not exist. \n");
		}

	}

	/**
	 * uses FileChooser class to open a file and calls readImported method to add to
	 * database
	 * 
	 * @param event when clicked
	 */
	@FXML
	void importFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fc.getExtensionFilters().add(fileExtensions);

		File selected = fc.showOpenDialog(null);

		if (selected != null) {
			String result = accountDatabase.readImported(selected, accountDatabase);
			textArea.appendText(result + "\n");
		} else {
			textArea.appendText("Unable to import file! \n");
		}

	}

	/**
	 * calls writeExported method to write database to a text file
	 * 
	 * @param event when clicked
	 */
	@FXML
	void exportFile(ActionEvent event) {
		String result = accountDatabase.writeExported(accountDatabase);
		textArea.appendText(result + "\n");
	}

	/**
	 * clears text fields, radio buttons, and check boxes on open close tab
	 */
	void clearoc() {
		ocFirstName.setText("");
		ocLastName.setText("");
		ocMonth.setText("");
		ocDay.setText("");
		ocYear.setText("");
		ocBalance.setText("");
		ocSavings.setSelected(false);
		ocCheckings.setSelected(false);
		ocMoneyMarket.setSelected(false);
		ocDirectDeposit.setSelected(false);
		ocLoyalCustomer.setSelected(false);
		ocDirectDeposit.setDisable(true);
		ocLoyalCustomer.setDisable(true);
	}

	/**
	 * clears text fields and radio buttons on deposit / withdrawal tab
	 */
	void clearwd() {
		wdFirstName.setText("");
		wdLastName.setText("");
		wdAmount.setText("");
		wdSavings.setSelected(false);
		wdCheckings.setSelected(false);
		wdMoneyMarket.setSelected(false);
	}

	

}
