package com.example.sharvani.myapplication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharvani on 2/13/16.
 */
public class News {

    private int mId;
    private String mTitle;
    private String mContent;
    private String mDate;

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmDate() {
        return mDate;
    }

    public static News fromJson(JSONObject jsonObject){
        News n = new News();
        try {
            n.mId = Integer.parseInt(jsonObject.getString("id"));
            n.mTitle = jsonObject.getString("title");
            n.mContent = jsonObject.getString("content");
            n.mDate = jsonObject.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return n;
    }

    public static List<News> fromJsonArray(JSONObject jsonObject){
        List<News> posts = new ArrayList<News>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject newsJson = null;
                try {
                    newsJson = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                News news = News.fromJson(newsJson);
                if(news != null){
                    posts.add(news);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}