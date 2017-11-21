package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("DISCOUNTS")
public class Discount extends BaseVO{
	@Column("ID")
	private int id;
	@Column("NAME")
	private String name;
	@Column("DETAIL")
	private String detail;
	@Column("DISCOUNT_TYPE")
	private String discountType;
	@Column("DISCOUNT_PERCENT")
	private double discountPercent;
	@Column("STARTED_DATE")
	private Date startedDate;
	@Column("END_DATE")
	private Date endedDate;
	
	@Override
	public String toString() {
		return "Discount [id=" + id + ", name=" + name + ", detail=" + detail + ", discountPercent=" + discountPercent
				+ ", startedDate=" + startedDate + ", endedDate=" + endedDate + "]";
	}
	
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Date getEndedDate() {
		return endedDate;
	}

	public void setEndedDate(Date endedDate) {
		this.endedDate = endedDate;
	}

	@Override
	public BaseVO newInstance() {
		return new Discount();
	}
	
}
