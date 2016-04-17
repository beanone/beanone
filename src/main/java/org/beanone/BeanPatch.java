package org.beanone;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
	        T updated) {
		// TODO: turn both into a map and compare the difference
		final Map<String, Object> baseMap = BeanMapper.toMap(base);
		final Map<String, Object> updatedMap = BeanMapper.toMap(updated);

		final Set<String> additionsKeys = new HashSet<>(updatedMap.keySet());
		additionsKeys.removeAll(baseMap.keySet());
		final Set<String> deletionsKeys = new HashSet<>(baseMap.keySet());
		deletionsKeys.removeAll(updatedMap.keySet());
		final Set<String> updationsKeys = new HashSet<>(updatedMap.keySet());
		updationsKeys.removeAll(additionsKeys);

		final BeanPatch<T> returns = new BeanPatch<>();
		for (final String key : additionsKeys) {
			returns.additions.put(key,
			        BeanOneUtils.ensureSerializable(updatedMap.get(key)));
		}
		for (final String key : deletionsKeys) {
			returns.deletions.put(key,
			        BeanOneUtils.ensureSerializable(baseMap.get(key)));
		}

		for (final String key : updationsKeys) {
			final Object oldValue = baseMap.get(key);
			final Object newValue = updatedMap.get(key);
			if (!newValue.equals(oldValue)) {
				returns.updates.put(key,
				        ValueDiff.makeDiff(oldValue, newValue));
			}
		}

		return returns;
	}

	// stores the old and new values in ValueDiffs, key might point to simple
	// attributes or indexed attributes
	private final Map<String, Serializable> additions = new HashMap<>();
	private final Map<String, Serializable> deletions = new HashMap<>();
	private final Map<String, ValueDiff> updates = new HashMap<>();

	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> addTo(BeanSnapshot<T> snapshot) {
		final T original = snapshot.getState();
		final Map<String, Object> originalMap = BeanMapper.toMap(original);
		for (final Entry<String, Serializable> entry : additions.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue());
		}
		for (final Entry<String, Serializable> entry : deletions.entrySet()) {
			originalMap.remove(entry.getKey());
		}
		for (final Entry<String, ValueDiff> entry : updates.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue().getNewValue());
		}

		final T nextBean = (T) BeanMapper.fromMap(originalMap,
		        original.getClass());

		return new BeanSnapshot<>(nextBean, snapshot.getBeanHistory(),
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

	public boolean hasChanges() {
		return !(additions.isEmpty() && deletions.isEmpty()
		        && updates.isEmpty());
	}

	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> substractFrom(BeanSnapshot<T> snapshot) {
		final T original = snapshot.getState();
		final Map<String, Object> originalMap = BeanMapper.toMap(original);
		for (final Entry<String, Serializable> entry : additions.entrySet()) {
			originalMap.remove(entry.getKey());
		}
		for (final Entry<String, Serializable> entry : deletions.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue());
		}
		for (final Entry<String, ValueDiff> entry : updates.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue().getOldValue());
		}

		final T previousBean = (T) BeanMapper.fromMap(originalMap,
		        original.getClass());

		return new BeanSnapshot<>(previousBean, snapshot.getBeanHistory(),
		        snapshot.getVersion() - 1);
	}
}
