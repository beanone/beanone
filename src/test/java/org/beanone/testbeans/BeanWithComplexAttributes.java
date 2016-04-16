package org.beanone.testbeans;

public class BeanWithComplexAttributes {
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
