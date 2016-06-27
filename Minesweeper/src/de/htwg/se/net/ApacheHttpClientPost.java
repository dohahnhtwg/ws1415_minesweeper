package de.htwg.se.net;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ApacheHttpClientPost {
    private HttpClient httpClient;
    private Gson gson;
    private HttpPost post;

    public ApacheHttpClientPost() {
        String postUrl = "http://de-htwg-sa-highscores.herokuapp.com/";
        httpClient = HttpClientBuilder.create().build();
        gson = new Gson();
        post = new HttpPost(postUrl);
    }

    public void post(final Highscore highscore) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringEntity postingString = new StringEntity(gson.toJson(highscore));
                    post.setEntity(postingString);
                    post.setHeader("Content-type", "application/json");
                    httpClient.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void main(String[] args) {
        ApacheHttpClientPost cl = new ApacheHttpClientPost();
        Highscore highscore = new Highscore(
                "Minesweeper",
                "Udo",
                "322"
        );
        cl.post(highscore);
    }
}
