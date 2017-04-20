////// Copyright 2017 Google Inc.
//////
////// Licensed under the Apache License, Version 2.0 (the "License");
////// you may not use this file except in compliance with the License.
////// You may obtain a copy of the License at
//////
//////    http://www.apache.org/licenses/LICENSE-2.0
//////
////// Unless required by applicable law or agreed to in writing, software
////// distributed under the License is distributed on an "AS IS" BASIS,
////// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
////// See the License for the specific language governing permissions and
////// limitations under the License.
////
////package com.google.codeu.codingchallenge;
////
////import java.util.Collection;
////import java.util.HashMap;
////import java.util.jar.Pack200;
////


package com.google.codeu.codingchallenge;

import java.util.Collection;
import java.util.HashMap;

final class MyJSON implements JSON {

    HashMap<String,String> stringObjects = new HashMap<String,String>();
    HashMap<String,JSON>   jsonObjects = new HashMap<String,JSON>();

    @Override
    public JSON getObject(String name) {

        // TODO: implement this
        if(jsonObjects.containsKey(name)){

            return jsonObjects.get(name);
        }

        return null;
    }

    @Override
    public JSON setObject(String name, JSON value) {
        // TODO: implement this
        if(jsonObjects.containsKey(name)){

            jsonObjects.put(name,value);
        } else{

            jsonObjects.put(name,value);
        }

        return this;
    }

    @Override
    public String getString(String name) {

        // TODO: implement this

        if(stringObjects.containsKey(name)){

            return stringObjects.get(name);
        }

        return null;
    }

    @Override
    public JSON setString(String name, String value) {
        // TODO: implement this
        if(stringObjects.containsKey(name)){

            stringObjects.put(name,value);
        } else{

            stringObjects.put(name,value);

        }

        return this;
    }

    @Override
    public void getObjects(Collection<String> names) {

        // TODO: implement this

        // names.copy(jsonObjects.keySet());
        names = jsonObjects.keySet();

    }

    @Override
    public void getStrings(Collection<String> names) {

        // TODO: implement this
        names = stringObjects.keySet();
    }
}

