package org.beanone.testbeans;

import java.lang.reflect.Type;

import org.beanone.ValueDiff;

import com.google.gson.InstanceCreator;

public class ValueDiffInstanceCreator implements InstanceCreator<ValueDiff> {

	@Override
	public ValueDiff createInstance(Type arg0) {
		return new ValueDiff();
	}

}
