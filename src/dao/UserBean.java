package dao;

public class UserBean {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	public boolean valid;
	private int value;
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String newLastName) {
		lastName = newLastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public void removePassword() {
		password = null;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String newUsername) {
		username = newUsername;
	}

	public void removeUserName() {
		username = null;
	}

	public void removeLastName() {
		lastName = null;
	}

	public void removeFirstName() {
		firstName = null;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean newValid) {
		valid = newValid;
	}

	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
// - See more at:
// http://www.codemiles.com/jsp-examples/login-using-jsp-servlets-and-database-following-mvc-t10898.html#sthash.erjdhXpE.dpuf