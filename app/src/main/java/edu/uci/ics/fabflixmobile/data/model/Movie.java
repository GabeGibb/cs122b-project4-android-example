package edu.uci.ics.fabflixmobile.data.model;

/**
 * Movie class that captures movie information for movies retrieved from MovieListActivity
 */
public class Movie {
    private final String name;
    private final short year;

    private final String[] actors;
    private final String[] genres;

    public Movie(String name, short year, String[] actors, String[] genres) {
        this.name = name;
        this.year = year;
        this.actors = actors;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    public String getActors() {
        String actorsString = "";
        for(int i = 0; i < actors.length; i++){
            if (i >=3){
                break;
            }
            actorsString += actors[i] + ", ";
        }
        return actorsString;
    }

    public String getGenres() {
        String genresString = "";
        for(int i = 0; i < genres.length; i++){
            if (i >=3){
                break;
            }
            genresString += genres[i] + ", ";
        }
        return genresString;
    }
}