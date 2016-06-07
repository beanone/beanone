package org.beanone.testbeans;

import java.util.HashMap;
import java.util.Map;

public class BeanWithMapAttribute {
	private Map<Object, Object> mapBean = new HashMap<Object, Object>();

	public BeanWithMapAttribute() {
		mapBean.put("1", "A");
		mapBean.put("2", "B");
	}

	public Map<Object, Object> getMapBean() {
		return mapBean;
	}

	public void setMapBean(Map<Object, Object> mapBean) {
		this.mapBean = mapBean;
	}
}
