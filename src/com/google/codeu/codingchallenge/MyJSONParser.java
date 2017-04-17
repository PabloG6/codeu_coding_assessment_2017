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

import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MyJSONParser implements JSONParser {
    ArrayList<BracketPositions> positionOfBrackets;
    private static final String key_regex = "(\"[\\w\\s ]+\")";
    private MyJSON myJSON = new MyJSON();
    

    @Override
    public MyJSON parse(String in) throws IOException {

        //create boolean array to store position of curly braces.
        //true means "{", false means "}"
        positionOfBrackets = new ArrayList<>();
        //do a check of the string that's been implemented by pushing value on stack

        if (isBalanced(in, positionOfBrackets)) {
            //start parsing


            //createDictionary method
            createDictionary(in);


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
    private boolean isBalanced(String in, ArrayList<BracketPositions> positionOfBrackets) throws IOException {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            char var = in.charAt(i);
            if (var == '{') {
                brackets.push(var);
                positionOfBrackets.add(new BracketPositions(i, true));
            } else if (var == '}') {

                positionOfBrackets.add(new BracketPositions(i, false));
                if (brackets.isEmpty()) return false;
                if (brackets.pop() != '{') {
                    return false;
                }


            }
        }
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

    /**
     * @return subObject to parse
     */
    private String subObject(int start, String in) throws IOException {
        if (start != -1 && in.charAt(start + 1) == '{') {
            parse(in.substring(start + 1, in.length() - 1));

        }
        return in;
    }

    /**
     * @param in JSON Object
     * @throws IOException
     */
    private void createDictionary(String in) throws IOException {
        //count from middle out to get deepest json object

        int right = positionOfBrackets.size() / 2;
        int left;
        left = positionOfBrackets.size() / 2 - 1;

        for (; left >= 0 && right < positionOfBrackets.size(); left--, right++) {
            int left_pos = positionOfBrackets.get(left).position;
            int right_pos = positionOfBrackets.get(right).position;
            if (positionOfBrackets.get(left).whichBracket && !positionOfBrackets.get(right).whichBracket) {
                String val = in.substring(left_pos + 1, right_pos);
                System.out.println(val);
                String[] values = val.split(",");

                String key = null;
                String value = null;
                for (int i = 0; i < values.length; i++) {

                    //get individual dictionary object from values split based on ,
                    String dictionary = values[i];
                    key = matcher(key_regex, dictionary.split(":")[0]);
                    value = matcher(key_regex, dictionary.split(":")[1]);
                }

                if (key == null && value == null) {
                    throw new IOException("This is not a valid json object");
                }

                //// TODO: 4/16/2017 delete this
                System.out.println("Key value: " + key);
                System.out.println("Object value: " + value);
                myJSON.setString(key, value);
            }
        }
    }

    /**
     * store the type of bracket and the bracket position in this custom object for easy lookup
     */

    private final static class BracketPositions {
        int position;
        boolean whichBracket;

        public BracketPositions(int position, boolean whichBracket) {
            this.position = position;
            this.whichBracket = whichBracket;
        }
    }
}
