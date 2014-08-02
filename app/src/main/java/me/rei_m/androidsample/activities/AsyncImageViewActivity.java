package me.rei_m.androidsample.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import me.rei_m.androidsample.R;
import me.rei_m.androidsample.views.LoaderImageView;

public class AsyncImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_image_view);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.async_image_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_async_image_view, container, false);
            return rootView;
        }


        @Override
        public void onStart() {
            super.onStart();
            LoaderImageView liv = (LoaderImageView) getActivity().findViewById(R.id.imageView);
            liv.setUrl("https://a248.e.akamai.net/f/248/99838/30m/www.ikyu.com/dg/image/logo/iklogo_20111206.gif");

            Loader loader = getActivity().getLoaderManager().initLoader(0, null, liv);
            loader.forceLoad();
        }
    }
}
