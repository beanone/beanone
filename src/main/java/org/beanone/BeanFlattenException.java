package org.beanone;

public class BeanFlattenException extends RuntimeException {
	public BeanFlattenException(String message, Exception e) {
		super(message, e);
	}

	private static final long serialVersionUID = -5035522753136731932L;

}
