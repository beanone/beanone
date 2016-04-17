package org.beanone.testbeans;

import java.io.Serializable;

public class BeanWithSimpleAttributes implements Serializable {
	private static final long serialVersionUID = -776760960898452272L;
	private int integer = 1;
	private Integer intObject = 2;
	private String string = "A string";
	private Object doubleAsObject = new Double(3);

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public Integer getIntObject() {
		return intObject;
	}

	public void setIntObject(Integer intObject) {
		this.intObject = intObject;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Object getDoubleAsObject() {
		return doubleAsObject;
	}

	public void setDoubleAsObject(Object doubleAsObject) {
		this.doubleAsObject = doubleAsObject;
	}
}
