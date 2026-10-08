package com.example.foobar;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Before
    public void doBefore() {
        System.out.println("This is before");
    }

    @After
    public void doAfter() {
        System.out.println("This is after");
    }

    @Test
    public void exampleTest() {
        System.out.println("This is the test");
        for (int i = 0; i < 1; i++) {
            assertEquals("Math is broken", 0, i);
        }
    }
}
