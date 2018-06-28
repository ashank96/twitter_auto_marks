/*/* Author: Ashank Bharati 28-06-2018
Http Handler: This class talks to the server by writing data to the output stream of the Http Url connection and reading from
                the input stream json string returned from the server.
                It returns the json response obtained from the server to the DownloadTask.java
*/

package com.example.aloofwillow.chillpilltwitter;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String getTweets(String request_url,String json){
        String JSONString=null;
        try {
            //initializing my server url
            URL url = new URL(request_url+"?json="+json);
            //opening http-connection for url
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            //setting http method to POST
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput (true);
            httpURLConnection.setUseCaches (false);
            httpURLConnection.setRequestProperty("Content-Type","application/json");

            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            while((JSONString=bufferedReader.readLine())!=null){
                stringBuilder.append(JSONString+"\n");
            }
            bufferedReader.close();

            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return JSONString;
    }


    public String getTweets(String request_url,String username,String authToken,String authTokenSecret) {
        String JSONString = null;
        try {
            //initializing my server url
            URL url = new URL(request_url);
            //opening http-connection for url
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            //setting http method to POST
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            //getting the instance of output stream of the connection to write(send to the server) the twitter username
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //initializing buffered writer for the output stream
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            //encoding to add username to the URL for
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")+
                    "&"+URLEncoder.encode("authToken", "UTF-8") + "=" + URLEncoder.encode(authToken, "UTF-8")+
                    "&"+URLEncoder.encode("authTokenSecret", "UTF-8") + "=" + URLEncoder.encode(authTokenSecret, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            // read the response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            while((JSONString=bufferedReader.readLine())!=null){
                stringBuilder.append(JSONString+"\n");
            }
            bufferedReader.close();

            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return JSONString;
    }

}
