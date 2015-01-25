package me.rei_m.androidsample.model;

/**
 * Created by rei_m on 2015/01/25.
 */
public class ModelLocator {

    private final static ModelLocator instance = new ModelLocator();

    public static ModelLocator getInstance() {return instance;}

    private AtndApi atndApi;

    public AtndApi getAtndApi() {
        return atndApi;
    }

    public void setAtndApi(AtndApi atndApi) {
        this.atndApi = atndApi;
    }
}
