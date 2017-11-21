package com.client.vo;

import java.util.ArrayList;
import java.util.List;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("MOVIE_TRANSLATION")
public class MovieTranslation extends BaseVO{
	@Column("ID")
	private int id;
	@Column("NAME")
	private String name;
	@Column("DESCRIPTION")
	private String description;
	@Column("PUBLISH_COUNTRY")
	private String publishCountry;
	@Column("DIRECTOR")
	private String director;
	@Column("LANG_ISO")
	private String langIso;
	@Column("LANG_DEFAULT")
	private int langDefault;
	@Column("MOVIE_ID")
	private int movieId;
	
	private Movie common;
	private List<MovieImage> images;
	private List<MovieReviewTranslation> reviews;
	private List<MovieGenreTranslation> genres;
	private List<Screen> screens;
	private double ratingPoint;
	private int ratingCount;
	public double getRatingPoint() {
		return ratingPoint;
	}
	
	
	public int getRatingCount() {
		return ratingCount;
	}


	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}


	@Override
	public String toString() {
		return "MovieTranslation [id=" + id + ", name=" + name + ", description=" + description + ", publishCountry="
				+ publishCountry + ", director=" + director + ", langIso=" + langIso + ", langDefault=" + langDefault
				+ ", movieId=" + movieId + ", common=" + common + ", images=" + images + ", reviews=" + reviews
				+ ", genres=" + genres + ", ratingPoint=" + ratingPoint + "]";
	}
	public void addGenre(MovieGenreTranslation genre) {
		if(this.genres == null)
			this.genres = new ArrayList();
		this.genres.add(genre);
	}
	
	public List<MovieReviewTranslation> getReviews() {
		return reviews;
	}

	public void setReviews(List<MovieReviewTranslation> reviews) {
		this.reviews = reviews;
	}

	public List<Screen> getScreens() {
		return screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}

	public List<MovieGenreTranslation> getGenres() {
		return genres;
	}

	public void setGenres(List<MovieGenreTranslation> genres) {
		this.genres = genres;
	}

	public void setRatingPoint(double ratingPoint) {
		this.ratingPoint = ratingPoint;
	}

	public Movie getCommon() {
		return common;
	}

	public void setCommon(Movie movie) {
		this.common = movie;
	}

	public List<MovieImage> getImages() {
		return images;
	}

	public void setImages(List<MovieImage> images) {
		this.images = images;
	}
	
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishCountry() {
		return publishCountry;
	}

	public void setPublishCountry(String publishCountry) {
		this.publishCountry = publishCountry;
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

	@Override
	public BaseVO newInstance() {
		return new MovieTranslation();
	}
}
