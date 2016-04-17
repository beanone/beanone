package org.beanone.testbeans;

import org.beanone.BeanHistory;

public class TestObjectFactory {
	public static Address createAddress(String addressLine1, String city,
	        int zip) {
		final Address address = new Address();
		address.setStreetAddress(addressLine1);
		address.setCity(city);
		address.setZip(zip);
		return address;
	}

	public static Person createPerson(String firstName, String lastName) {
		final Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		return person;
	}

	public static BeanHistory<UserDetail> createTestBeanHistory()
	        throws Exception {
		final UserDetail userDetail = TestObjectFactory.createTestUserDetail();
		return new BeanHistory<UserDetail>(userDetail);
	}

	public static BeanHistory<UserDetail> createTestBeanHistoryWithThreeVersions()
	        throws Exception {
		final UserDetail userDetail = createTestUserDetail();
		final BeanHistory<UserDetail> beanHistory = new BeanHistory<UserDetail>(
		        userDetail);
		userDetail.getPerson().setFirstName("Bobby");
		beanHistory.createPatch(userDetail);
		userDetail.addAddress(TestObjectFactory.createAddress("222 Blue Ave.",
		        "Master", 111));
		beanHistory.createPatch(userDetail);
		return beanHistory;
	}

	public static UserDetail createTestUserDetail() {
		final UserDetail returns = new UserDetail();
		final Person person = createPerson("Bob", "Smith");
		returns.setPerson(person);
		returns.setPosition(1);
		returns.setUserId("bob.smith");

		final Person brother = createPerson("William", "Smith");
		returns.addRelated("brother", brother);

		final Address address = createAddress("123 Main St.", "Seattle", 123);

		returns.addAddress(address);

		return returns;
	}
}
