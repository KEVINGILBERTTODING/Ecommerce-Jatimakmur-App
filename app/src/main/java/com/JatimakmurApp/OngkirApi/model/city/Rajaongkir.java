package com.JatimakmurApp.OngkirApi.model.city;

/**
 * Created by Robby Dianputra on 2/13/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rajaongkir {

    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
