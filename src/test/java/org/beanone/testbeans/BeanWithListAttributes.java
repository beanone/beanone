package org.beanone.testbeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class BeanWithListAttributes implements Serializable {
	private static final long serialVersionUID = 103005671110689505L;
	private final List<Integer> integers = Arrays.asList(1, 2, 3);
	// private final Set<Integer> integerSet = new HashSet<>(integers);
	//
	// public Set<Integer> getIntegerSet() {
	// return integerSet;
	// }

	public List<Integer> getIntegers() {
		return integers;
	}
}
