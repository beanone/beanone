package org.beanone;

import org.beanone.testbeans.TestObjectFactory;
import org.beanone.testbeans.UserDetail;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BeanHistoryTest {
	@Test
	public void testBeanHistory() throws Exception {
		final BeanHistory<UserDetail> bh = TestObjectFactory
		        .createTestBeanHistory();
		Assert.assertNotNull(bh.getInitialState());
		Assert.assertNotNull(bh.getInitialState().getPerson());
		Assert.assertNotNull(bh.getInitialState().getPerson().getFirstName());
		Assert.assertNotNull(bh.getLatestState());
		Assert.assertNotNull(bh.getInitialSnapshot());
		Assert.assertNotNull(bh.getLastestSnapshot());
		Assert.assertEquals(0, bh.getPatches().size());
	}

	@Test
	public void testBeanHistoryWithPatchesSerializedAsJson() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final Gson gson = new GsonBuilder().create();
		Assert.assertEquals(
		        "{\"initialState\":{\"position\":1,\"userId\":\"bob.smith\",\"person\":{\"firstName\":\"Bob\",\"lastName\":\"Smith\",\"emailAddresses\":[]},\"addresses\":[{\"streetAddress\":\"123 Main St.\",\"city\":\"Seattle\",\"zip\":123}],\"relations\":{\"brother\":{\"firstName\":\"William\",\"lastName\":\"Smith\",\"emailAddresses\":[]}}},\"latestState\":{\"position\":1,\"userId\":\"bob.smith\",\"person\":{\"firstName\":\"Bobby\",\"lastName\":\"Smith\",\"emailAddresses\":[]},\"addresses\":[{\"streetAddress\":\"123 Main St.\",\"city\":\"Seattle\",\"zip\":123},{\"streetAddress\":\"222 Blue Ave.\",\"city\":\"Master\",\"zip\":111}],\"relations\":{}},\"patches\":[{\"additions\":{},\"deletions\":{},\"updates\":{\"person.firstName\":{\"oldValue\":\"Bob\",\"newValue\":\"Bobby\"}}},{\"additions\":{\"addresses[1].city\":\"Master\",\"addresses[1].streetAddress\":\"222 Blue Ave.\",\"addresses[1].zip\":111},\"deletions\":{\"relations.brother.firstName\":\"William\",\"relations.brother.lastName\":\"Smith\"},\"updates\":{}}]}",
		        gson.toJson(beanHistory));
	}

	@Test
	public void testCreatePatchBeanUpdater() throws Exception {
		final BeanHistory<UserDetail> bh = TestObjectFactory
		        .createTestBeanHistory();
		bh.createPatch(bean -> bean.getPerson().setFirstName("Bobby"));

		Assert.assertEquals(1, bh.getPatches().size());
		Assert.assertEquals("Bobby", bh.getPatches().get(0).getUpdates()
		        .get("person.firstName").getNewValue());
	}

	@Test
	public void testCreatePatchBeanUpdaterWithNoChanges() throws Exception {
		final BeanHistory<UserDetail> bh = TestObjectFactory
		        .createTestBeanHistory();
		Assert.assertNull(bh.createPatch(bean -> bean.getPerson()));
	}

	@Test
	public void testCreatePatchT() throws Exception {
		final UserDetail userDetail = TestObjectFactory.createTestUserDetail();
		final BeanHistory<UserDetail> bh = new BeanHistory<UserDetail>(
		        userDetail);
		userDetail.getPerson().setFirstName("Bobby");
		bh.createPatch(userDetail);

		Assert.assertEquals(1, bh.getPatches().size());
		Assert.assertEquals("Bobby", bh.getPatches().get(0).getUpdates()
		        .get("person.firstName").getNewValue());
	}

	@Test
	public void testCreatePatchTWithNoChanges() throws Exception {
		final UserDetail userDetail = TestObjectFactory.createTestUserDetail();
		final BeanHistory<UserDetail> bh = new BeanHistory<UserDetail>(
		        userDetail);
		Assert.assertNull(bh.createPatch(userDetail));
	}
}
