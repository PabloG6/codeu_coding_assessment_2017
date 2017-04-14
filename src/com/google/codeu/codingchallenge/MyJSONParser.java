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

final class MyJSONParser implements JSONParser {
    ArrayList<Boolean> positionOfBrackets = new ArrayList<>();
    private static final String key_regex = "(\"[\\w\\s ]+\")";

    @Override
    public JSON parse(String in) throws IOException {

        //do a check of the string that's been implemented by pushing value on stack
        if (isBalanced(in, positionOfBrackets)) {

            //find the first key value

            for (int i = 0; i < positionOfBrackets.size(); i++) {
                boolean isFrontCurly = positionOfBrackets.get(i);
                if (isFrontCurly) {
                    
                }

            }
            return new MyJSON();
        } else {

            throw new IOException("This is not a valid json object");
        }

    }

    /**
     * @param in                 string to validate
     * @param positionOfBrackets store the position of the brackets for quickeer look up
     * @return return boolean if expression is unbalanced
     */
    private static boolean isBalanced(String in, ArrayList<Boolean> positionOfBrackets) {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            char var = in.charAt(i);


            if (var == '{') {
                positionOfBrackets.add(true);
                brackets.push(var);
            } else if (var == '}') {
                positionOfBrackets.add(false);

                if (brackets.isEmpty()) return false;
                if (brackets.pop() != '{') return false;

            }
        }
        return brackets.isEmpty();
    }
}
