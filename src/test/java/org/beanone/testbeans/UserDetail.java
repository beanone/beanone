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
	private List<Address> addresses = new ArrayList<Address>();
	private Map<String, Person> relations = new HashMap<String, Person>();

	public void addAddress(Address address) {
		this.addresses.add(address);
	}

	public void addRelated(String name, Person person) {
		this.relations.put(name, person);
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public Person getPerson() {
		return this.person;
	}

	public int getPosition() {
		return this.position;
	}

	public Map<String, Person> getRelations() {
		return this.relations;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setRelations(Map<String, Person> relations) {
		this.relations = relations;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
