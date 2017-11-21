package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("CUSTOMER_RANK")
public class CustomerRank extends BaseVO{
	@Column("ID")
	private int id;
	@Column("TYPE")
	private String name;
	@Column("PRIORITY")
	private int priority;
	
	@Override
	public String toString() {
		return "CustomerRank [id=" + id + ", name=" + name + ", priority=" + priority + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Override
	public BaseVO newInstance() {
		return new CustomerRank();
	}
	
}
