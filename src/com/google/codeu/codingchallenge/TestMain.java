// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import com.sun.deploy.security.MozillaMyKeyStore;

import java.util.Collection;
import java.util.HashSet;

final class TestMain {

    public static void main(String[] args) {
        final Tester tests = new Tester();
//        tests.add("Test 0", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                final JSONParser parser = factory.parser();
//                final JSON obj = parser.parse("{ }");
//                final Collection<String> strings = new HashSet<>();
//                obj.getStrings(strings);
//
//                Asserts.isEqual(strings.size(), 0);
//
//                final Collection<String> objects = new HashSet<>();
//                obj.getObjects(objects);
//
//                Asserts.isEqual(objects.size(), 0);
//            }
//        });

//        tests.add("Test 1", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                final MyJSONParser parser = new MyJSONParser();
//                final JSON obj = parser.parse("{ \"name\":\"sam doe\" }");
//
//                Asserts.isEqual("sam doe", obj.getString("name"));
//            }
//        });
//
//        tests.add("Level 2", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//
//                final JSONParser parser = factory.parser();
//                final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");
//
//                final JSON nameObj = obj.getObject("name");
//
//                Asserts.isNotNull(nameObj);
//                Asserts.isEqual("sam", nameObj.getString("first"));
//                Asserts.isEqual("doe", nameObj.getString("last"));
//            }
//        });



//        tests.add("Level 3 Json Test", new Test()
//        {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                final MyJSONParser parser = new MyJSONParser();
//                final JSON obj = parser.parse("{ \"name\":\"sam doe\" }");
//
//              Asserts.isEqual("sam doe", obj.getString("name"));
//
//
//            }
//        });
//
//
//        tests.add("Level 4 JSON Test", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                //testing json object with commas
//                final MyJSONParser parser = new MyJSONParser();
//                final JSON obj = parser.parse("{ \"name\":\"sam doe\", " +
//                        "\"school\": \"Manchester High \" }");
//
//                Asserts.isEqual("sam doe", obj.getString("name"));
//            }
//        });
//
//      tests.add("Level 5 JSON test", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                final MyJSONParser parser = new MyJSONParser();
//                final JSON obj = parser.parse("{\"name\":\"John\",\"age\":\"thirty\", \"cars\": { \"car1\":\"Ford\",\"car2\":" +
//                        "\"BMW\", \"car3\":\"Fiat\"}}");
//
//                Asserts.isEqual("John", obj.getString("name"));
//                Asserts.isEqual("thirty", obj.getString("age"));
//
//                Asserts.isEqual("Ford", obj.getObject("cars").getString("car1"));
//
//
//            }
//        });
        tests.add("Level 5 JSON Test, testing for correct number of commas", new Test() {
            @Override
            public void run(JSONFactory factory) throws Exception {
                MyJSONParser jsonParser = new MyJSONParser();
                final JSON obj = jsonParser.parse("{\"first\":\"sam\" \"last\":\"doe\"}");

            }
        });

        tests.add("Level 6 JSON Test", new Test() {
            @Override
            public void run(JSONFactory factory) throws Exception {
                MyJSONParser parser = new MyJSONParser();
                final JSON obj = (parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\"} ,\"places\":{\"country\":\"Jamaica\"}}"));
                Asserts.isEqual("sam", obj.getObject("name").getString("first"));
            }
        });


//        tests.add("Test case 8, {{}, {}}", new Test() {
//            @Override
//            public void run(JSONFactory factory) throws Exception {
//                MyJSONParser jsonParser = new MyJSONParser();
//                JSON myJSON = jsonParser.parse("{\"students\":" +
//                        "{\"name\":\"first_name\":\"Pablo\", \"last_name\":\"Grant\"," +
//
//                        "\"paul\":{\"test\":\"works\"} }}");
//            }
//        });

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









        tests.run(new JSONFactory() {
            @Override
            public JSONParser parser() {
                return new MyJSONParser();
            }

            @Override
            public JSON object() {
                return new MyJSON();
            }
        });
    }
}
