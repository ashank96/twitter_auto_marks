/*/* Author: Ashank Bharati 28-06-2018
 DownloadTask: This class extends the AsyncTask thereby performs downloading operation in the background and parses the jsonString returned by the
*                HttpHandler class
*                */
package com.example.aloofwillow.chillpilltwitter;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class DownloadTask extends AsyncTask<String, Void, ArrayList<Tweet>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Tweet> doInBackground(String... objects) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        if (objects.length > 0) {
            String jsonString = httpHandler.getTweets("http://decoderssit.club/twitter.php", objects[0], objects[1], objects[2]);

          try {
                JSONArray tweetsArray = new JSONArray(jsonString);
                if(tweetsArray==null) {
                    Log.e("mesg","Reached the limits");
                    return null;
                }
                for (int i = 0; i < tweetsArray.length(); i++) {
                    JSONObject jsonObject = tweetsArray.getJSONObject(i);
                    String timeStamp = jsonObject.getString("created_at");
                    String content = jsonObject.getString("text");
                    String userId  = jsonObject.getJSONObject("user").getString("screen_name");
                    String[] splittedContent=content.split(" |\\n");
                    ArrayList<String> links=new ArrayList<>();
                    for(String s:splittedContent){
                        if(s.contains("https://"))
                            links.add(s);
                    }

                   // Log.e("time",   timeStamp);
                   // Log.e("userId", userId);
                    if (links.size() > 0)   //only when urls present in content
                        tweets.add(new Tweet(timeStamp,userId,links));

                }
                return tweets;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

            return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Tweet> tweets) {
        super.onPostExecute(tweets);
        Gson gson=new Gson();
        //converting TweetObject to json and sending it to the database server
        String json=gson.toJson(new TweetObject(TweetsActivity.user.getDisplayName(), tweets));
        Log.e("json",json);
        new DatabaseTask().execute(json);

    }
}
