package org.beanone;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * The snapshot of a bean. With a BeanSnapshot, one can easily navigate in
 * between the different versions of a JavaBean.
 *
 * <p>
 * A BeanSnapshot is intentionally made not a {@link Serializable}.
 * </p>
 *
 * <p>
 * This is largely immutable. The only way to "modify" this is through modifying
 * the BeanHistory.
 * </p>
 *
 * @author Hongyan Li
 *
 * @param <T>
 *            the type of JavaBean this snapshot is for.
 */
class BeanSnapshot<T extends Serializable> {
	private final T state;
	private final BeanHistory<T> beanHistory;
	private final int version;

	/**
	 * Constructs a new instance of this from a JavaBean, its history and a
	 * version number.
	 *
	 * @param bean
	 *            the bean state this snapshot is for.
	 * @param beanHistory
	 *            the bean history this snapshot is part of.
	 * @param version
	 *            the version of this snapshot.
	 */
	public BeanSnapshot(T bean, BeanHistory<T> beanHistory, int version) {
		this.state = SerializationUtils.clone(bean);
		this.beanHistory = beanHistory;
		this.version = version;
	}

	/**
	 * @return The {@link BeanHistory} this snapshot is for.
	 */
	BeanHistory<T> getBeanHistory() {
		return beanHistory;
	}

	/**
	 * @return the JavaBean state this snapshot is for. You can freely change
	 *         the object returned since it is a deep clone of the object
	 *         contained in this snapshot.
	 */
	public T getState() {
		return SerializationUtils.clone(state);
	}

	/**
	 * @return the version of the snapshot. The version is an automatically
	 *         generated integer, it starts from 0 and increments by 1.
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return true if this is version 0.
	 */
	boolean isBaseSnapshot() {
		return version == 0;
	}

	/**
	 * @return true if this is the latest version of the whole
	 *         {@link BeanHistory}.
	 */
	boolean isLatestSnapshot() {
		return version == getBeanHistory().getPatches().size();
	}

	/**
	 * @return the next version snapshot of the BeanHistory. If this is already
	 *         the latest version, then return null.
	 */
	public BeanSnapshot<T> nextVersion() {
		if (version >= getBeanHistory().getPatches().size()) {
			return null;
		} else {
			final BeanPatch<T> patch = getBeanHistory().getPatches()
			        .get(version);
			return patch.addTo(this);
		}
	}

	/**
	 * @return the previous version snapshot of the BeanHistory. If this is
	 *         already version 0, then return null.
	 */
	public BeanSnapshot<T> previousVersion() {
		if (version == 0) {
			return null;
		} else {
			final BeanPatch<T> patch = getBeanHistory().getPatches()
			        .get(version - 1);
			return patch.substractFrom(this);
		}
	}
}
