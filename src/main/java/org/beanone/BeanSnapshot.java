package org.beanone;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * The snapshot of a bean. With a BeanSnapshot, one can easily navigate in
 * between the different versions of a JavaBean.
 * <p/>
 * A BeanSnapshot is intentionally made not a {@link Serializable}.
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

	public BeanSnapshot(T bean, BeanHistory<T> beanHistory, int version) {
		this.state = SerializationUtils.clone(bean);
		this.beanHistory = beanHistory;
		this.version = version;
	}

	BeanHistory<T> getBeanHistory() {
		return beanHistory;
	}

	public T getState() {
		return SerializationUtils.clone(state);
	}

	public int getVersion() {
		return version;
	}

	boolean isBaseSnapshot() {
		return version == 0;
	}

	boolean isLatestSnapshot() {
		return version == getBeanHistory().getPatches().size();
	}

	public BeanSnapshot<T> nextVersion() {
		if (version >= getBeanHistory().getPatches().size()) {
			return null;
		} else {
			final BeanPatch<T> patch = getBeanHistory().getPatches()
			        .get(version);
			return patch.addTo(this);
		}
	}

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
