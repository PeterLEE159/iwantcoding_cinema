package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;

@Table("MOVIE_GENRE")
public class MovieGenre extends BaseVO{
	@Column("ID")
	private int id;
	@Column("MOVIE_ID")
	private int movieId;
	@Column("GENRE_ID")
	private int genreId;
	
	@Override
	public String toString() {
		return "MovieGenre [id=" + id + ", movieId=" + movieId + ", genreId=" + genreId + "]";
	}
	@Override
	public BaseVO newInstance() {
		return new MovieGenre();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	
	
}
