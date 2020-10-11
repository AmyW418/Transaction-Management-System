/**
 * Profile class defines Profile object
 * @author Amy Wang, Junhao Shen
 */
public class Profile {

	private String fname;
	private String lname;

	/**
	 * Constructor for a new Profile object
	 * @param fname of account holder
	 * @param lname of account holder
	 */
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	/**
	 * Gets first name of account holder
	 * @return fname of account holder
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * Gets last name of account holder
	 * @return lname of account holder
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * Checks if first and last name of given Profiles are equal
	 * @param obj to be compared
	 * @return true if equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Profile) {
			Profile profile = (Profile) obj;
			if (this.fname.equals(profile.getFname()) && this.lname.equals(profile.getLname())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Rewrite Profile information in string format
	 * @return profile info
	 */
	@Override
	public String toString() {
		return this.fname + " " + this.lname;
	}
}
