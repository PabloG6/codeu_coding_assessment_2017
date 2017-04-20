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

