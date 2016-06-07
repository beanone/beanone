package org.beanone.testbeans;

import java.util.HashMap;
import java.util.Map;

public class BeanWithArrayAttributes {
	private int[] integers = new int[] { 1, 2, 3 };
	private Map<Object, Object> intMap = new HashMap<>();

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

	public void setIntegers(int[] integers) {
		this.integers = integers;
	}

	public void setIntMap(Map<Object, Object> intMap) {
		this.intMap = intMap;
	}
}
