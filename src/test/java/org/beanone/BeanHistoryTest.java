package org.beanone;

import org.beanone.testbeans.TestObjectFactory;
import org.beanone.testbeans.UserDetail;
import org.junit.Assert;
import org.junit.Test;

public class BeanHistoryTest {
	@Test
	public void testBeanHistory() throws Exception {
		BeanHistory<UserDetail> bh = TestObjectFactory.createTestBeanHistory();
		Assert.assertNotNull(bh.getInitialState());
		Assert.assertNotNull(bh.getInitialState().getPerson());
		Assert.assertNotNull(bh.getInitialState().getPerson().getFirstName());
		Assert.assertNotNull(bh.getLatestState());
		Assert.assertNotNull(bh.getInitialSnapshot());
		Assert.assertNotNull(bh.getLastestSnapshot());
		Assert.assertEquals(0, bh.getPatches().size());
	}

	@Test
	public void testCreatePatchBeanUpdater() throws Exception {
		BeanHistory<UserDetail> bh = TestObjectFactory.createTestBeanHistory();
		bh.createPatch(new BeanUpdater<UserDetail>() {
			@Override
			public void update(UserDetail bean) {
				bean.getPerson().setFirstName("Bobby");
			}
		});

		Assert.assertEquals(1, bh.getPatches().size());
		Assert.assertEquals("Bobby", bh.getPatches().get(0).getUpdates().get("person.firstName").getNewValue());
	}

	@Test
	public void testCreatePatchT() throws Exception {
		UserDetail userDetail = TestObjectFactory.createTestUserDetail();
		BeanHistory<UserDetail> bh = new BeanHistory<UserDetail>(userDetail);
		userDetail.getPerson().setFirstName("Bobby");
		bh.createPatch(userDetail);

		Assert.assertEquals(1, bh.getPatches().size());
		Assert.assertEquals("Bobby", bh.getPatches().get(0).getUpdates().get("person.firstName").getNewValue());
	}

}
