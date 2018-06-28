package com.example.aloofwillow.chillpilltwitter;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseTask extends AsyncTask<String, Void, ArrayList<Tweet>> {



    HttpHandler httpHandler = new HttpHandler();
    String jsonString="";

    @Override
    protected ArrayList<Tweet> doInBackground(String... strings) {
        try {
            if (strings.length > 0) {
                jsonString = httpHandler.getTweets("http://www.decoderssit.club/server.php", strings[0]);
                JSONArray jsonArray= null;
                try {
                    jsonArray = new JSONArray(jsonString);

                ArrayList<Tweet> tweets=new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String userId=jsonObject.getString("user_id");
                    String timeStamp=jsonObject.getString("time");
                    String urls=jsonObject.getString("urls");
                    String[]all_urls=urls.split(" ");
                    ArrayList<String> urlList=new ArrayList<>();
                    for(String s:all_urls)
                        urlList.add(s);
                    Tweet tweet=new Tweet(timeStamp,userId,urlList);
                    tweets.add(tweet);
                    }
                    return tweets;
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }


        }catch (Exception e){
            Log.i("Exception",e.getMessage());
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Tweet> tweets){
       // super.onPostExecute(tweets);
       //
        DashboardActivity.getWebView(tweets);

    }
}
