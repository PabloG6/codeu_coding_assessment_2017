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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MyJSONParser implements JSONParser {
    private static final String key_regex = "(\"[\\w\\s ]+\")";
    private JSON json = new MyJSON();
    GenQueue<Integer> frontBrace = new GenQueue<>();
    Stack<Integer> backBrace = new Stack<>();

    private int preComma = 1;
    private int nextComma;
    int commaCount;

    //keep position of commas and colons
    GenQueue<Integer> commas = new GenQueue<>();

    @Override
    public JSON parse(String in) throws IOException {

        //create boolean array to store position of curly braces.
        //true means "{", false means "}"
        if (isBalanced(in)) {

            json = testJsonIfy(in);

            return json;
        } else {

            throw new IOException("Invalid number of curly braces");
        }
    }

    /**
     * @param in string to validate
     * @return return false if expression is unbalanced
     */


    private boolean isBalanced(String in) throws IOException {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            char var = in.charAt(i);
            if (var == ':') {

            }


            if (var == ',') {
                commas.enqueue(i);
            }
            if (var == '{') {
                brackets.push(var);
                frontBrace.enqueue(i);

            } else if (var == '}') {


                if (brackets.isEmpty()) return false;
                if (brackets.pop() != '{') return false;
                backBrace.push(i);

            }
        }

        //if commas size less than colons size by more than one, return false;

        return brackets.isEmpty();
    }

    private static String matcher(String key, String in) throws IOException {
        Pattern pattern = Pattern.compile(key);
        Matcher match = pattern.matcher(in);

        if (match.find()) {
            return match.group().substring(1, match.group().length() - 1);
        }
        return null;
    }


    private JSON jsonify(String in) throws IOException {
        int start = eatSpaces(in);
        in = in.substring(start);


        //find first colon


        while (in.contains(":")) {

            //start to the right of colon
            int val = eatSpaces(in.indexOf(":") + 1, in);

            if (in.substring(val).charAt(0) == '{') {
                String key = matcher(key_regex, in);
                in = in.substring(val + 1);
                json.setObject(key, deepJSON(in.substring(0, in.indexOf('}') + 1)));


            } else {
                try {
                    String key = matcher(key_regex, in);
                    if (key == null)
                        throw new IOException("Key is null");

                    in = in.substring(in.indexOf(":"));
                    String object = matcher(key_regex, in);
                    if (object == null) {
                        throw new IOException("Object is null");
                    }

                    in = in.substring(object.length() + 3);
                    val = eatSpaces(0, in);
                    in = in.substring(val);
                    if (in.charAt(0) != ',' & in.charAt(0) != '}') {

                    }

                    json.setString(key, object);
                } catch (StringIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

            }

        }


        return json;
    }


    /**
     * find next json object in string
     *
     * @param in substring to check for json object
     * @return MyJSON object
     */

    private MyJSON deepJSON(String in) throws IOException {
        ;
        MyJSON object = new MyJSON();
        int start = eatSpaces(in.indexOf(":") + 1, in);
        if (in.charAt(in.substring(start).charAt(0)) == '{') {
            String key1 = findKey(0, in);

            //modify the string to remove the key found
            in = in.substring(key1.length() + 3);
            return object.setObject(key1, deepJSON(in.substring(in.indexOf(":") + 1, in.indexOf("}") + 1)));
        } else {
            //if the next object is not a { but a string create a json object

            //if the string in question contains commas, split it into an array of json objects
            if (in.contains(",")) {
                //find next two commas
                int first = 0;
                int second = in.indexOf(',');

                while (first != -1 && second != -1) {
                    System.out.printf("first: %d, second: %d \n", first, second);
                    String s = in.substring(first + 1, second);
                    first = in.indexOf(',', first + 1);
                    second = in.indexOf(',', second + 1);
                    if (s.contains("{")) {
                        String key2 = matcher(key_regex, in);
                        object.setObject(key2, deepJSON(in.substring(first).substring(in.indexOf(":") + 1, in.indexOf('}'))));
                    }
                    System.out.println(s);

                }


            }

            return object;
        }

    }

    private int eatSpaces(String in) throws IOException {
        int val = 0;
        for (int i = 0; i < in.length(); i++) {
            val = i;
            if (in.charAt(i) != ' ') {
                return val;
            }


        }
        return val;
    }

    private int eatSpaces(int start, String in) throws IOException {
        int val = 0;
        for (int i = start; i < in.length(); i++) {
            val = i;
            if (in.charAt(i) != ' ') {
                return val;
            }


        }
        return val;
    }


    private String findKey(int start, String in) throws IOException {

        return matcher(key_regex, in.substring(start));
    }


    /**
     * set the previous and nextCommaPositions
     */

    JSON testJsonIfy(String in) throws IOException {
        return testDeepJSON(in, in.substring(frontBrace.dequeue()+1, backBrace.pop()));
    }


    public JSON testDeepJSON(String in, String parseObject) throws IOException {
        //first parse the object first
        JSON json = new MyJSON();


        GenQueue<Integer> commaQueue = new GenQueue<>(commas);


        //find and replace curly braces
        // because they've already been passed in recursive function

        if (in.contains("{") && !frontBrace.isEmpty() && !backBrace.isEmpty()) {
            int front = frontBrace.dequeue();
            int back = backBrace.pop();
            //split the string based on first square brace
            StringBuilder builder = new StringBuilder(in);
            builder.setCharAt(front, '[');
            builder.setCharAt(back, ']');
            in = builder.toString();

            //this may have multiple json objects so split by comma and set values
            String split[] = parseObject.split(",");

            for (int i = 0; i<split.length; i++) {
                String s = split[i];

                commaQueue.dequeue();
                if(!s.contains("{")) {

                    String key = matcher(key_regex, s);
                    String value = matcher(key_regex, s.substring(key.length()));
                    json.setString(key, value);
                } else {
                    String key = matcher(key_regex, s);
                    return json.setObject(key, testDeepJSON(in, in.substring(front+1, back)));


                }
            }


            return json;


        }
        for (String t : parseObject.split(",")) {
            parseObject = parseObject.substring(1, parseObject.length());

            String key = matcher(key_regex, t);
            if (key == null)
                throw new IOException("No key is present for " + t + " question");

            t = t.substring(key.length());
            String value = matcher(key_regex, t);
            if(value == null)
                throw new IOException("Value is empty for this key");
            json.setString(key, value);
        }
        return json;
    }


}


