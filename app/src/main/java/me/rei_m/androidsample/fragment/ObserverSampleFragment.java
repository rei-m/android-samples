package me.rei_m.androidsample.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Observable;
import java.util.Observer;

import me.rei_m.androidsample.R;

import me.rei_m.androidsample.entity.AtndEventEntity;
import me.rei_m.androidsample.model.AtndApiModel;
import me.rei_m.androidsample.model.LoaderEvent;
import me.rei_m.androidsample.model.ModelLocator;

public class ObserverSampleFragment extends Fragment
        implements AbsListView.OnItemClickListener, Observer{

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<AtndEventEntity> mAdapter;

    /**
     * AtndAPIモデル
     */
    private AtndApiModel mAtndApiModel;

    /**
     * ファクトリメソッド
     *
     * @return インスタンス
     */
    public static ObserverSampleFragment newInstance() {
        ObserverSampleFragment fragment = new ObserverSampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ObserverSampleFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1);

        // ModelLocatorからAtndAPIモデルを取得してフラグメントをObserverとして登録する
        mAtndApiModel = ModelLocator.getInstance().getAtndApiModel();
        mAtndApiModel.addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item, container, false);

        AbsListView listView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        // AtndAPIモデルから取得結果を取り出し、値が存在しない場合はリクエストを投げる
        // 実際にはAPIのリクエストパラメータとかは変わるはずなので、実用を考えたらもうちょい厳密な判定になるはずだけど。
        if(mAtndApiModel.getList() == null){
            mAtndApiModel.fetchList(view.getContext(), getLoaderManager());
        }else{
            mAdapter.addAll(mAtndApiModel.getList());
            mAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // AtndAPIモデルからフラグメントをObserverから削除する
        mAtndApiModel.deleteObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAtndApiModel = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof LoaderEvent){
            // AtndAPIモデルから通知を受け取ったらモデルからAPIの取得結果を取り出して、ListViewにセットして、Viewを更新
            AtndApiModel atndApiModel = (AtndApiModel) ((LoaderEvent) data).getSource();
            mAdapter.addAll(atndApiModel.getList());
            mAdapter.notifyDataSetChanged();
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
        public void onFragmentInteraction(int id);
    }
}
