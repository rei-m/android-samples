package me.rei_m.androidsample.fragment;

import android.app.Activity;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.androidsample.R;
import me.rei_m.androidsample.view.LoaderImageView;

public class AsyncImageViewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    // factory
    public static AsyncImageViewFragment newInstance() {
        AsyncImageViewFragment fragment = new AsyncImageViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AsyncImageViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_async_image_view, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        // カスタムイメージビューを作成
        LoaderImageView liv = (LoaderImageView) getActivity().findViewById(R.id.imageView);
        // 取得するURLをセット
        liv.setUrl("https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xfp1/t1.0-9/10307218_714125065291821_116728579375456989_n.jpg");
        // ローダーを作成しロード開始
        Loader loader = getActivity().getLoaderManager().initLoader(0, null, liv);
        loader.forceLoad();
    }
}
