package org.beanone.testbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetail implements Serializable {
	private static final long serialVersionUID = 8784496857799810886L;

	private int position;
	private String userId;
	private Person person;
	private final List<Address> addresses = new ArrayList<Address>();
	private final Map<String, Person> relations = new HashMap<String, Person>();

	public void addAddress(Address address) {
		addresses.add(address);
	}

	public void addRelated(String name, Person person) {
		relations.put(name, person);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public Person getPerson() {
		return person;
	}

	public int getPosition() {
		return position;
	}

	public Map<String, Person> getRelations() {
		return relations;
	}

	public String getUserId() {
		return userId;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
