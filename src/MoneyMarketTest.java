import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;

/**
 * JUnit test for MoneyMarket class
 * @author Amy Wang, Junhao Shen
 */
class MoneyMarketTest {
	private Date date;
	private MoneyMarket moneyMarket;
	private MoneyMarket moneyMarket2;
	private MoneyMarket moneyMarket3;

	/**
	 * Sets up variables for the test
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		date = new Date(2,21,2020);
		// 1. Checks balance >= 2500 and withdrawals <= 6 - should waive fee
		moneyMarket = new MoneyMarket("Market","Forever",2934.45,date);
		moneyMarket.setWithdrawals(1);
		// 2. Checks balance >= 2500 and withdrawals > 6 - should apply fee
		moneyMarket2 = new MoneyMarket("Jerry","Anderson",2934.45,date);
		moneyMarket2.setWithdrawals(7);
		// 1. Checks balance < 2500 and withdrawals <= 6 - should apply fee
		moneyMarket3 = new MoneyMarket("John", "Doe",1500.20,date);
		moneyMarket3.setWithdrawals(1);
	}

	/**
	 * Tests the monthlyInterest method
	 */
	@Test
	void testMonthlyInterest() {
		DecimalFormat df = new DecimalFormat("#0.00");
		assertEquals("1.59",df.format(moneyMarket.monthlyInterest()));
		assertEquals("1.59",df.format(moneyMarket2.monthlyInterest()));
		assertEquals("0.81",df.format(moneyMarket3.monthlyInterest()));
	}

	/**
	 * Tests the monthlyFee method
	 */
	@Test
	void testMonthlyFee() {
		DecimalFormat df = new DecimalFormat("#0.00");
		assertEquals("0.00",df.format(moneyMarket.monthlyFee())); // >= balance and <= 6 withdrawals - no fee
		assertEquals("12.00",df.format(moneyMarket2.monthlyFee())); // >= balance and > 6 withdrawals - fee
		assertEquals("12.00",df.format(moneyMarket3.monthlyFee()));// < balance and <= 6 withdrawals - fee
	}

}
