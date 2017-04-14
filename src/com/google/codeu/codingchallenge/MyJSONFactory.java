package com.google.codeu.codingchallenge;

/**
 * Created by Pablo on 4/5/2017.
 */
public class MyJSONFactory implements JSONFactory {
    @Override
    public JSON object() {

        return new MyJSON();
    }

    @Override
    public JSONParser parser() {
        return new MyJSONParser();
    }
}
