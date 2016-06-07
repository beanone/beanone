package org.beanone.testbeans;

public class BeanWithGetterThrowException {
	public String getValue() {
		throw new IllegalArgumentException();
	}
}
