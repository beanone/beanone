package org.beanone.testbeans;

import java.io.Serializable;

public class BeanWithComplexAttributes implements Serializable {
	private static final long serialVersionUID = 3825035186427202158L;
	private final int integer = 1;
	private final BeanWithMapAttribute beanWithMap = new BeanWithMapAttribute();
	private final BeanWithListAttributes beanWithList = new BeanWithListAttributes();
	private final UserDetail userDetail = TestObjectFactory.createTestUserDetail();

	public int getInteger() {
		return integer;
	}

	public BeanWithMapAttribute getBeanWithMap() {
		return beanWithMap;
	}

	public BeanWithListAttributes getBeanWithList() {
		return beanWithList;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}
}
