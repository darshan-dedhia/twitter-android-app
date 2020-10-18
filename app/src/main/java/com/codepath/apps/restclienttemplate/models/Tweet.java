package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    private String body;
    private String createdAt;
    private User user;
    private long id;

    public Tweet(){}

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        String body = jsonObject.getString("text");
        String createdAt = jsonObject.getString("created_at");
        User user = User.fromJSON(jsonObject.getJSONObject("user"));
        long id = jsonObject.getLong("id");
        tweet.setBody(body);
        tweet.setCreatedAt(createdAt);
        tweet.setUser(user);
        tweet.setId(id);
        return tweet;
    }

    public static List<Tweet> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJSON(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
