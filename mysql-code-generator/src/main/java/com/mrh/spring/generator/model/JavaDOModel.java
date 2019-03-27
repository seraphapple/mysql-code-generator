package com.mrh.spring.generator.model;

import java.util.List;

public class JavaDOModel {
	
	private String name;
	
	private List<JavaDOPropModel> props;
	
	private String packageStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<JavaDOPropModel> getProps() {
		return props;
	}

	public void setProps(List<JavaDOPropModel> props) {
		this.props = props;
	}

	public String getPackageStr() {
		return packageStr;
	}

	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}

}
