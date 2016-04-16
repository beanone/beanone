package org.beanone.testbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
	private static final long serialVersionUID = -5001884724414260401L;

	private String firstName;
	private String lastName;
	private final List<String> emailAddresses = new ArrayList<String>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getEmailAddresses() {
		return emailAddresses;
	}

	public void addEmailAddress(String emailAddress) {
		this.emailAddresses.add(emailAddress);
	}
}
