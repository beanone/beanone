package org.beanone.testbeans;

import java.io.Serializable;

public class BeanWithGetterThrowException implements Serializable {
	private static final long serialVersionUID = -6994421182558094137L;

	public String getValue() {
		throw new IllegalArgumentException();
	}
}
