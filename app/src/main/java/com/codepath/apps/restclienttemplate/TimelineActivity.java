package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    public static final String TAG = "TimelineActivity";
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        // setSupportActionBar(toolbar);
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        client = TwitterApp.getRestClient(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Pull to Refresh:- Fetching new data");
                populateHomeTimeline(client);
            }
        });

        rvTweets = findViewById(R.id.rvTimeline);
        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);

        scrollListner = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "onLoadMore: " + page);
                loadMoreData();
            }
        };
        rvTweets.addOnScrollListener(scrollListner);
        populateHomeTimeline(client);
    }

    private void loadMoreData() {
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray tweetsRaw = json.jsonArray;
                try {
                    adapter.addAll(Tweet.fromJSONArray(tweetsRaw));
                } catch (JSONException e) {
                    Log.e(TAG, "Next Page JSON Parsing Failed");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Next Page Failed Load");
            }
        }, tweets.get(tweets.size() - 1).getId());
    }

    private void populateHomeTimeline(TwitterClient twitterClient) {
        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray tweetsRaw = json.jsonArray;
                    adapter.clear();
                    adapter.addAll(Tweet.fromJSONArray(tweetsRaw));
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                    Log.i(TAG, "Parsing successful");
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Parsing exception");
                }
                Log.i(TAG, "Home timeline retrieved successfully" + json.toString());
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Error occured while retrieving home timeline");
            }
        });
    }
}