package org.beanone.testbeans;

public class TestObjectFactory {
    public static UserDetail createTestUserDetail() {
        final UserDetail returns = new UserDetail();
        final Person person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Smith");
        returns.setPerson(person);
        returns.setPosition(1);
        returns.setUserId("bob.smith");

        final Person brother = new Person();
        brother.setFirstName("William");
        brother.setLastName("Smith");
        returns.addRelated("brother", brother);

        final Address address = new Address();
        address.setStreetAddress("123 Main St.");
        address.setCity("Seattle");
        address.setZip(123);

        returns.addAddress(address);

        return returns;
    }
}
