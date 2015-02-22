package me.rei_m.androidsample.fragment;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.rei_m.androidsample.R;

import me.rei_m.androidsample.fragment.dummy.DummyContent;
import me.rei_m.androidsample.util.HttpAsyncLoader;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ListViewSampleFragment extends Fragment implements AbsListView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArrayAdapter<DummyContent.DummyItem> mAdapter;

    // 非同期用ローダー
    private Loader mLoader;

    public static ListViewSampleFragment newInstance() {
        ListViewSampleFragment fragment = new ListViewSampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListViewSampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listviewsample, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    // Activity作成時にローダーの初期化を行う
    // LoaderManagerはActivityやFragmentごとに１つだけ存在している。
    // Loaderは複数持てる。initLoaderの第一引数がLoaderをユニークにするIDとなる
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // LoaderManagerの初期化
        Bundle args = new Bundle();
        mLoader = getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        // HttpAsyncLoaderを作成して指定したURLを非同期で読み込む
        HttpAsyncLoader loader = new HttpAsyncLoader(getActivity(), "https://api.atnd.org/events/?keyword_or=google,cloud&format=json");
        loader.forceLoad();
        // LoaderManagerに作成したローダーを返す
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        // ローダーの通信完了後の処理を実装
        if (loader.getId() == 0) {
            try {
                // 受け取ったイベント情報を解析
                JSONObject json = new JSONObject(data);
                int evCnt = json.getInt("results_returned");
                if (evCnt > 0) {
                    JSONArray events = json.getJSONArray("events");
                    for (int i = 0; i < evCnt; i++) {
                        JSONObject ev = events.getJSONObject(i).getJSONObject("event");

                        // イベント情報をセットしたダミークラスのオブジェクトを作り、アダプターに追加する。
                        DummyContent.DummyItem item = new DummyContent.DummyItem(ev.getString("event_id"), ev.getString("title"));
                        mAdapter.add(item);
                    }
                    // 追加された情報をアダプターに通知する
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // 今回は何もしない
    }
}
