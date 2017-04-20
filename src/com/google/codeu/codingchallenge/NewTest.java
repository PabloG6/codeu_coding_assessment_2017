package com.google.codeu.codingchallenge;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

/**
 * Created by Pablo on 4/5/2017.
 */
public class NewTest {
    public static void main(String[] args) throws IOException {
        final Tester tests = new Tester();

        MyJSONParser parser = new MyJSONParser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", " +
                "\"last\":\"doe\", \"name2\":{\"first\":\"bob\", " +
                "\"last\":\"murphy\", \"middle\":\"robert\" } }  }");


    }
}
