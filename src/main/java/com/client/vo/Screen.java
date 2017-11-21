package com.client.vo;

import java.util.List;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("SCREENS")
public class Screen extends BaseVO{
	@Column("ID")
	private int id;
	@Column("SEATS_COUNT")
	private int seatCount;
	@Column("NAME")
	private String name;
	
	private List<Seat> seats;
	private List<MovieTimetable> timetables;
	
	@Override
	public String toString() {
		return "Screen [id=" + id + ", seatCount=" + seatCount + ", name=" + name + "]";
	}
	
	public List<MovieTimetable> getTimetables() {
		return timetables;
	}

	public void setTimetables(List<MovieTimetable> timetables) {
		this.timetables = timetables;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public BaseVO newInstance() {
		return new Screen();
	}
	
}
