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

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

final class Tester {
    public static class TestMapper {
        Test test;
        String testValue;

        public Test getTest() {
            return test;
        }

        public void setTest(Test test) {
            this.test = test;
        }

        public String getTestValue() {
            return testValue;
        }

        public void setTestValue(String testValue) {
            this.testValue = testValue;
        }

        public TestMapper(Test test, String testValue) {
            this.test = test;
            this.testValue = testValue;
        }


    }

    private final Map<String, Test> tests = new HashMap<>();
    private final ArrayList<TestMapper> testMapperArrayList = new ArrayList<>();

    public void add(String name, Test test) {
        tests.put(name, test);
        testMapperArrayList.add(new TestMapper(test, name));
    }

    public void run(JSONFactory factory) {
        for (final Map.Entry<String, Test> test : tests.entrySet()) {

            try {
                test.getValue().run(factory);
                System.out.println();
                System.out.format("PASS : Test %s\n", test.getKey());
            } catch (Exception ex) {
                System.out.println();
                System.out.format("FAIL : Test %s (%s)\n", test.getKey(), ex.toString());
            }
        }
//        System.out.println("Array List ordering");
//        for (TestMapper mapper : testMapperArrayList) {
//            System.out.format("PASS : Test %s\n", mapper.getTestValue());
//            try {
//                mapper.getTest().run(factory);
//            } catch (Exception ex) {
//                System.out.format("FAIL : Test %s (%s)\n", mapper.getTestValue(), ex.toString());
//             ex.printStackTrace();
//            }
//        }
    }
}
