package com.xrdcode.pertemuan3.model;

import java.util.List;

/**
 * Created by reysd on 12/08/2017.
 */

public class MovieDetail
{
    public boolean adult;
    public String backdrop_path;
    public BelongsToCollection belongs_to_collection;
    public int budget;
    public List<Genre> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public List<ProductionCompany> production_companies;
    public List<ProductionCountry> production_countries;
    public String release_date;
    public int revenue;
    public int runtime;
    public List<SpokenLanguage> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;

    public class BelongsToCollection
    {
        public int id;
        public String name;
        public String poster_path;
        public String backdrop_path;
    }

    public class Genre
    {
        public int id;
        public String name;
    }

    public class ProductionCompany
    {
        public String name;
        public int id;
    }

    public class ProductionCountry
    {
        public String iso_3166_1;
        public String name;
    }

    public class SpokenLanguage
    {
        public String iso_639_1;
        public String name;
    }
}
