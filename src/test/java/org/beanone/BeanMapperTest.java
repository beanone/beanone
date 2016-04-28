package org.beanone;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConversionException;
import org.beanone.testbeans.BeanWithArrayAttributes;
import org.beanone.testbeans.BeanWithComplexAttributes;
import org.beanone.testbeans.BeanWithGetterThrowException;
import org.beanone.testbeans.BeanWithInstantiationError;
import org.beanone.testbeans.BeanWithListAttributes;
import org.beanone.testbeans.BeanWithMapAttribute;
import org.beanone.testbeans.BeanWithSimpleAttributes;
import org.junit.Assert;
import org.junit.Test;

public class BeanMapperTest {
	@Test(expected = ConversionException.class)
	public void testFromMapClassInstantiationError() throws Exception {
		BeanMapper.fromMap(new HashMap<>(), BeanWithInstantiationError.class);
	}

	@Test(expected = ConversionException.class)
	public void testToMapForBeanGetterThrowException() throws Exception {
		BeanMapper.toMap(new BeanWithGetterThrowException());
	}

	@Test
	public void testToMapForBeanWithArrayAttributes() throws Exception {
		final Map<String, Object> map = BeanMapper
		        .toMap(new BeanWithArrayAttributes());
		Assert.assertEquals(7, map.size());
		final BeanWithArrayAttributes bean = BeanMapper.fromMap(map,
		        BeanWithArrayAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithComplexAttributes() throws Exception {
		final Map<String, Object> map = BeanMapper
		        .toMap(new BeanWithComplexAttributes());
		Assert.assertEquals(15, map.size());
		final BeanWithComplexAttributes bean = BeanMapper.fromMap(map,
		        BeanWithComplexAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithListOfSimpleAttributes() throws Exception {
		final Map<String, Object> map = BeanMapper
		        .toMap(new BeanWithListAttributes());
		Assert.assertEquals(3, map.size());
		final BeanWithListAttributes bean = BeanMapper.fromMap(map,
		        BeanWithListAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForBeanWithSimpleAttributes() throws Exception {
		final Map<String, Object> map = BeanMapper
		        .toMap(new BeanWithSimpleAttributes());
		Assert.assertEquals(4, map.size());
		final BeanWithSimpleAttributes bean = BeanMapper.fromMap(map,
		        BeanWithSimpleAttributes.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void testToMapForMap() throws Exception {
		final Map<String, Object> map = BeanMapper
		        .toMap(new BeanWithMapAttribute());
		Assert.assertEquals(2, map.size());
		final BeanWithMapAttribute bean = BeanMapper.fromMap(map,
		        BeanWithMapAttribute.class);
		Assert.assertNotNull(bean);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToMapForNullBean() throws Exception {
		BeanMapper.toMap(null);
	}
}
