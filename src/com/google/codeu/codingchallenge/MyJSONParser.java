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
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MyJSONParser implements JSONParser {
    boolean[] positionOfBrackets;
    private static final String key_regex = "(\"[\\w\\s ]+\")";
    private MyJSON myJSON = new MyJSON();

    @Override
    public MyJSON parse(String in) throws IOException {

        //create boolean array to store position of curly braces.
        //true means "{", false means "}"
        positionOfBrackets = new boolean[in.length()];
        //do a check of the string that's been implemented by pushing value on stack

        if (isBalanced(in, positionOfBrackets)) {
            //start parsing

            //get smallest subObject

            //createDictionary method
            for (int front = 0, back = positionOfBrackets.length - 1;
                 front < positionOfBrackets.length && back >= 0; front++, back--) {
                if (positionOfBrackets[front] && !positionOfBrackets[back]) {
                    String val = in.substring(front + 1, back);
                    System.out.println(val);
                    String[] values = val.split(":");

                    String key = matcher(key_regex, values[0]);
                    String value = matcher(key_regex, values[1]);

                    if(key==null && value==null) {
                        throw new IOException("This is not a valid json object");
                    }
                    System.out.println("Key value: " + key);
                    System.out.println("Object value: " + value);
                    myJSON.setString(key, value);
                }
            }
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
    private boolean isBalanced(String in, boolean[] positionOfBrackets) throws IOException {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            char var = in.charAt(i);
            if (var == '{') {
                brackets.push(var);
                positionOfBrackets[i] = true;
            } else if (var == '}') {

                positionOfBrackets[i] = false;
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

}
