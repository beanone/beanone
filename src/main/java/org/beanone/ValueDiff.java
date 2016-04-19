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

	public static ValueDiff makeDiff(Object oldValue, Object newValue) {
		final ValueDiff returns = new ValueDiff();
		returns.oldValue = BeanOneUtils.ensureSerializable(oldValue);
		returns.newValue = BeanOneUtils.ensureSerializable(newValue);
		return returns;
	}

	// note that if these member attributes are declared as Serializable, then
	// Gson is going to fail on de-serialization.
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
