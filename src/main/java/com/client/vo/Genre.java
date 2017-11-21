package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("GENRE")
public class Genre extends BaseVO{
	@Column("ID")
	private int id;
	
	@Override
	public String toString() {
		return "Genre [id=" + id + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public BaseVO newInstance() {
		return new Genre();
	}
}
