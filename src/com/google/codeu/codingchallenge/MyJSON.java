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

import java.util.Collection;
import java.util.HashMap;
import java.util.jar.Pack200;

final class MyJSON implements JSON {
    private HashMap<String, String> stringObjects = new HashMap<>();
    private HashMap<String, MyJSON> jsonObjects = new HashMap<>();

    protected MyJSON() {
    }


    @Override
    public MyJSON getObject(String name) {
        // TODO: implement
        if (jsonObjects.containsKey(name)) {
            return jsonObjects.get(name);
        }


        return null;
    }

    @Override
    public MyJSON setObject(String name, JSON value) {

        jsonObjects.put(name, (MyJSON) value);

        return this;
    }

    @Override
    public String getString(String name) {
        if (stringObjects.containsKey(name)) {
            return stringObjects.get(name);
        }
        return null;
    }

    @Override
    public MyJSON setString(String name, String value) {
        // TODO: implement this
        stringObjects.put(name, value);

        return this;
    }

    @Override
    public void getObjects(Collection<String> names) {
        for (String name :
                jsonObjects.keySet()) {
            names.add(name);
        }

    }

    @Override
    public void getStrings(Collection<String> names) {
        // TODO: implement this
        //// TODO: 4/14/2017 implement recursion that breaks out when object being returned will be null
        for (String name :
                stringObjects.keySet()) {
            names.add(name);
        }
    }


}
