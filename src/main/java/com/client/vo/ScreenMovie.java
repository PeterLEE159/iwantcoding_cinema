package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("SCREEN_MOVIES")
public class ScreenMovie extends BaseVO{
	@Column("ID")
	private int id;
	@Column("SCREEN_ID")
	private int screenId;
	@Column("MOVIE_ID")
	private int movieId;
	@Override
	public BaseVO newInstance() {
		return new ScreenMovie();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
}
