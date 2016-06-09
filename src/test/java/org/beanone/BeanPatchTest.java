package org.beanone;

import org.beanone.testbeans.Person;
import org.beanone.testbeans.TestObjectFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanPatchTest {
	private final Person person = TestObjectFactory.createPerson("Bob", null);
	private final Person person1 = TestObjectFactory.createPerson("Bob",
	        "Smith");
	private final Person person2 = TestObjectFactory.createPerson("Bobby",
	        "Smith");
	private final Person person3 = TestObjectFactory.createPerson("Bob",
	        "Smith");

	@Before
	public void init() {
		person1.addEmailAddress("a@b.com");
		person1.addEmailAddress("c@d.com");
		person3.addPhone("1112223333");
	}

	@Test
	public void testCreateWithAdditionAndUpdates() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person, person2);
		validateChanges(patch, 1, 0, 1);
	}

	@Test
	public void testCreateWithAdditions() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person, person1);
		validateChanges(patch, 3, 0, 1);
	}

	@Test
	public void testCreateWithAdditionsAndDeletionsOnly() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person1, person3);
		Assert.assertTrue(patch.hasChanges());
	}

	@Test
	public void testCreateWithAdditionsOnly() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person2, person1);
		Assert.assertTrue(patch.hasChanges());
	}

	@Test
	public void testCreateWithDeletions() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person1, person);
		validateChanges(patch, 0, 3, 1);
	}

	@Test
	public void testCreateWithDeletionsOnly() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person1, person2);
		Assert.assertTrue(patch.hasChanges());
	}

	@Test
	public void testCreateWithNoUpdates() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person, person);
		Assert.assertFalse(patch.hasChanges());
	}

	@Test
	public void testCreateWithUpdatesOnly() throws Exception {
		final BeanPatch<Person> patch = BeanPatch.create(person, person2);
		Assert.assertTrue(patch.hasChanges());
	}

	private void validateChanges(BeanPatch<Person> patch, int additions,
	        int deletions, int updates) {
		Assert.assertNotNull(patch.getAdditions());
		Assert.assertEquals(additions, patch.getAdditions().size());
		Assert.assertNotNull(patch.getDeletions());
		Assert.assertEquals(deletions, patch.getDeletions().size());
		Assert.assertNotNull(patch.getUpdates());
		Assert.assertEquals(updates, patch.getUpdates().size());
	}

}
