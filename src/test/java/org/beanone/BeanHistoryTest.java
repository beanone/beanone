package org.beanone;

import java.util.List;

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

	@Test(expected = UnsupportedOperationException.class)
	public void testBeanHistoryGetPatchesUnmodifiable() throws Exception {
		final BeanHistory<UserDetail> hist = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final List<BeanPatch<UserDetail>> patches = hist.getPatches();
		Assert.assertEquals(2, patches.size());
		patches.remove(0);
	}

	@Test
	public void testBeanHistoryWithPatchesSerializedAsJson() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final Gson gson = new GsonBuilder().create();
		Assert.assertEquals(
		        "{\"initialState\":{\"position\":1,\"userId\":\"bob.smith\",\"person\":{\"firstName\":\"Bob\",\"lastName\":\"Smith\",\"emailAddresses\":[]},\"addresses\":[{\"streetAddress\":\"123 Main St.\",\"city\":\"Seattle\",\"zip\":123}],\"relations\":{\"brother\":{\"firstName\":\"William\",\"lastName\":\"Smith\",\"emailAddresses\":[]}}},\"latestState\":{\"position\":1,\"userId\":\"bob.smith\",\"person\":{\"firstName\":\"Bobby\",\"lastName\":\"Smith\",\"emailAddresses\":[]},\"addresses\":[{\"streetAddress\":\"123 Main St.\",\"city\":\"Seattle\",\"zip\":123},{\"streetAddress\":\"222 Blue Ave.\",\"city\":\"Master\",\"zip\":111}],\"relations\":{}},\"patches\":[{\"additions\":{},\"deletions\":{},\"updates\":{\"person.firstName\":{\"oldValue\":\"S,Bob\",\"newValue\":\"S,Bobby\"}}},{\"additions\":{\"addresses.1#1ctype\":\"org.beanone.testbeans.Address\",\"addresses.1.serialVersionUID\":\"L,-7200555141567811331\",\"person.emailAddresses#2size\":\"0\",\"person.lastName\":\"S,Smith\",\"addresses.1.city\":\"S,Master\",\"addresses.1.streetAddress\":\"S,222 Blue Ave.\",\"person#1ctype\":\"org.beanone.testbeans.Person\",\"person.firstName\":\"S,Bobby\",\"addresses.1.zip\":\"I,111\",\"person.emailAddresses#1ctype\":\"java.util.ArrayList\",\"person.serialVersionUID\":\"L,-5001884724414260401\"},\"deletions\":{\"relations.1#value.emailAddresses#2size\":\"0\",\"relations.1#value.lastName\":\"S,Smith\",\"relations.1#value.emailAddresses#1ctype\":\"java.util.ArrayList\",\"person#ref\":\"person\",\"relations.1#value#1ctype\":\"org.beanone.testbeans.Person\",\"relations.1#value.firstName\":\"S,William\",\"relations.1#value.serialVersionUID\":\"L,-5001884724414260401\",\"relations.1#key\":\"S,brother\"},\"updates\":{\"relations#2size\":{\"oldValue\":\"1\",\"newValue\":\"0\"},\"addresses#2size\":{\"oldValue\":\"1\",\"newValue\":\"2\"}}}]}",
		        gson.toJson(beanHistory));
	}

	@Test
	public void testCreatePatchBeanUpdater() throws Exception {
		final BeanHistory<UserDetail> bh = TestObjectFactory
		        .createTestBeanHistory();
		bh.createPatch(bean -> bean.getPerson().setFirstName("Bobby"));

		Assert.assertEquals(1, bh.getPatches().size());
		Assert.assertEquals("S,Bobby", bh.getPatches().get(0).getUpdates()
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
		Assert.assertEquals("S,Bobby", bh.getPatches().get(0).getUpdates()
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
