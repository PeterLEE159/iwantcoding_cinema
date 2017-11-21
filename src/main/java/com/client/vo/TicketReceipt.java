package com.client.vo;

import java.util.Date;
import java.util.List;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("TICKET_RECEIPTS")
public class TicketReceipt extends BaseVO{
	@Column("ID")
	private int id;
	@Column("RID")
	private String rid;
	@Column("PRICE")
	private int price;
	@Column("PURCHASE_TYPE")
	private String purchaseType;
	@Column("PURCHASE_DATE")
	private Date purchaseDate;
	@Column("DISCOUNTED_PRICE")
	private int discountPrice;
	@Column("POS_ID")
	private int posId;
	@Column("CUSTOMER_ID")
	private int customerId;
	
	private List<Ticket> tickets;
	
	@Override
	public String toString() {
		return "TicketReceipt [id=" + id + ", rid=" + rid + ", price=" + price + ", purchaseType=" + purchaseType
				+ ", purchaseDate=" + purchaseDate + ", discountPrice=" + discountPrice + ", posId=" + posId
				+ ", customerId=" + customerId + ", tickets=" + tickets + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getPosId() {
		return posId;
	}

	public void setPosId(int posId) {
		this.posId = posId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public BaseVO newInstance() {
		return new TicketReceipt();
	}
	
}
