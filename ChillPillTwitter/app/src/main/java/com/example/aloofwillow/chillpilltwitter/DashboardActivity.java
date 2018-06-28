package com.example.aloofwillow.chillpilltwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
   static WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        webView= (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        Intent intent=getIntent();
        String currentUser=intent.getStringExtra("user");
        String authToken=intent.getStringExtra("authtoken");
        String authTokenSecret=intent.getStringExtra("authtokenSecret");
        new DownloadTask().execute(currentUser, authToken, authTokenSecret);
    }

    public static void getWebView(ArrayList<Tweet> tweets){
            Log.i("msg",tweets.toString());
            //webView.loadUrl("file:///android_asset/index.html");
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("<html>" +
                    "<head>\n" +
                    "  <title>Twitter Auto Marks</title>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                    "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                    "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                    "</head>" +
                    "<body>" +
                    "<h1 class='text-center'>DASHBOARD</h1>" +
                    "<table style=\"width:100%\"><tr>\n" +
                    "    <th class='text-center'>TIMESTAMP</th>\n" +
                    "    <th class= 'text-center'>USER-ID</th> \n" +
                    "    <th class= 'text-center'>BOOKMARKS</th>\n" +
                    "  </tr>\n" );
            for(Tweet tweet : tweets){
                stringBuilder.append("<tr><td>"+tweet.getCreatedAt()+"</td><td>"+tweet.getCreaterId()+"</td>");

                for(String url:tweet.getUrls())
                    stringBuilder.append("<td><a href="+url+">"+url+"</a><br>");
                stringBuilder.append("</td></tr>");
            }
            stringBuilder.append("" +
                    "</table>" +
                    "</body>" +
                    "</html>");
            webView.loadData(stringBuilder.toString(), "text/html; charset=utf-8", "utf-8");
    }



}
