//// Copyright 2017 Google Inc.
////
//// Licensed under the Apache License, Version 2.0 (the "License");
//// you may not use this file except in compliance with the License.
//// You may obtain a copy of the License at
////
////    http://www.apache.org/licenses/LICENSE-2.0
////
//// Unless required by applicable law or agreed to in writing, software
//// distributed under the License is distributed on an "AS IS" BASIS,
//// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//// See the License for the specific language governing permissions and
//// limitations under the License.
//
//package com.google.codeu.codingchallenge;
//
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//final class MyJSONParser implements JSONParser {
//    private static final String key_regex = "(\"[\\w\\s ]+\")";
//    private JSON json = new MyJSON();
//    GenQueue frontBrace = new GenQueue();
//    Stack<Integer> backBrace = new Stack<>();
//
//    //keep position of commas and colons
//    GenQueue commas = new GenQueue();
//
//    @Override
//    public JSON parse(String in) throws IOException {
//
//        //create boolean array to store position of curly braces.
//        //true means "{", false means "}"
//        if (isBalanced(in)) {
//
//            json = testJsonIfy(in);
//
//            return json;
//        } else {
//
//            throw new IOException("Invalid number of curly braces");
//        }
//    }
//
//    /**
//     * @param in string to validate
//     * @return return false if expression is unbalanced
//     */
//
//
//    private boolean isBalanced(String in) throws IOException {
//        Stack<Character> brackets = new Stack<>();
//        for (int i = 0; i < in.length(); i++) {
//            char var = in.charAt(i);
//            if (var == ':') {
//
//            }
//
//
//            if (var == ',') {
//                commas.enqueue(i);
//            }
//            if (var == '{') {
//                brackets.push(var);
//                frontBrace.enqueue(i);
//
//            } else if (var == '}') {
//
//
//                if (brackets.isEmpty()) return false;
//                if (brackets.pop() != '{') return false;
//                backBrace.push(i);
//
//            }
//        }
//
//        //if commas size less than colons size by more than one, return false;
//
//        return brackets.isEmpty();
//    }
//
//    private static String matcher(String key, String in) throws IOException {
//        Pattern pattern = Pattern.compile(key);
//        Matcher match = pattern.matcher(in);
//
//        if (match.find()) {
//            return match.group().substring(1, match.group().length() - 1);
//        }
//        return null;
//    }
//
//
//
//
//    /**
//     * find next json object in string
//     *
//     * @param in substring to check for json object
//     * @return MyJSON object
//     */
//
//    /**
//     * set the previous and nextCommaPositions
//     */
//
//    JSON testJsonIfy(String in) throws IOException {
//        return testDeepJSON(in, in);
//    }
//
//
//    public JSON testDeepJSON(String in, String parseObject) throws IOException {
//        //first parse the object first
//        JSON json = new MyJSON();
//
//
//        GenQueue commaQueue = new GenQueue(commas);
//
//
//        //find and replace curly braces
//        // because they've already been passed in recursive function
//
//        if (in.contains("{") && !frontBrace.isEmpty() && !backBrace.isEmpty()) {
//            int front;
//            int back;
//            Matcher bBrace = backBracePatternMatcher(in.substring(front));
//            if(bBrace!=null) {
//                //find the key to the left of the front brace
//
//                String key = matcher(key_regex, parseObject);
//                Object remove = bBrace.start()+front;
//                back = (int) remove;
//
//                backBrace.remove(remove);
//                StringBuilder builder = new StringBuilder(in);
//                builder.setCharAt(front, '[');
//                builder.setCharAt(back, ']');
//                in = builder.toString();
//
//                json.setObject(key, testDeepJSON(in, in.substring(front, bBrace.end()+front)));
//
//            } else {
//                 back = backBrace.pop();
//                 front = frontBrace.dequeue();
//                StringBuilder builder = new StringBuilder(in);
//                builder.setCharAt(front, '[');
//                builder.setCharAt(back, ']');
//                in = builder.toString();
//
//            }
//            //do this to prevent stringoutofboundsexception
//
//            /*if the value after front at any point in time is a back curly brace followed by a comma, then search for t
//              the last value in the back curly brace that comes after a comma and pass it again
//             */
//            StringBuilder builder = new StringBuilder(in);
//            builder.setCharAt(front, '[');
//            builder.setCharAt(back, ']');
//            in = builder.toString();
//
//
//            //split the string based on first square brace
//          ;
//
//            //this may have multiple json objects so split by comma and set values
//            String split[] = parseObject.split(",");
//
//            for (int i = 0; i<split.length; i++) {
//                String s = split[i];
//
//                commaQueue.dequeue();
//                if(!s.contains("{")) {
//
//                    String key = matcher(key_regex, s);
//                    if(key == null) throw new IOException("The key for the value is null");
//                    String value = matcher(key_regex, s.substring(key.length()));
//                    json.setString(key, value);
//                } else {
//                    String key = matcher(key_regex, s);
//                    return json.setObject(key, testDeepJSON(in, in.substring(front+1, back)));
//
//
//                }
//            }
//
//            return json;
//
//
//        }
//        for (String t : parseObject.split(",")) {
//            parseObject = parseObject.substring(1, parseObject.length());
//
//            String key = matcher(key_regex, t);
//            if (key == null)
//                throw new IOException("No key is present for " + t + " question");
//
//            t = t.substring(key.length());
//            String value = matcher(key_regex, t);
//            if(value == null)
//                throw new IOException("Value is empty for this key");
//            json.setString(key, value);
//        }
//
//
//        return json;
//    }
//
//    private Matcher backBracePatternMatcher(String in) {
//        String back_brace_end = "((}[\\s]+,)|(},))";
//        Pattern pattern = Pattern.compile(back_brace_end);
//        Matcher match = pattern.matcher(in);
//        if(match.find()) {
//            return match;
//        }
//        return null;
//    }
//
//
//}
//
//

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

