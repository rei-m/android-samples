package me.rei_m.androidsample.model;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Observable;

import me.rei_m.androidsample.entity.FollowerEntity;

public class FollowerListModel extends Observable{

    public static FollowerListModel createInstance(){

        FollowerListModel instance = new FollowerListModel();
        instance.mFollowerList = new HashMap<>();

        return instance;
    }

    private FollowerListModel(){}

    private HashMap<String, FollowerEntity> mFollowerList;

    public HashMap<String, FollowerEntity> getFollowerList() {
        return mFollowerList;
    }

    public boolean hasFollower(FollowerEntity target){
        return mFollowerList.containsKey(target.getId());
    }

    public void addFollower(FollowerEntity target){
        mFollowerList.put(target.getId(), target);
        notifyEvent(true);
    }

    public void removeFollower(FollowerEntity target){
        mFollowerList.remove(target.getId());
        notifyEvent(false);
    }

    private void notifyEvent(boolean isAdded){
        ChangeFollowerListEvent event = new ChangeFollowerListEvent(this);
        event.setIsAdded(isAdded);
        setChanged();
        notifyObservers(event);
    }

    public static class ChangeFollowerListEvent extends EventObject {
        public ChangeFollowerListEvent(Object source){
            super(source);
        }
        private boolean isAdded;

        public boolean getIsAdded() {
            return isAdded;
        }

        public void setIsAdded(boolean isAdded) {
            this.isAdded = isAdded;
        }
    }

}
