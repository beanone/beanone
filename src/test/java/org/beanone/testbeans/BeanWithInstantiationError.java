package org.beanone.testbeans;

public class BeanWithInstantiationError {
	public BeanWithInstantiationError() throws InstantiationException {
		throw new InstantiationException();
	}
}
