package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("DISCOUNT_TICKET")
public class DiscountTicket extends BaseVO{
	@Column("ID")
	private int id;
	@Column("DISCOUNT_ID")
	private int discountId;
	@Column("TICKET_RECEIPT_ID")
	private int receiptId;
	
	@Override
	public String toString() {
		return "DiscountTicket [id=" + id + ", discountId=" + discountId + ", receiptId=" + receiptId + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	@Override
	public BaseVO newInstance() {
		return new DiscountTicket();
	}
	
}
