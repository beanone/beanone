package org.beanone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private final int version;
	private final List<BeanPatch<T>> patches = new ArrayList<>();

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
	public BeanSnapshot(T bean, final List<BeanPatch<T>> patches, int version) {
		this.state = cloneBean(bean);
		this.patches.addAll(patches);
		this.version = version;
	}

	/**
	 * @return the JavaBean state this snapshot is for. You can freely change
	 *         the object returned since it is a deep clone of the object
	 *         contained in this snapshot.
	 */
	public T getState() {
		return cloneBean(this.state);
	}

	/**
	 * @return the version of the snapshot. The version is an automatically
	 *         generated integer, it starts from 0 and increments by 1.
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * @return the next version snapshot of the BeanHistory. If this is already
	 *         the latest version, then return null.
	 */
	public BeanSnapshot<T> nextVersion() {
		if (this.version >= getPatches().size()) {
			return null;
		} else {
			final BeanPatch<T> patch = getPatches().get(this.version);
			return patch.addTo(this);
		}
	}

	/**
	 * @return the previous version snapshot of the BeanHistory. If this is
	 *         already version 0, then return null.
	 */
	public BeanSnapshot<T> previousVersion() {
		if (this.version == 0) {
			return null;
		} else {
			final BeanPatch<T> patch = getPatches().get(this.version - 1);
			return patch.substractFrom(this);
		}
	}

	private T cloneBean(T bean) {
		return SerializationUtils.clone(bean);
	}

	List<BeanPatch<T>> getPatches() {
		return Collections.unmodifiableList(this.patches);
	}

	/**
	 * @return true if this is version 0.
	 */
	boolean isBaseSnapshot() {
		return this.version == 0;
	}

	/**
	 * @return true if this is the latest version of the whole
	 *         {@link BeanHistory}.
	 */
	boolean isLatestSnapshot() {
		return this.version == getPatches().size();
	}
}
