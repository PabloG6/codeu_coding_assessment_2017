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

final class MyJSON implements JSON {

    protected MyJSON() {
    }

    String name;
    Object value;
    String val;
    MyJSON jsonValue;

    @Override
    public MyJSON getObject(String name) {
        // TODO: implement this
        return jsonValue;
    }

    @Override
    public MyJSON setObject(String name, JSON value) {
        this.name = name;
        this.jsonValue = (MyJSON) value;
        return this;
    }

    @Override
    public String getString(String name) {

        return val;
    }

    @Override
    public MyJSON setString(String name, String value) {
        // TODO: implement this
        this.name = name;
        this.val = value;
        return this;
    }

    @Override
    public void getObjects(Collection<String> names) {
        // TODO: implement this
        //// TODO: 4/14/2017 implement recursion that breaks when object being returned is null
    }

    @Override
    public void getStrings(Collection<String> names) {
        // TODO: implement this
        //// TODO: 4/14/2017 implement recursion that breaks out when object being returned will be null
    }
}
