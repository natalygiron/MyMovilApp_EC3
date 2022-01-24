package com.example.myapp_natalygiron.model;

import java.util.ArrayList;

public class LyricsResponse {

    private ArrayList<Lyrics> results;

    public ArrayList<Lyrics> getResults() {
        return results;
    }

//    Type collectionType = new TypeToken<Collection<Lyrics>>(){}.getType();
    // Collection<Lyrics> lyrics = gson.fromJson( results,collectionType);

    public void setResults(ArrayList<Lyrics> results) {
        this.results = results;
    }
}
