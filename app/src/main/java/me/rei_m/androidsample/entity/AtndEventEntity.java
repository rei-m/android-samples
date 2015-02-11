package me.rei_m.androidsample.entity;

import java.io.Serializable;

/**
 * Created by rei_m on 2015/01/25.
 */
public class AtndEventEntity implements Serializable {

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
