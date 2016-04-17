package org.beanone.testbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BeanWithMapAttribute implements Serializable {
	private static final long serialVersionUID = 1106482663080673430L;
	private final Map<Object, Object> mapBean = new HashMap<Object, Object>();

	public BeanWithMapAttribute() {
		mapBean.put("1", "A");
		mapBean.put("2", "B");
	}

	public Map<Object, Object> getMapBean() {
		return mapBean;
	}
}
