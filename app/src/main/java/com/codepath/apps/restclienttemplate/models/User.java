package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private long id;
    private long idStr;
    private String name;
    private String screenName;
    private String location;
    private String description;
    private String profileImageURL;
    private boolean verified;

    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setId(jsonObject.getLong("id"));
        user.setIdStr(jsonObject.getLong("id_str"));
        user.setName(jsonObject.getString("name"));
        user.setScreenName(jsonObject.getString("screen_name"));
        user.setLocation(jsonObject.getString("location"));
        user.setDescription(jsonObject.getString("description"));
        user.setProfileImageURL(jsonObject.getString("profile_image_url_https"));
        user.setVerified(jsonObject.getBoolean("verified"));
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdStr() {
        return idStr;
    }

    public void setIdStr(long idStr) {
        this.idStr = idStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
