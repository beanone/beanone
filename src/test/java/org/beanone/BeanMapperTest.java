package org.beanone;

import java.util.Map;

import org.beanone.testbeans.BeanWithComplexAttributes;
import org.beanone.testbeans.BeanWithListAttributes;
import org.beanone.testbeans.BeanWithMapAttribute;
import org.beanone.testbeans.BeanWithSimpleAttributes;
import org.junit.Assert;
import org.junit.Test;

public class BeanMapperTest {
	@Test(expected = IllegalArgumentException.class)
	public void testToMapForNullBean() throws Exception {
		BeanMapper.toMap(null);
	}

	@Test
	public void testToMapForBeanWithSimpleAttributes() throws Exception {
		Map<String, Object> map = BeanMapper.toMap(new BeanWithSimpleAttributes());
		Assert.assertEquals(4, map.size());
		BeanWithSimpleAttributes bean = BeanMapper.fromMap(map, BeanWithSimpleAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithCollectionsOfSimpleAttributes() throws Exception {
		Map<String, Object> map = BeanMapper.toMap(new BeanWithListAttributes());
		Assert.assertEquals(3, map.size());
		BeanWithListAttributes bean = BeanMapper.fromMap(map, BeanWithListAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForMap() throws Exception {
		Map<String, Object> map = BeanMapper.toMap(new BeanWithMapAttribute());
		Assert.assertEquals(2, map.size());
		BeanWithMapAttribute bean = BeanMapper.fromMap(map, BeanWithMapAttribute.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithComplexAttributes() throws Exception {
		Map<String, Object> map = BeanMapper.toMap(new BeanWithComplexAttributes());
		Assert.assertEquals(15, map.size());
		BeanWithComplexAttributes bean = BeanMapper.fromMap(map, BeanWithComplexAttributes.class);
		Assert.assertNotNull(bean);
	}
}
