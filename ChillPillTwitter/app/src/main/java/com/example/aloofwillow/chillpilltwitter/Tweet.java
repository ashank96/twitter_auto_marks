/* Author: Ashank Bharati 28-06-2018*/
 /*Tweet class holds all the attributes of a tweet home which are timestamp,urls,and creater id */
package com.example.aloofwillow.chillpilltwitter;

import java.util.ArrayList;

public class Tweet {

    private String createdAt;
    private String createrId;
    private ArrayList<String > urls;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "createdAt=" + createdAt +
                ", createrId='" + createrId + '\'' +
                ", urls=" + urls +
                '}';
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public Tweet(String createdAt, String createrId, ArrayList<String> urls) {
        this.createdAt = createdAt;
        this.createrId = createrId;

        this.urls = urls;
    }
}
