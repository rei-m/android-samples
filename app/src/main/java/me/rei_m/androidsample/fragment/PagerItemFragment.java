package me.rei_m.androidsample.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import me.rei_m.androidsample.R;
import me.rei_m.androidsample.entity.FollowerEntity;
import me.rei_m.androidsample.model.FollowerListModel;
import me.rei_m.androidsample.model.ModelLocator;

public class PagerItemFragment extends Fragment implements Observer, View.OnClickListener {

    private static final String ARG_PAGE_NO = "pageNo";

    public static PagerItemFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NO, pageNo);

        PagerItemFragment fragment = new PagerItemFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private FollowerEntity mUser;
    private int mPageNo;
    private TextView mTextView;
    private Button mButton;

    private FollowerListModel mFollowerListModel;

    public PagerItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE_NO);
        mFollowerListModel = ModelLocator.getInstance().getFollowerListModel();
        mFollowerListModel.addObserver(this);

        // 仮のユーザを設定
        mUser = new FollowerEntity();
        mUser.setId("0000001");
        mUser.setName("ほげー");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager_item, container, false);

        mTextView = (TextView) view.findViewById(R.id.text_pager_item_title);
        mTextView.setText(String.valueOf(mPageNo));
        mButton = (Button) view.findViewById(R.id.button_pager_item);
        mButton.setOnClickListener(this);

        // 初期表示の設定
        if(mFollowerListModel.hasFollower(mUser)){
            setFollowedStyle();
        }else{
            setUnFollowedStyle();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFollowerListModel.deleteObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTextView = null;
        mButton = null;
        mFollowerListModel = null;
        mUser = null;
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof FollowerListModel.ChangeFollowerListEvent){
            FollowerListModel.ChangeFollowerListEvent event = (FollowerListModel.ChangeFollowerListEvent) data;
            if(event.getIsAdded()){
                setFollowedStyle();
            }else{
                setUnFollowedStyle();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_pager_item:
                // フォロアーリストを確認して、追加済であれば削除、未追加であれば追加
                if(mFollowerListModel.hasFollower(mUser)){
                    mFollowerListModel.removeFollower(mUser);
                }else{
                    mFollowerListModel.addFollower(mUser);
                }
                break;
        }
    }

    private void setFollowedStyle(){
        mButton.setText(mUser.getName() + "をふぉろーしてるよ");
        mTextView.setBackgroundColor(getResources().getColor(R.color.active));
    }

    private void setUnFollowedStyle(){
        mButton.setText(mUser.getName() + "はまだふぉろーしてないよ");
        mTextView.setBackground(null);
    }
}
