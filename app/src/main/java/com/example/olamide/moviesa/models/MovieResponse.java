package com.example.olamide.moviesa.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Olamide on 5/22/17.
 */

public class MovieResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movies> results;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

}
