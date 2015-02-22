package me.rei_m.androidsample.model;

/**
 * Created by rei_m on 2015/01/25.
 */
public class ModelLocator {

    private final static ModelLocator instance = new ModelLocator();

    public static ModelLocator getInstance() {
        return instance;
    }

    private ModelLocator() {
    }

    private AtndApiModel atndApiModel;

    private FollowerListModel followerListModel;

    public AtndApiModel getAtndApiModel() {
        return atndApiModel;
    }

    public void setAtndApiModel(AtndApiModel atndApiModel) {
        this.atndApiModel = atndApiModel;
    }

    public FollowerListModel getFollowerListModel() {
        return followerListModel;
    }

    public void setFollowerListModel(FollowerListModel followerListModel) {
        this.followerListModel = followerListModel;
    }

    public void initialize() {

        // モデルの初期化
        setAtndApiModel(AtndApiModel.createInstance());
        setFollowerListModel(FollowerListModel.createInstance());
    }
}