final class MyJSONParser implements JSONParser {

    @Override
    public JSON parse(String in) throws IOException {
        // TODO: implement this
        System.out.println();
        return handle(in, new MyJSON());
        //return new MyJSON();
    }

    enum PARSE_STATE {

        INITIAL, KEYFIND, SPECIALCHARACTER, VALUEFIND, STRINGVALUE, JSONVALUE, COMMA
    }

    public JSON handle(String in, JSON json) throws IOException{


        PARSE_STATE state = PARSE_STATE.INITIAL;
        PARSE_STATE next_state = PARSE_STATE.INITIAL;
        PARSE_STATE previous_state = PARSE_STATE.INITIAL;
        String key = "";
        String value = "";
        int bracket_balance = 0;

        for (int i = 0; i < in.length(); ++i) {

            System.out.print(in.charAt(i));
            switch (state) {

                case INITIAL:
                    if(previous_state == PARSE_STATE.STRINGVALUE) {
                        if(in.charAt(' ')==' ') break;
                    if(in.charAt(i)!=',' && in.charAt(i)!='}') {
                        throw new IOException("Not a json object");
                    }
                    }
                    if (in.charAt(i) == '\"') {
                        previous_state = PARSE_STATE.KEYFIND;
                        state = PARSE_STATE.KEYFIND;
                    }

                    break;

                case KEYFIND:

                    if (in.charAt(i) == '\\') {

                        state = PARSE_STATE.SPECIALCHARACTER;
                        next_state = PARSE_STATE.KEYFIND;

                    } else if (in.charAt(i) == '\"') {

                        //ignore

                    } else if (in.charAt(i) == ':') {

                        state = PARSE_STATE.VALUEFIND;
                    } else {


                        key += in.charAt(i);
                    }


                    break;

                case SPECIALCHARACTER:
                    // READ IN SPECIAL CHARACTER TO KEY

                    key += in.charAt(i);
                    state = next_state;

                    break;

                case VALUEFIND:

                    if (in.charAt(i) == '\"') {
                        state = PARSE_STATE.STRINGVALUE;
                    } else if (in.charAt(i) == '{') {

                        state = PARSE_STATE.JSONVALUE;

                    }

                    break;

                case STRINGVALUE:
                    previous_state = PARSE_STATE.STRINGVALUE;
                    if (in.charAt(i) == '\\') {

                        state = PARSE_STATE.SPECIALCHARACTER;
                        next_state = PARSE_STATE.STRINGVALUE;
                    } else if (in.charAt(i) == '\"') {

                        //STRING TERMINATED, SET JSON VALUE
                        json.setString(key, value);

                        key = "";
                        value = "";
                        state = PARSE_STATE.INITIAL;

                    } else {

                        value += in.charAt(i);
                    }

                    break;

                case JSONVALUE:

                    if (in.charAt(i) == '\\') {

                        state = PARSE_STATE.SPECIALCHARACTER;
                        next_state = PARSE_STATE.JSONVALUE;
                    } else if (in.charAt(i) == '}' && bracket_balance == 0) {

                        //STRING TERMINATED, SET JSON VALUE
                        json.setObject(key, handle(value, new MyJSON()));
                        key = "";
                        value = "";
                        state = PARSE_STATE.INITIAL;

                    } else {

                        if (in.charAt(i) == '{') {

                            bracket_balance += 1;
                        } else if (in.charAt(i) == '}') {

                            bracket_balance -= 1;
                        }

                        value += in.charAt(i);
                    }


                    break;

            }


        }

        return json;
    }
}

