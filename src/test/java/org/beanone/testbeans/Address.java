package org.beanone.testbeans;

import java.io.Serializable;

public class Address implements Serializable {
	private static final long serialVersionUID = -7200555141567811331L;

	private String streetAddress;
	private String city;
	private int zip;

	public String getCity() {
		return city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public int getZip() {
		return zip;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
}
