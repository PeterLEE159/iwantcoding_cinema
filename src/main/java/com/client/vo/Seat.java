package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("SEATS")
public class Seat extends BaseVO{
	@Column("ID")
	private int id;
	@Column("STATUS")
	private String status;
	@Column("DETAIL")
	private String detail;
	@Column("NAME")
	private String name;
	@Column("SCREEN_ID")
	private int screenId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	@Override
	public BaseVO newInstance() {
		return new Seat();
	}
}
