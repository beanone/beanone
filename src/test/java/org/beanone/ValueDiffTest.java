package org.beanone;

import org.junit.Assert;
import org.junit.Test;

public class ValueDiffTest {

	@Test
	public void testMakeDiff() {
		ValueDiff vd = ValueDiff.makeDiff("a", "b");
		Assert.assertEquals("a", vd.getOldValue());
		Assert.assertEquals("b", vd.getNewValue());
	}

}
