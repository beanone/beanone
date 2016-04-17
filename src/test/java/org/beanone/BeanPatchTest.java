package org.beanone;

import org.beanone.testbeans.Person;
import org.beanone.testbeans.TestObjectFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanPatchTest {
	private Person person = TestObjectFactory.createPerson("Bob", null);
	private Person person1 = TestObjectFactory.createPerson("Bob", "Smith");
	private Person person2 = TestObjectFactory.createPerson("Bobby", "Smith");

	@Before
	public void init() {
		person1.addEmailAddress("a@b.com");
		person1.addEmailAddress("c@d.com");
	}

	@Test
	public void testCreateWithAdditionAndUpdates() throws Exception {
		BeanPatch<Person> patch = BeanPatch.create(person, person2);
		validateChanges(patch, 1, 0, 1);
	}

	@Test
	public void testCreateWithAdditions() throws Exception {
		BeanPatch<Person> patch = BeanPatch.create(person, person1);
		validateChanges(patch, 3, 0, 0);
	}

	@Test
	public void testCreateWithDeletions() throws Exception {
		BeanPatch<Person> patch = BeanPatch.create(person1, person);
		validateChanges(patch, 0, 3, 0);
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
