package org.beanone;

import org.beanone.testbeans.TestObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

public class ValueDiffTest {

	@Test
	public void testDiffSerialization() {
		final Gson gson = TestObjectFactory.createGson();
		final ValueDiff vd = ValueDiff.makeDiff("a", "b");
		final String vdString = gson.toJson(vd);
		final ValueDiff vd1 = gson.fromJson(vdString, ValueDiff.class);
		Assert.assertEquals(vd.getNewValue(), vd1.getNewValue());
		Assert.assertEquals(vd.getOldValue(), vd1.getOldValue());
	}

	@Test
	public void testMakeDiff() {
		final ValueDiff vd = ValueDiff.makeDiff("a", "b");
		Assert.assertEquals("a", vd.getOldValue());
		Assert.assertEquals("b", vd.getNewValue());
	}
}
