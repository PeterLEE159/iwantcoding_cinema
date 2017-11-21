package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("MOVIE_REVIEWS")
public class MovieReview extends BaseVO{
	@Column("ID")
	private int id;
	@Column("CREATED_AT")
	private Date createdAt;
	@Column("RATING_POINT")
	private int ratingPoint;
	@Column("CUSTOMER_ID")
	private int customerId;
	@Column("MOVIE_ID")
	private int movieId;
	
	@Override
	public String toString() {
		return "MovieReview [id=" + id + ", createdAt=" + createdAt + ", ratingPoint=" + ratingPoint + ", customerId="
				+ customerId + ", movieId=" + movieId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getRatingPoint() {
		return ratingPoint;
	}

	public void setRatingPoint(int ratingPoint) {
		this.ratingPoint = ratingPoint;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	@Override
	public BaseVO newInstance() {
		return new MovieReview();
	}
	
}
