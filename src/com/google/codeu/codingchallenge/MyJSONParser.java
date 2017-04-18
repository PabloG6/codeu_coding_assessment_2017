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

import java.io.IOException;
import java.util.ArrayList;
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

        if (match.find()) {
            return match.group().substring(1, match.group().length() - 1);
        }
        return null;
    }


    private MyJSON jsonify(String in) throws IOException {
        int start = eatSpaces(in);
        in = in.substring(start);



        //find first colon
        setColonPositions();
        setCommaPositions(nextComma);
        if (commas.size() != 0) {
            nextComma = commas.get(0);

            while (commaCount < commas.size()) {

                int val = eatSpaces(colonPosition + 1, in);
                setColonPositions();
                setCommaPositions(nextComma);
                if (in.substring(val).charAt(0) == '{') {
                    String key = matcher(key_regex, in.substring(val));
                    myJSON.setObject(key, deepJSON(in.substring(val, in.indexOf('}') + 1)));


                } else {
                    String key = matcher(key_regex, in);
                    if (key == null)
                        throw new IOException("Key is null");
                    in = in.substring(in.indexOf(key));

                    String object = matcher(key_regex, in);
                    if (object == null) {
                        throw new IOException("Object is null");
                    }

                    in = in.substring(in.indexOf(object));

                    myJSON.setString(key, object);


                }

            }
        }

        return myJSON;
    }

    private void setColonPositions() {
        if (colonCount < colons.size())
            colonPosition = colons.get(colonCount);
        colonCount++;

    }

    /**
     * find next json object in string
     *
     * @param in substring to check for json object
     * @return MyJSON object
     */

    private MyJSON deepJSON(String in) throws IOException {
        String key = findKey(0, in);
        Log.i("Deep JSON", in);
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
    private void setCommaPositions(int next) {
        commaCount++;
        this.preComma = next;
        if (commaCount < commas.size())
            this.nextComma = commas.get(commaCount);

    }

    //prints log statements for easier debugging
    public static class Log {
        public static void i(String key, String value) {
            System.out.printf("%s, %s \n", key, value);
        }

    }


}

