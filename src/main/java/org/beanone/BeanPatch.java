package org.beanone;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulates a patch for a JavaBean.
 *
 * @author Hongyan Li
 *
 * @param <T>
 *            the type of JavaBean this patch is for.
 */
public class BeanPatch<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -2602568652611370513L;

	public static <T extends Serializable> BeanPatch<T> create(T base,
	        T updated) throws IllegalAccessException, InvocationTargetException,
	                NoSuchMethodException {
		// TODO: turn both into a map and compare the difference
		final Map<String, Object> baseMap = BeanMapper.toMap(base);
		final Map<String, Object> updatedMap = BeanMapper.toMap(updated);

		final Set<String> additionsKeys = new HashSet<String>(
		        updatedMap.keySet());
		additionsKeys.removeAll(baseMap.keySet());
		final Set<String> deletionsKeys = new HashSet<String>(baseMap.keySet());
		deletionsKeys.removeAll(updatedMap.keySet());
		final Set<String> updationsKeys = new HashSet<String>(
		        updatedMap.keySet());
		updationsKeys.removeAll(additionsKeys);

		final BeanPatch<T> returns = new BeanPatch<T>();
		for (final String key : additionsKeys) {
			returns.additions.put(key, updatedMap.get(key));
		}
		for (final String key : deletionsKeys) {
			returns.deletions.put(key, baseMap.get(key));
		}
		Object oldValue = null;
		Object newValue = null;
		for (final String key : updationsKeys) {
			oldValue = baseMap.get(key);
			newValue = updatedMap.get(key);
			if (oldValue == null && newValue != null) {
				returns.additions.put(key, newValue);
			} else if (newValue == null && oldValue != null) {
				returns.deletions.put(key, oldValue);
			} else if (newValue != null && !newValue.equals(oldValue)) {
				returns.updates.put(key,
				        ValueDiff.makeDiff(oldValue, newValue));
			}
		}

		return returns;
	}

	// stores the old and new values in ValueDiffs, key might point to simple
	// attributes or indexed attributes
	private final Map<String, Object>	additions	= new HashMap<String, Object>();
	private final Map<String, Object>	deletions	= new HashMap<String, Object>();

	private final Map<String, ValueDiff> updates = new HashMap<String, ValueDiff>();

	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> addTo(BeanSnapshot<T> snapshot)
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		final T original = snapshot.getState();
		final Map<String, Object> originalMap = BeanMapper.toMap(original);
		for (final String key : additions.keySet()) {
			originalMap.put(key, additions.get(key));
		}
		for (final String key : deletions.keySet()) {
			originalMap.remove(key);
		}
		for (final String key : updates.keySet()) {
			originalMap.put(key, updates.get(key).getNewValue());
		}

		final T nextBean = (T) BeanMapper.fromMap(originalMap,
		        original.getClass());

		return new BeanSnapshot<T>(nextBean, snapshot.getBeanHistory(),
		        snapshot.getVersion() + 1);
	}

	public Map<String, Object> getAdditions() {
		return Collections.unmodifiableMap(additions);
	}

	public Map<String, Object> getDeletions() {
		return Collections.unmodifiableMap(deletions);
	}

	public Map<String, ValueDiff> getUpdates() {
		return Collections.unmodifiableMap(updates);
	}

	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> substractFrom(BeanSnapshot<T> snapshot)
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		if (snapshot.isBaseSnapshot()) {
			throw new IllegalArgumentException(
			        "The passed in is already the base snapshot!");
		}

		final T original = snapshot.getState();
		final Map<String, Object> originalMap = BeanMapper.toMap(original);
		for (final String key : additions.keySet()) {
			originalMap.remove(key);
		}
		for (final String key : deletions.keySet()) {
			originalMap.put(key, deletions.get(key));
		}
		for (final String key : updates.keySet()) {
			originalMap.put(key, updates.get(key).getOldValue());
		}

		final T previousBean = (T) BeanMapper.fromMap(originalMap,
		        original.getClass());

		return new BeanSnapshot<T>(previousBean, snapshot.getBeanHistory(),
		        snapshot.getVersion() - 1);
	}
}
