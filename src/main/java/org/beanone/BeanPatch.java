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

	/**
	 * Creates a patch that contains the difference in between the two passed in
	 * JavaBeans.
	 *
	 * @param base
	 *            the base of the patch is created for.
	 * @param updated
	 *            the updated JavaBean if the patch is applied to the base.
	 * @return the patch.
	 */
	public static <T extends Serializable> BeanPatch<T> create(T base,
	        T updated) {
		final Map<String, String> baseMap = BeanMapper.toMap(base);
		final Map<String, String> updatedMap = BeanMapper.toMap(updated);

		final Set<String> additionsKeys = new HashSet<>(updatedMap.keySet());
		additionsKeys.removeAll(baseMap.keySet());
		final Set<String> deletionsKeys = new HashSet<>(baseMap.keySet());
		deletionsKeys.removeAll(updatedMap.keySet());
		final Set<String> updationsKeys = new HashSet<>(updatedMap.keySet());
		updationsKeys.removeAll(additionsKeys);

		final BeanPatch<T> returns = new BeanPatch<>();
		for (final String key : additionsKeys) {
			returns.additions.put(key, updatedMap.get(key));
		}
		for (final String key : deletionsKeys) {
			returns.deletions.put(key, baseMap.get(key));
		}

		for (final String key : updationsKeys) {
			final String oldValue = baseMap.get(key);
			final String newValue = updatedMap.get(key);
			if (!newValue.equals(oldValue)) {
				returns.updates.put(key,
				        ValueDiff.makeDiff(oldValue, newValue));
			}
		}

		return returns;
	}

	private final Map<String, String> additions = new HashMap<>();
	private final Map<String, String> deletions = new HashMap<>();
	private final Map<String, ValueDiff> updates = new HashMap<>();

	/**
	 * Applies the current patch to the passed in. This method can be used to
	 * support redo operation.
	 *
	 * @param snapshot
	 *            the base snapshot the patch is applied to.
	 * @return the new JavaBean after the patch is applied to the passed in.
	 */
	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> addTo(BeanSnapshot<T> snapshot) {
		final T original = snapshot.getState();
		final Map<String, String> originalMap = BeanMapper.toMap(original);
		for (final Entry<String, String> entry : additions.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue());
		}
		for (final Entry<String, String> entry : deletions.entrySet()) {
			originalMap.remove(entry.getKey());
		}
		for (final Entry<String, ValueDiff> entry : updates.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue().getNewValue());
		}

		final T nextBean = (T) BeanMapper.fromMap(originalMap);

		return new BeanSnapshot<>(nextBean, snapshot.getBeanHistory(),
		        snapshot.getVersion() + 1);
	}

	/**
	 * The additions this patch contains.
	 *
	 * @return a map of attribute key / value pairs.
	 */
	public Map<String, Object> getAdditions() {
		return Collections.unmodifiableMap(additions);
	}

	/**
	 * The deletions this patch contains.
	 *
	 * @return a map of attribute key / value pairs.
	 */
	public Map<String, Object> getDeletions() {
		return Collections.unmodifiableMap(deletions);
	}

	/**
	 * The updates this patch contains.
	 *
	 * @return a map of attribute key / value pairs.
	 */
	public Map<String, ValueDiff> getUpdates() {
		return Collections.unmodifiableMap(updates);
	}

	/**
	 * @return false if no additions, no deletions and updates. Otherwise return
	 *         true.
	 */
	public boolean hasChanges() {
		return !(additions.isEmpty() && deletions.isEmpty()
		        && updates.isEmpty());
	}

	/**
	 * un-applies the current patch to the passed in. This method can be used to
	 * support undo operation.
	 *
	 * @param snapshot
	 *            the snapshot the patch is un-applied from.
	 * @return the base JavaBean after the patch is un-applied from the passed
	 *         in.
	 */
	@SuppressWarnings("unchecked")
	public BeanSnapshot<T> substractFrom(BeanSnapshot<T> snapshot) {
		final T original = snapshot.getState();
		final Map<String, String> originalMap = BeanMapper.toMap(original);
		for (final Entry<String, String> entry : additions.entrySet()) {
			originalMap.remove(entry.getKey());
		}
		for (final Entry<String, String> entry : deletions.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue());
		}
		for (final Entry<String, ValueDiff> entry : updates.entrySet()) {
			originalMap.put(entry.getKey(), entry.getValue().getOldValue());
		}

		final T previousBean = (T) BeanMapper.fromMap(originalMap);

		return new BeanSnapshot<>(previousBean, snapshot.getBeanHistory(),
		        snapshot.getVersion() - 1);
	}
}
