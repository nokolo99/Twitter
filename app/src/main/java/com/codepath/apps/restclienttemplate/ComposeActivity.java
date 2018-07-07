package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    Button button;
    EditText tweet;

    // declare variables to keep track of changing character count
    TextView tvCharactersLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        button = findViewById(R.id.bnTweet);
        tweet = findViewById(R.id.et_Tweet);
        client = new TwitterClient(this);
        tvCharactersLeft = findViewById(R.id.tvCharactersLeft);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.sendTweet(tweet.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Tweet new_tweet = Tweet.fromJSON(response);
                            Intent i = new Intent();
                            i.putExtra("tweet", Parcels.wrap(new_tweet));
                            setResult(RESULT_OK, i);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        tweet.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This sets a textview to the appropriate amount of characters left
                tvCharactersLeft.setText(String.valueOf(140 - s.length()) + " characters left");

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This sets a textview to the appropriate amount of characters left
                tvCharactersLeft.setText(String.valueOf(140 - s.length()) + " characters left");
            }

            public void afterTextChanged(Editable s) {
                // This sets a textview to the appropriate amount of characters left
                tvCharactersLeft.setText(String.valueOf(140 - s.length()) + " characters left");
            }
        });
    }




}
