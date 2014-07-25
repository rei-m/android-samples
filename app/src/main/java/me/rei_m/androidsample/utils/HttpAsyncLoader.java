package me.rei_m.androidsample.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by rei_m on 2014/07/24.
 */
public class HttpAsyncLoader extends AsyncTaskLoader<String> {

    private String url;

    public HttpAsyncLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public String loadInBackground() {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            String responseBody = httpClient.execute(new HttpGet(this.url),
                    new ResponseHandler<String>() {
                        @Override
                        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                            if(HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
                                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                            }else{
                                return null;
                            }

                        }
                    });
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }
}
