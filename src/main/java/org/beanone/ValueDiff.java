package org.beanone;

import java.io.Serializable;

/**
 * Stores the value differences in a BeanPatch. A ValueDiff has no meaning when
 * used outside of a {@link BeanPatch}. It is useful only if used inside a
 * BeanPatch.
 *
 * <p>
 * This is immutable.
 * </P>
 *
 * @author Hongyan Li
 *
 */
public class ValueDiff implements Serializable {
	private static final long serialVersionUID = 750309839430474647L;

	// note that if these member attributes are declared as Serializable, then
	// Gson is going to fail on de-serialization.
	private String oldValue;

	private String newValue;
	public ValueDiff() {
		// default constructor to make this a valid JavaBean
	}

	public static ValueDiff makeDiff(String oldValue, String newValue) {
		final ValueDiff returns = new ValueDiff();
		returns.oldValue = oldValue;
		returns.newValue = newValue;
		return returns;
	}

	public String getNewValue() {
		return newValue;
	}

	public String getOldValue() {
		return oldValue;
	}
}
