package org.beanone;

public class Key {
	private KeyType type;
	private String name;

	public Key(String name, KeyType type) {
		this.name = name;
		this.type = type;
	}

	public KeyType getType() {
		return type;
	}

	public void setType(KeyType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
