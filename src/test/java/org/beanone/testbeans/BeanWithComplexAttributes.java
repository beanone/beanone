package org.beanone.testbeans;

public class BeanWithComplexAttributes {
	private int integer = 1;
	private BeanWithMapAttribute beanWithMap = new BeanWithMapAttribute();
	private BeanWithListAttributes beanWithList = new BeanWithListAttributes();
	private UserDetail userDetail = TestObjectFactory.createTestUserDetail();

	public BeanWithListAttributes getBeanWithList() {
		return beanWithList;
	}

	public BeanWithMapAttribute getBeanWithMap() {
		return beanWithMap;
	}

	public int getInteger() {
		return integer;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setBeanWithList(BeanWithListAttributes beanWithList) {
		this.beanWithList = beanWithList;
	}

	public void setBeanWithMap(BeanWithMapAttribute beanWithMap) {
		this.beanWithMap = beanWithMap;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
}
