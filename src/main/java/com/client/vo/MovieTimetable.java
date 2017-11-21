package com.client.vo;

import java.util.Date;
import java.util.List;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("MOVIE_TIMETABLE")
public class MovieTimetable extends BaseVO{
	@Column("ID")
	private int id;
	@Column("STARTED_AT")
	private Date startedAt;
	@Column("ENDED_AT")
	private Date endedAt;
	@Column("ONLINE_PURCHASE")
	private int online;
	@Column("OFFLINE_PURCHASE")
	private int offline;
	@Column("SCREEN_MOVIE_ID")
	private int screenMovieId;
	
	private List<Ticket> tickets;
	private Seat seat;
	
	
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	private MovieTranslation movie;
	private Screen screen;
	
	
	@Override
	public String toString() {
		return "MovieTimetable [id=" + id + ", startedAt=" + startedAt + ", endedAt=" + endedAt + ", online=" + online
				+ ", offline=" + offline + ", screenMovieId=" + screenMovieId + ", tickets=" + tickets + ", seat="
				+ seat + ", movie=" + movie + ", screen=" + screen + "]";
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public MovieTranslation getMovie() {
		return movie;
	}
	public int getScreenMovieId() {
		return screenMovieId;
	}

	public void setScreenMovieId(int screenMovieId) {
		this.screenMovieId = screenMovieId;
	}
	public void setMovie(MovieTranslation movie) {
		this.movie = movie;
	}
	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}
	@Override
	public BaseVO newInstance() {
		return new MovieTimetable();
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public int getOffline() {
		return offline;
	}
	public void setOffline(int offline) {
		this.offline = offline;
	}
	
}
