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
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MyJSONParser implements JSONParser {
    private static final String key_regex = "(\"[\\w\\s ]+\")";
    private MyJSON myJSON = new MyJSON();
    ArrayList<Integer> bracketPositions = new ArrayList<>();
    private int preComma = 1;
    private int nextComma;
    int commaCount;

    //keep position of commas and colons
    ArrayList<Integer> commas = new ArrayList<>();
    ArrayList<Integer> colons = new ArrayList<>();
    private int colonPosition = 0;
    private int colonCount = 0;

    @Override
    public MyJSON parse(String in) throws IOException {

        //create boolean array to store position of curly braces.
        //true means "{", false means "}"
        if (isBalanced(in, bracketPositions)) {
            jsonify(in);
            return myJSON;
        } else {

            throw new IOException("This is not a valid json object");
        }

    }

    /**
     * @param in                 string to validate
     * @param positionOfBrackets store the position of the brackets for quickeer look up
     * @return return boolean if expression is unbalanced
     */


    private boolean isBalanced(String in, ArrayList positionOfBrackets) throws IOException {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            char var = in.charAt(i);
            if (var == ':') {
                colons.add(i);
            }

            if (var == ',') {
                commas.add(i);
            }
            if (var == '{') {
                brackets.push(var);

            } else if (var == '}') {


                if (brackets.isEmpty()) return false;
                if (brackets.pop() != '{') return false;


            }
        }

        //if commas size less than colons size by more than one, return false;

        return brackets.isEmpty();
    }

    private static String matcher(String key, String in) throws IOException {
        Pattern pattern = Pattern.compile(key);
        Matcher match = pattern.matcher(in);
        System.out.println(in);
        if (match.find()) {
            return match.group().substring(1, match.group().length() - 1);
        }
        return null;
    }


    private MyJSON jsonify(String in) throws IOException {
        int start = eatSpaces(in);
        in = in.substring(start);


        //now get to the deepest level of json


        //find first colon
        setColonPositions();
        String key = matcher(key_regex, in);
        String value = null;
        //if a string value is not presetn but no colon the json object is invalid.
        if (key == null && in.contains(":")) {
            throw new IOException("This is not a valid json object");
        }


        //if it's not a json then do the other thing
        nextComma = commas.get(0);
        while (commaCount < commas.size()) {

            if (in.charAt(colonPosition + 1) == '{') {
                myJSON.setObject(key, deepJSON(in.substring(colonPosition)));
            } else {

                value = matcher(key_regex, in.substring(preComma+1, nextComma).substring(in.indexOf(":")));
                setColonPositions();
                setCommaPositions(nextComma);
                myJSON.setString(key, value);
            }

        }
        return myJSON;
    }

    private void setColonPositions() {


        colonPosition = colons.get(colonCount+1);
    }

    /**
     * find next json object in string
     *
     * @param in substring to check for json object
     * @return MyJSON object
     */

    private MyJSON deepJSON(String in) throws IOException {
        String key = findKey(0, in);
        MyJSON object = new MyJSON();
        if (key == null) {
            throw new IOException("This is not a valid json object");

        } else if (in.charAt(in.indexOf(":")) == '{')
            return object.setObject(key, deepJSON(in.substring(in.indexOf(":") + 1)));

        else {
            //if the next object is not a { but a string create a json object
            String value = matcher(key, in);
            if (value == null) {
                throw new IOException("Not a valid json object");
            }
            return object.setString(key, value);
        }
    }

    private String findObject(int start, String in) throws IOException {
        return matcher(key_regex, in.substring(start));
    }

    private String jsonify(int start, String in, int num_of_commas) throws IOException {
        if (in.charAt(start + 1) == '{') {
            System.out.println(in);
            return jsonify(start, in.substring(start + 1), num_of_commas + 1);
        }
        jsonify(in);
        return null;
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


    private String findKey(int start, String in) throws IOException {

        return matcher(key_regex, in.substring(start));
    }


    //simple validation check to see if a key contains a value;
    private void simpleValid(String in) throws IOException {

        String key = matcher(key_regex, in);

        //if a string value is not present but no colon the json object is invalid.
        if (key == null && !in.contains(":")) {
            throw new IOException("This is not a valid json object");
        }


    }

    /**
     * set the previous and nextCommaPositions
     */
    private void setCommaPositions(int next) {

        this.preComma = next;
        this.nextComma = commas.get(commaCount+1);
        commaCount++;
    }

    //prints log statements for easier debugging
    public static class Log {
        public static void i(String key, String value) {
            System.out.printf("%s , %s \n", key, value);
        }

    }


}

