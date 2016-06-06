package org.beanone;

import java.util.Map;

import org.beanone.testbeans.BeanWithArrayAttributes;
import org.beanone.testbeans.BeanWithComplexAttributes;
import org.beanone.testbeans.BeanWithListAttributes;
import org.beanone.testbeans.BeanWithMapAttribute;
import org.beanone.testbeans.BeanWithSimpleAttributes;
import org.junit.Assert;
import org.junit.Test;

public class BeanMapperTest {
	@Test
	public void testToMapForBeanWithArrayAttributes() throws Exception {
		final Map<String, String> map = BeanMapper
		        .toMap(new BeanWithArrayAttributes());
		Assert.assertEquals(23, map.size());
		final BeanWithArrayAttributes bean = (BeanWithArrayAttributes) BeanMapper
		        .fromMap(map);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithComplexAttributes() throws Exception {
		final Map<String, String> map = BeanMapper
		        .toMap(new BeanWithComplexAttributes());
		Assert.assertEquals(41, map.size());
		final BeanWithComplexAttributes bean = (BeanWithComplexAttributes) BeanMapper
		        .fromMap(map);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithListOfSimpleAttributes() throws Exception {
		final Map<String, String> map = BeanMapper
		        .toMap(new BeanWithListAttributes());
		Assert.assertEquals(6, map.size());
		final BeanWithListAttributes bean = (BeanWithListAttributes) BeanMapper
		        .fromMap(map);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithSimpleAttributes() throws Exception {
		final Map<String, String> map = BeanMapper
		        .toMap(new BeanWithSimpleAttributes());
		Assert.assertEquals(5, map.size());
		final BeanWithSimpleAttributes bean = (BeanWithSimpleAttributes) BeanMapper
		        .fromMap(map);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForMap() throws Exception {
		final Map<String, String> map = BeanMapper
		        .toMap(new BeanWithMapAttribute());
		Assert.assertEquals(7, map.size());
		final BeanWithMapAttribute bean = (BeanWithMapAttribute) BeanMapper
		        .fromMap(map);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForNullBean() throws Exception {
		Assert.assertNull(BeanMapper.toMap(null));
	}
}
