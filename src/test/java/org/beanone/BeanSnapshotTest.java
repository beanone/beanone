package org.beanone;

import org.beanone.testbeans.TestObjectFactory;
import org.beanone.testbeans.UserDetail;
import org.junit.Assert;
import org.junit.Test;

public class BeanSnapshotTest {

	@Test
	public void testBeanSnapshot() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final BeanSnapshot<UserDetail> snapshot = new BeanSnapshot<UserDetail>(
		        beanHistory.getInitialState(), beanHistory.getPatches(), 0);
		Assert.assertNotNull(snapshot.getPatches());
		Assert.assertNotNull(snapshot.getState());
		Assert.assertEquals(0, snapshot.getVersion());
		Assert.assertFalse(snapshot.getState().getRelations().isEmpty());
		BeanSnapshot<UserDetail> nextVersion = snapshot.nextVersion();
		Assert.assertNotNull(nextVersion);
		Assert.assertEquals("Bobby",
		        nextVersion.getState().getPerson().getFirstName());
		Assert.assertFalse(nextVersion.getState().getRelations().isEmpty());
		nextVersion = nextVersion.nextVersion();
		Assert.assertNotNull(nextVersion);
		Assert.assertTrue(nextVersion.getState().getRelations().isEmpty());
		Assert.assertEquals(2, nextVersion.getState().getAddresses().size());
		Assert.assertEquals("123 Main St.", nextVersion.getState()
		        .getAddresses().get(0).getStreetAddress());
		Assert.assertEquals("222 Blue Ave.", nextVersion.getState()
		        .getAddresses().get(1).getStreetAddress());
	}

	@Test
	public void testIsBaseSnapshot() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final BeanSnapshot<UserDetail> initialSnapshot = beanHistory
		        .getInitialSnapshot();
		final BeanSnapshot<UserDetail> latestSnapshot = beanHistory
		        .getLastestSnapshot();
		Assert.assertTrue(initialSnapshot.isBaseSnapshot());
		Assert.assertFalse(latestSnapshot.isBaseSnapshot());
	}

	@Test
	public void testIsLatestSnapshot() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final BeanSnapshot<UserDetail> initialSnapshot = beanHistory
		        .getInitialSnapshot();
		final BeanSnapshot<UserDetail> latestSnapshot = beanHistory
		        .getLastestSnapshot();
		Assert.assertFalse(initialSnapshot.isLatestSnapshot());
		Assert.assertTrue(latestSnapshot.isLatestSnapshot());
	}

	@Test
	public void testNextVersion() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final BeanSnapshot<UserDetail> initialSnapshot = beanHistory
		        .getInitialSnapshot();
		final BeanSnapshot<UserDetail> latestSnapshot = beanHistory
		        .getLastestSnapshot();
		Assert.assertNull(
		        initialSnapshot.nextVersion().nextVersion().nextVersion());
		Assert.assertNull(latestSnapshot.nextVersion());
	}

	@Test
	public void testPreviousVersion() throws Exception {
		final BeanHistory<UserDetail> beanHistory = TestObjectFactory
		        .createTestBeanHistoryWithThreeVersions();
		final BeanSnapshot<UserDetail> initialSnapshot = beanHistory
		        .getInitialSnapshot();
		final BeanSnapshot<UserDetail> latestSnapshot = beanHistory
		        .getLastestSnapshot();
		Assert.assertNull(initialSnapshot.previousVersion());
		Assert.assertNull(latestSnapshot.previousVersion().previousVersion()
		        .previousVersion());
	}

}
