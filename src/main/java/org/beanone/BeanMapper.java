package org.beanone;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;

/**
 * Maps a JavaBean to a flattened Map structure and vise versa.
 * <p/>
 * Issues:
 * <p/>
 * The JavaBean cannot have properties of type Set.
 * 
 * @author hongliii
 *
 */
public class BeanMapper {
	private static final JsonObjectMapper<?, ?> JSON_OBJECT_MAPPER = JsonObjectMapperProvider.newInstance();

	private static void doFlatten(String propertyPrefix, Map<String, Object> inputMap, Map<String, Object> resultMap) {
		if (StringUtils.hasText(propertyPrefix)) {
			propertyPrefix = propertyPrefix + ".";
		}
		for (Entry<String, Object> entry : inputMap.entrySet()) {
			doProcessElement(propertyPrefix + entry.getKey(), entry.getValue(), resultMap);
		}
	}

	private static void doProcessCollection(String propertyPrefix, Collection<?> list, Map<String, Object> resultMap) {
		int counter = 0;
		for (Object element : list) {
			doProcessElement(propertyPrefix + "[" + counter + "]", element, resultMap);
			counter++;
		}
	}

	@SuppressWarnings("unchecked")
	private static void doProcessElement(String propertyPrefix, Object element, Map<String, Object> resultMap) {
		if (element instanceof Map) {
			doFlatten(propertyPrefix, (Map<String, Object>) element, resultMap);
		} else if (element instanceof Collection) {
			doProcessCollection(propertyPrefix, (Collection<?>) element, resultMap);
		} else if (element != null && element.getClass().isArray()) {
			Collection<?> collection = CollectionUtils.arrayToList(element);
			doProcessCollection(propertyPrefix, collection, resultMap);
		} else {
			resultMap.put(propertyPrefix, element);
		}
	}

	private static Map<String, Object> flattenMap(Map<String, Object> result) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		doFlatten("", result, resultMap);
		return resultMap;
	}

	/**
	 * un-flattens the passed in Map of primitive values into a JavaBean of the
	 * passed in class type.
	 * 
	 * @param map
	 *            a Map that contains all the primitive attributes of the bean
	 *            with dotted format to represent the keys for attributes of
	 *            non-primitive attributes.
	 * @param clazz
	 *            the class type of the JavaBean the passed in map is
	 *            un-flattened to.
	 * @return a JavaBean of the passed in class type.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> T fromMap(Map<String, Object> map, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T target = clazz.newInstance();

		DataBinder binder = new DataBinder(target);
		ConversionService conversionService = new DefaultConversionService();

		binder.setConversionService(conversionService);
		binder.bind(new MutablePropertyValues(map));

		return target;
	}

	/**
	 * Flattens the passed in bean into a Map of primitive values.
	 * 
	 * @param bean
	 *            a JavaBean object.
	 * @return all primitive attributes of the bean are flatten into a map with
	 *         the keys represent the attributes in a dotted format to represent
	 *         the object structure.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object bean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			throw new IllegalArgumentException("The passed in bean is null!");
		}
		try {
			Map<String, Object> result = JSON_OBJECT_MAPPER.fromJson(JSON_OBJECT_MAPPER.toJson(bean), Map.class);
			return flattenMap(result);
		} catch (Exception e) {
			throw new BeanFlattenException(e.getMessage(), e);
		}
	}
}
