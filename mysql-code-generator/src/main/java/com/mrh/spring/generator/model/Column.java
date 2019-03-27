package com.mrh.spring.generator.model;

public class Column {
	private String columnName;
	
	private String columnType;
	
	private String reference;
	
	private int nullAble;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getNullAble() {
		return nullAble;
	}

	public void setNullAble(int nullAble) {
		this.nullAble = nullAble;
	}
}
