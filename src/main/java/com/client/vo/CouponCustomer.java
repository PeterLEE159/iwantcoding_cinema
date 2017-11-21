package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("COUPON_CUSTOMERS")
public class CouponCustomer extends BaseVO{
	@Column("ID")
	private int id;
	@Column("RECEIVED_AT")
	private Date receivedAt;
	@Column("EXPIRED_AT")
	private Date expiredAt;
	@Column("USED")
	private int used;
	@Column("RECEIVED")
	private int received;
	@Column("CUSTOMER_ID")
	private int customerId;
	@Column("COUPON_ID")
	private int couponId;
	@Column("TICKET_RECEIPT_ID")
	private int receiptId;
	
	private Coupon coupon;
	
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
@Override
	public String toString() {
		return "CouponCustomer [id=" + id + ", receivedAt=" + receivedAt + ", expiredAt=" + expiredAt + ", used=" + used
				+ ", received=" + received + ", customerId=" + customerId + ", couponId=" + couponId + ", receiptId="
				+ receiptId + ", coupon=" + coupon + "]";
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	@Override
	public BaseVO newInstance() {
		return new CouponCustomer();
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
	public Date getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	
}
