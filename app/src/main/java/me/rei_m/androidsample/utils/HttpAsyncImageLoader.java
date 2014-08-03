package me.rei_m.androidsample.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rei_m on 2014/08/01.
 */
public class HttpAsyncImageLoader extends AsyncTaskLoader<Bitmap> {

    private String mUrl;

    public HttpAsyncImageLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {

        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            // 404はここでキャッチする
            e.printStackTrace();
        }

        return null;
    }
}
