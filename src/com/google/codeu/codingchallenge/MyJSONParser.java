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

            while (in.contains(":")) {


                setColonPositions();
                setCommaPositions(nextComma);
                if(!in.contains(":")) {
                  //do something
                }
                int val = eatSpaces(in.indexOf(":"), in);
                if (in.substring(val+1).charAt(1) == '{') {
                    String key = matcher(key_regex, in);
                    myJSON.setObject(key, deepJSON(key, in.substring(val+1, in.indexOf('}') + 1)));
in = in.substring(val+1);

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

                        in = in.substring(object.length()+3);
                         val = eatSpaces(0, in);
                        in = in.substring(val);
                        if(in.charAt(0) != ',' & in.charAt(0) !='}') {
                            System.out.println("final in " +in);
                            throw new IOException("object not separated by comma. Invalid json");
                        }

                        myJSON.setString(key, object);
                    } catch (StringIndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }

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

    private MyJSON deepJSON(String key, String in) throws IOException {
;
        MyJSON object = new MyJSON();
         if (in.charAt(in.indexOf(":")) == '{') {
             String key1 = findKey(0, in);
             in = in.substring(key.length()+3);
             Log.i("Deep JSON", in);
             return object.setObject(key1, deepJSON(key1, in.substring(in.indexOf(":") + 1, in.indexOf("}") + 1)));
         }


        else {
            //if the next object is not a { but a string create a json object

            //if the string in question contains commas, split it into an array of json objects
            if(in.contains(",")) {
                MyJSON json = new MyJSON();
               String[] key_value_pairs = in.split(",");
                for (int i = 0; i < key_value_pairs.length; i++) {
                    System.out.println(key_value_pairs[i]);
                    String key2 = matcher(key_regex, key_value_pairs[i]);
                    String value = matcher(key_regex, key_value_pairs[i].substring(key_value_pairs[i].indexOf(":")));
                    System.out.printf("%s %s \n", key, value);
                    if(myJSON!=null) {
                        object.setString(key2,  value);
                    }
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
    private void setCommaPositions(int next) {
        this.preComma = next;
        if (commaCount < commas.size())
            this.nextComma = commas.get(commaCount);
        commaCount++;

    }

    //prints log statements for easier debugging
    public static class Log {
        public static void i(String key, String value) {
            System.out.printf("%s, %s \n", key, value);
        }

    }


}

