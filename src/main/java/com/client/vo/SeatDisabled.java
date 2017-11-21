package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("SEAT_DISABLED")
public class SeatDisabled extends BaseVO{
	@Column("ID")
	private int id;
	@Column("TIME_LIMIT")
	private int timeLimit;
	@Column("SEAT_ID")
	private int seatId;
	@Override
	public BaseVO newInstance() {
		return new SeatDisabled();
	}
	
	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
