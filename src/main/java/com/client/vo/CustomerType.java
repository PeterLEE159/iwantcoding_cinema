package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("CUSTOMER_TYPES")
public class CustomerType extends BaseVO{
	@Column("ID")
	private int id;
	@Column("TYPE")
	private String name;
	@Column("DETAIL")
	private String detila;
	@Column("PRICE")
	private int price;
	
	
	public String getDetila() {
		return detila;
	}
	public void setDetila(String detila) {
		this.detila = detila;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public BaseVO newInstance() {
		return new CustomerType();
	}
	
}
