package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("GIFTS")
public class Gift extends BaseVO{
	@Column("ID")
	private int id;
	@Column("DISTRIBUTE_TYPE")
	private String distributeType;
	@Column("DISTRIBUTE_UNTIL")
	private Date distributeUntil;
	@Column("REASON")
	private String reason;
	@Column("NAME")
	private String name;
	@Column("CUSTOMER_RANK_ID")
	private int customerRankId;
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", distributeType=" + distributeType + ", distributeUntil=" + distributeUntil
				+ ", reason=" + reason + ", name=" + name + ", customerRankId=" + customerRankId + "]";
	}
	@Override
	public BaseVO newInstance() {
		return new Gift();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDistributeType() {
		return distributeType;
	}
	public void setDistributeType(String distributeType) {
		this.distributeType = distributeType;
	}
	public Date getDistributeUntil() {
		return distributeUntil;
	}
	public void setDistributeUntil(Date distributeUntil) {
		this.distributeUntil = distributeUntil;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCustomerRankId() {
		return customerRankId;
	}
	public void setCustomerRankId(int customerRankId) {
		this.customerRankId = customerRankId;
	}
}
