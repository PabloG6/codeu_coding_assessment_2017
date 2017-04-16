package com.google.codeu.codingchallenge;

/**
 * Created by Pablo on 4/5/2017.
 */
public class NewTest {
    public static void main(String[] args) {
        final Tester tests = new Tester();
        //pass
        tests.add("Level 0 Json Test", new Test()
        {
            @Override
            public void run(JSONFactory factory) throws Exception {
                final MyJSONParser parser = new MyJSONParser();
                final MyJSON obj = parser.parse("{ \"name\":\"sam doe\" }");

               Asserts.isEqual("sam doe", obj.getString("name"));


            }
        });

        tests.run(new JSONFactory() {
            @Override
            public JSON object() {
                return null;
            }

            @Override
            public JSONParser parser() {
                return null;
            }
        });

    }
}
