/* Author: Ashank Bharati 28-06-2018*/
/*TweetObject class holds all the twitter home feed under a specific user*/

package com.example.aloofwillow.chillpilltwitter;

import java.util.ArrayList;

public class TweetObject {
    private String username;
    private ArrayList<Tweet> tweets=new ArrayList<>();
    public TweetObject(){

    }

    public TweetObject(String username, ArrayList<Tweet> tweets) {
        this.username = username;
        this.tweets = tweets;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "TweetObject{" +
                "username='" + username + '\'' +
                ", tweets=" + tweets +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }
}
