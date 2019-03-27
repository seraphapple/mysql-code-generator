package com.mrh.spring.generator.model;

import java.util.Date;

public class MUser {
	
	private Long id;
		
	private String username;
		
	private String desc;
		
	private String password;
		
	private Integer status;
		
	private Double percent;
		
	private Date createTime;
		
	private String address;
		
	private String opinion;
		
	
	
	public void setId (Long id) {
		this.id = id;
	}
	
	
	public Long getId () {
		return this.id;
	}
		
	public void setUsername (String username) {
		this.username = username;
	}
	
	
	public String getUsername () {
		return this.username;
	}
		
	public void setDesc (String desc) {
		this.desc = desc;
	}
	
	
	public String getDesc () {
		return this.desc;
	}
		
	public void setPassword (String password) {
		this.password = password;
	}
	
	
	public String getPassword () {
		return this.password;
	}
		
	public void setStatus (Integer status) {
		this.status = status;
	}
	
	
	public Integer getStatus () {
		return this.status;
	}
		
	public void setPercent (Double percent) {
		this.percent = percent;
	}
	
	
	public Double getPercent () {
		return this.percent;
	}
		
	public void setCreateTime (Date createTime) {
		this.createTime = createTime;
	}
	
	
	public Date getCreateTime () {
		return this.createTime;
	}
		
	public void setAddress (String address) {
		this.address = address;
	}
	
	
	public String getAddress () {
		return this.address;
	}
		
	public void setOpinion (String opinion) {
		this.opinion = opinion;
	}
	
	
	public String getOpinion () {
		return this.opinion;
	}
		
	
}