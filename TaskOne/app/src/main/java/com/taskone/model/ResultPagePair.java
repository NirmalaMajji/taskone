package com.taskone.model;

import org.w3c.dom.NodeList;

public class ResultPagePair {
    NodeList item;
    String nextPageToken;

    public NodeList getItem() {
        return this.item;
    }

    public void setItem(NodeList item) {
        this.item = item;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
