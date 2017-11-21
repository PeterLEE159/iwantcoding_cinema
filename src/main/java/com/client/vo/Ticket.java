package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("TICKETS")
public class Ticket extends BaseVO{
	@Column("ID")
	private int id;
	@Column("MOVIE_TIMETABLE_ID")
	private int movietimetableId;
	@Column("TICKET_RECEIPT_ID")
	private int ticketReceiptId;
	@Column("CUSTOMER_TYPE_ID")
	private int customerTypeId;
	@Column("SEAT_ID")
	private int seatId;
	
	private Seat seat;
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", movietimetableId=" + movietimetableId + ", ticketReceiptId=" + ticketReceiptId
				+ ", customerTypeId=" + customerTypeId + ", seatId=" + seatId + ", seat=" + seat + "]";
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	@Override
	public BaseVO newInstance() {
		return new Ticket();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovietimetableId() {
		return movietimetableId;
	}
	public void setMovietimetableId(int movietimetableId) {
		this.movietimetableId = movietimetableId;
	}
	public int getTicketReceiptId() {
		return ticketReceiptId;
	}
	public void setTicketReceiptId(int ticketReceiptId) {
		this.ticketReceiptId = ticketReceiptId;
	}
	public int getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	
}
