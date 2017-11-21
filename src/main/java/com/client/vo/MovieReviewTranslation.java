package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("REVIEW_TRANSLATION")
public class MovieReviewTranslation extends BaseVO{
	@Column("ID")
	private int id;
	@Column("REVIEW")
	private String review;
	@Column("LANG_ISO")
	private String langIso;
	@Column("LANG_DEFAULT")
	private int langDefault;
	@Column("MOVIE_REVIEW_ID")
	private int movieReviewId;
	
	private MovieReview common;
	private Customer customer;
	private MovieTranslation movie;
	
	
	@Override
	public String toString() {
		return "MovieReviewTranslation [id=" + id + ", review=" + review + ", langIso=" + langIso + ", langDefault="
				+ langDefault + ", movieReviewId=" + movieReviewId + ", common=" + common + ", customer=" + customer
				+ ", movie=" + movie + "]";
	}
	public MovieTranslation getMovie() {
		return movie;
	}
	public void setMovie(MovieTranslation movie) {
		this.movie = movie;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public BaseVO newInstance() {
		return new MovieReviewTranslation();
	}
	public MovieReview getCommon() {
		return common;
	}
	public void setCommon(MovieReview common) {
		this.common = common;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getLangIso() {
		return langIso;
	}
	public void setLangIso(String langIso) {
		this.langIso = langIso;
	}
	public int getLangDefault() {
		return langDefault;
	}
	public void setLangDefault(int langDefault) {
		this.langDefault = langDefault;
	}
	public int getMovieReviewId() {
		return movieReviewId;
	}
	public void setMovieReviewId(int movieReviewId) {
		this.movieReviewId = movieReviewId;
	}
	
}
