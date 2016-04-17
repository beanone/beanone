package org.beanone;

/**
 * Exception when trying to flatten a JavaBean into a flattened map structure.
 * 
 * @author Hongyan Li
 *
 */
public class BeanFlattenException extends RuntimeException {
	public BeanFlattenException(String message, Exception e) {
		super(message, e);
	}

	private static final long serialVersionUID = -5035522753136731932L;

}
