import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for testing Date class
 * @author Junhao Shen, Amy Wang
 */
class DateTest {

	private Date date;
	private Date date2;
	
	/**
	 * Sets up variables for the test
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		date = new Date(13,1,2010); // Invalid month
		date2 = new Date(1,13,2010); // proper date
	}

	/**
	 * Test the IsValid method
	 */
	@Test
	void testIsValid() {
		assertFalse(date.isValid());
		assertTrue(date2.isValid());
	}

}



