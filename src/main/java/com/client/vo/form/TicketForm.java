package com.client.vo.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketForm {
	List<EachTicketForm> tickets;
	{
		tickets = new ArrayList();
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
		tickets.add(new EachTicketForm());
	}
	
	
	
	public List<EachTicketForm> getTickets() {
		return tickets;
	}

	public void setTickets(List<EachTicketForm> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return tickets.toString();
	}
	
	public class EachTicketForm {
		private int movieId;
		private String movieName;
		private String screenName;
		private int seatId;
		private String seatName;
		private Date startedAt;
		private int timetableId;
		
		
		public int getMovieId() {
			return movieId;
		}
		public void setMovieId(int movieId) {
			this.movieId = movieId;
		}
		public String getMovieName() {
			return movieName;
		}
		public void setMovieName(String movieName) {
			this.movieName = movieName;
		}
		public String getScreenName() {
			return screenName;
		}
		public void setScreenName(String screenName) {
			this.screenName = screenName;
		}
		public int getSeatId() {
			return seatId;
		}
		public void setSeatId(int seatId) {
			this.seatId = seatId;
		}
		public String getSeatName() {
			return seatName;
		}
		public void setSeatName(String seatName) {
			this.seatName = seatName;
		}
		public Date getStartedAt() {
			return startedAt;
		}
		public void setStartedAt(Date startedAt) {
			this.startedAt = startedAt;
		}
		public int getTimetableId() {
			return timetableId;
		}
		public void setTimetableId(int timetableId) {
			this.timetableId = timetableId;
		}
		@Override
		public String toString() {
			return "EachTicketForm [movieId=" + movieId + ", movieName=" + movieName + ", screenName=" + screenName
					+ ", seatId=" + seatId + ", seatName=" + seatName + ", startedAt=" + startedAt + ", timetableId="
					+ timetableId + "]";
		}
		
	}
}
