package org.beanone.testbeans;

import java.util.Arrays;
import java.util.List;

public class BeanWithListAttributes {
	private final List<Integer> integers = Arrays.asList(1, 2, 3);

	public List<Integer> getIntegers() {
		return integers;
	}
}
