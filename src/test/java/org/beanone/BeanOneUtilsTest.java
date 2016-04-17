package org.beanone;

import org.junit.Assert;
import org.junit.Test;

public class BeanOneUtilsTest {
	@Test
	public void testEnsureSerializableForNotSerializable() {
		Assert.assertEquals("A string",
		        BeanOneUtils.ensureSerializable("A string"));
	}

	@Test
	public void testEnsureSerializableForNull() {
		Assert.assertNull(BeanOneUtils.ensureSerializable(null));
	}

	@Test
	public void testEnsureSerializableForSerializable() {
		final Object o = new Object();
		Assert.assertEquals(o.toString(), BeanOneUtils.ensureSerializable(o));
	}
}
