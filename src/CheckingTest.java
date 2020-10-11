import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;

/**
 * JUnit test for Checking class
 * @author Amy Wang, Junhao Shen
 */
class CheckingTest {

	private Date date;
	private Checking checking;
	private Checking checking2;
	private Checking checking3;
	
	/**
	 * Sets up variables for the test
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		date = new Date(9,23,2020);
		// 1. Checks balance < 1500 and no direct deposit  - should apply fee
		checking = new Checking("Jerry","Anderson",1001.40,date,false); 
		// 2. Checks balance < 1500 and has direct deposit - should waive fee
		checking2 = new Checking("Bob", "Anderson", 1001.40,date,true); 
		// 3. Checks balance >= 1500 and no direct deposit - should waive fee
		checking3 = new Checking("John", "Doe", 1600,date,false);
	}

	/**
	 * Tests the monthlyInterest method
	 */
	@Test
	void testMonthlyInterest() {
		DecimalFormat df = new DecimalFormat("#0.00");
		assertEquals("0.04",df.format(checking.monthlyInterest()));
		assertEquals("0.04",df.format(checking2.monthlyInterest()));
		assertEquals("0.07",df.format(checking3.monthlyInterest()));
	}

	/**
	 * Tests the monthlyFee method
	 */
	@Test
	void testMonthlyFee() {
		DecimalFormat df = new DecimalFormat("#0.00");
		assertEquals("25.00",df.format(checking.monthlyFee())); // < balance and no direct deposit - fee
		assertEquals("0.00",df.format(checking2.monthlyFee())); // < balance and yes direct deposit - no fee
		assertEquals("0.00",df.format(checking3.monthlyFee())); // > balance and no direct deposit - no fee
	}

}
