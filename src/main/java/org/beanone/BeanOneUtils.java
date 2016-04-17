package org.beanone;

import java.io.Serializable;

class BeanOneUtils {
	static Serializable ensureSerializable(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Serializable) {
			return (Serializable) value;
		}
		return value.toString();
	}

	private BeanOneUtils() {
		// private for utility
	}
}
