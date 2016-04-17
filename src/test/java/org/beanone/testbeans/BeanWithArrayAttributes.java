package org.beanone.testbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BeanWithArrayAttributes implements Serializable {
	private static final long serialVersionUID = 103005671110689505L;
	private final int[] integers = new int[] { 1, 2, 3 };
	private final Map<Object, Object> intMap = new HashMap<>();

	public BeanWithArrayAttributes() {
		intMap.put("array", integers);
		intMap.put(integers, "integers");
	}

	public int[] getIntegers() {
		return integers;
	}

	public Map<Object, Object> getIntMap() {
		return intMap;
	}
}
