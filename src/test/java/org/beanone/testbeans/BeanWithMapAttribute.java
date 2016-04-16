package org.beanone.testbeans;

import java.util.HashMap;
import java.util.Map;

public class BeanWithMapAttribute {
	private final Map<Object, Object> mapBean = new HashMap<Object, Object>();

	public Map<Object, Object> getMapBean() {
		return mapBean;
	}

	public BeanWithMapAttribute() {
		mapBean.put("1", "A");
		mapBean.put("2", "B");
	}
}
