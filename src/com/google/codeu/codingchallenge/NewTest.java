package com.google.codeu.codingchallenge;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

/**
 * Created by Pablo on 4/5/2017.
 */
public class NewTest {
    public static void main(String[] args) throws IOException {
        final Tester tests = new Tester();



//        tests.add("Level 0 Json Test", new Test()
//        {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                final MyJSONParser parser = new MyJSONParser();
//                final MyJSON obj = parser.parse("{ \"name\":\"sam doe\" }");
//
//              Asserts.isEqual("sam doe", obj.getString("name"));
//
//
//            }
//        });


//        tests.add("Level 1 JSON Test", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                //testing json object with commas
//                final MyJSONParser parser = new MyJSONParser();
//                final MyJSON obj = parser.parse("{ \"name\":\"sam doe\", " +
//                        "\"school\": \"Manchester High \" }");
//            }
//        });

        tests.add("Level 2 JSON test", new Test() {
            @Override
            public void run(JSONFactory factory) throws Exception {
                final MyJSONParser parser = new MyJSONParser();
                final MyJSON obj = parser.parse("{\"name\":\"John\",\"age\":\"thirty\", \"cars\": { \"car1\":\"Ford\",\"car2\":" +
                        "\"BMW\", \"car3\":\"Fiat\"}}");
                MyJSONParser.Log.i("name", obj.getString("name"));
                MyJSONParser.Log.i("age", obj.getString("age"));
                MyJSONParser.Log.i("cars", obj.getObject("cars").getString("car1"));
            }
        });


        tests.run(new JSONFactory() {
            @Override
            public JSON object() {
                return null;
            }

            @Override
            public JSONParser parser() {
                return null;
            }
        });
    }
}
