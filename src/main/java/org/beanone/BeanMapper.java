package org.beanone;

import java.util.Map;

import org.beanone.flattener.FlattenerTool;

/**
 * <p>
 * Maps a JavaBean to a flattened Map structure and vise versa.
 * </p>
 *
 * <p>
 * Issues: The JavaBean cannot have properties of type Set.
 * </p>
 *
 * @author Hongyan Li
 *
 */
public class BeanMapper {
	private static final FlattenerTool flattenerTool = new FlattenerTool();

	/**
	 * un-flattens the passed in Map of simple values into a JavaBean of the
	 * passed in class type.
	 *
	 * @param map
	 *            a Map that contains all the simple attributes of the bean with
	 *            dotted format to represent the keys for attributes of
	 *            non-primitive attributes.
	 * @return a JavaBean of the passed in class type.
	 */
	public static Object fromMap(Map<String, String> map) {
		return flattenerTool.unflat(map);
	}

	/**
	 * Flattens the passed in bean into a Map of simple values.
	 *
	 * @param bean
	 *            a JavaBean object.
	 * @return all primitive attributes of the bean are flatten into a map with
	 *         the keys represent the attributes in a dotted format to represent
	 *         the object structure.
	 */
	public static Map<String, String> toMap(Object bean) {
		return bean == null ? null : flattenerTool.flat(bean);
	}

	private BeanMapper() {
		// private for utility
	}
}
