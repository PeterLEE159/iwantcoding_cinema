package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("GIFT_CUSTOMERS")
public class GiftCustomer extends BaseVO{
	@Column("ID")
	private int id;
	@Column("RECEIVED_AT")
	private Date receivedAt;
	@Column("RECEIVED")
	private int received;
	@Column("CUSTOMER_ID")
	private int customerId;
	@Column("GIFT_ID")
	private int giftId;
	@Override
	public BaseVO newInstance() {
		return new GiftCustomer();
	}
	@Override
	public String toString() {
		return "GiftCustomer [id=" + id + ", receivedAt=" + receivedAt + ", customerId=" + customerId + ", giftId="
				+ giftId + "]";
	}
	public int getReceived() {
		return received;
	}
	public void setReceived(int received) {
		this.received = received;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getReceivedAt() {
		return receivedAt;
	}
	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getGiftId() {
		return giftId;
	}
	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}
}
