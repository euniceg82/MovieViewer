package com.exam.movieviewer.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResGetDetails {
    @SerializedName("movie_id")
    @Expose
    public String movieId;
    @SerializedName("advisory_rating")
    @Expose
    public String advisoryRating;
    @SerializedName("canonical_title")
    @Expose
    public String canonicalTitle;
    public String genre;
    public String poster;
    @SerializedName("poster_landscape")
    @Expose
    public String posterLandscape;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("runtime_mins")
    @Expose
    public String runtimeMins;
    public String synopsis;
    @SerializedName("theater")
    @Expose
    public String theatre;
    public List<String> cast;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getAdvisoryRating() {
        return advisoryRating;
    }

    public void setAdvisoryRating(String advisoryRating) {
        this.advisoryRating = advisoryRating;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterLandscape() {
        return posterLandscape;
    }

    public void setPosterLandscape(String posterLandscape) {
        this.posterLandscape = posterLandscape;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }
}
