package org.beanone.testbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanWithListAttributes {
	private List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));

	public List<Integer> getIntegers() {
		return integers;
	}

	public void setIntegers(List<Integer> integers) {
		this.integers = integers;
	}
}
