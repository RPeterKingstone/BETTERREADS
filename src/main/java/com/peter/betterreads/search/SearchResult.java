package com.peter.betterreads.search;

import java.util.List;

public class SearchResult {
    int numFound;
    private List<SearchResultBooks> docs;
    
    public int getNumFound() {
        return numFound;
    }
    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }
    public List<SearchResultBooks> getDocs() {
        return docs;
    }
    public void setDocs(List<SearchResultBooks> docs) {
        this.docs = docs;
    }
    

    
}
