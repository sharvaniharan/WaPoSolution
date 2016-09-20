package com.example.sharvani.myapplication.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sharvani.myapplication.R;
import com.example.sharvani.myapplication.activities.MainActivity;
import com.example.sharvani.myapplication.models.HttpClient;
import com.example.sharvani.myapplication.models.News;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;*/
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sharvani on 2/13/16.
 */

public class JSONParserAsync extends AsyncTask<String, String, List<News>> {
    private static final String mQueryString = "http://www.washingtonpost.com/wp-srv/simulation/simulation_test.json";
    private Activity activity;
    private List<News> lNews;
    String result = null;

    public JSONParserAsync(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<News> doInBackground(String... arg0) {


        HttpClient.get(mQueryString, null, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                        result = res;

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Toast.makeText(activity,activity.getString(R.string.error),Toast.LENGTH_LONG).show();
                    }
                }
        );

        //Below is the code that doesnt work to fetch the same JSON.
        // Not sure why AsyncHttp works and not this, especially since the library does exactly the same on an AsyncTask

/*
try {
                    //Creating URL from query String
                    mUrl = new URL(queryString);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

        mUrlConnection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            //Http connection
            mUrlConnection = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000
        conn.setConnectTimeout(15000
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        mUrlConnection.setDoOutput(true);
        conn.connect();


            int statusCode = mUrlConnection.getResponseCode();
            if (statusCode == 200) {//Checking for 'Success' condition

                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                //Converting response to String
                result = convertInputStreamToString(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e) {

            }
        }*/
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject = new JSONObject(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        lNews = News.fromJsonArray(jsonObject);

        return lNews;


    }

    @Override
    protected void onPostExecute(List<News> result) {
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setList(result);
        }
    }

/*    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            *//* Close Stream *//*
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }*/
}