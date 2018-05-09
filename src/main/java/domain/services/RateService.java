package domain.services;

import domain.Movie;
import domain.Rating;

public class RateService {
	private Movie movie;
	private static int currentId = -1;

	public void add(Rating r) {
		r.setId(++currentId);
		movie.getRatingList().add(r);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public void setAverageRating(){
		int elementsNumber = movie.getRatingList().size();
		float value = 0;
		for(int i=0;i<elementsNumber;i++){
			value += movie.getRatingList().get(i).getRating();
		}
		value = value / elementsNumber;
		movie.setRating(value);
	}
}
