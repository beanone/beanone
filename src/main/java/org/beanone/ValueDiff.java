package org.beanone;

import java.io.Serializable;

/**
 * Stores the value differences in a BeanPatch. A ValueDiff has no meaning when
 * used outside of a {@link BeanPatch}. It is useful only if used inside a
 * BeanPatch.
 * 
 * @author Hongyan Li
 *
 */
public class ValueDiff implements Serializable {
	private static final long serialVersionUID = 750309839430474647L;

	public static ValueDiff makeDiff(Object oldValue, Object newValue) {
		final ValueDiff returns = new ValueDiff();
		returns.oldValue = oldValue;
		returns.newValue = newValue;
		return returns;
	}

	private Object oldValue;

	private Object newValue;

	public ValueDiff() {
		// default constructor to make this a valid JavaBean
	}

	public Object getNewValue() {
		return newValue;
	}

	public Object getOldValue() {
		return oldValue;
	}
}
