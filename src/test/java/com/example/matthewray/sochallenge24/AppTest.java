package com.example.matthewray.sochallenge24;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for entire App.
 */
public class AppTest {
    private ByteArrayOutputStream capturedOutputStream;
    private PrintStream capturedPrintStream;
    private PrintStream originalSystemOut;

    @Before
    public void redirectOutput() {
        originalSystemOut = System.out;
        capturedOutputStream = new ByteArrayOutputStream();
        capturedPrintStream = new PrintStream(capturedOutputStream);
        System.setOut(capturedPrintStream);
    }

    @After
    public void unRedirectOutput() {
        System.setOut(originalSystemOut);
        if (capturedPrintStream != null) {
            capturedPrintStream.close();
        }
        try {
            if (capturedOutputStream != null) {
                capturedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        capturedPrintStream = null;
        capturedOutputStream = null;
    }

    /**
     * The expected output has been manually validated. All point values assigned to
     * hands appear to be correct, and they have been totaled correctly.
     */
    @Test
    public void runAppOnFullData() {
        List<String> expectedOutput = List.of(
                "Hand 1: 2 points",
                "Hand 2: 1 points",
                "Hand 3: 1 points",
                "Hand 4: 8 points",
                "Hand 5: 512 points",
                "Hand 6: 8 points",
                "Hand 7: 1 points",
                "Hand 8: 2 points",
                "Hand 9: 4 points",
                "Hand 10: 2 points",
                "Hand 11: 16 points",
                "Hand 12: 4 points",
                "Hand 13: 4 points",
                "Hand 14: 2 points",
                "Hand 15: 4 points",
                "Hand 16: 2 points",
                "Hand 17: 1 points",
                "Hand 18: 8 points",
                "Hand 19: 8 points",
                "Hand 20: 2 points",
                "Hand 21: 16 points",
                "Hand 22: 1 points",
                "Hand 23: 8 points",
                "Hand 24: 16 points",
                "Hand 25: 2 points",
                "Hand 26: 1 points",
                "Hand 27: 4 points",
                "Hand 28: 2 points",
                "Hand 29: 2 points",
                "Hand 30: 1 points",
                "Hand 31: 2 points",
                "Hand 32: 2 points",
                "Hand 33: 1 points",
                "Hand 34: 256 points",
                "Hand 35: 2 points",
                "Hand 36: 32 points",
                "Hand 37: 4 points",
                "Hand 38: 8 points",
                "Hand 39: 1 points",
                "Hand 40: 512 points",
                "Hand 41: 2 points",
                "Hand 42: 256 points",
                "Hand 43: 1 points",
                "Hand 44: 2 points",
                "Hand 45: 16 points",
                "Hand 46: 2 points",
                "Hand 47: 1 points",
                "Hand 48: 128 points",
                "Hand 49: 2 points",
                "Hand 50: 8 points",
                "Hand 51: 1 points",
                "Hand 52: 2 points",
                "Hand 53: 4 points",
                "Hand 54: 8 points",
                "Hand 55: 256 points",
                "Hand 56: 64 points",
                "Hand 57: 32 points",
                "Hand 58: 4 points",
                "Hand 59: 2 points",
                "Hand 60: 8 points",
                "Hand 61: 16 points",
                "Hand 62: 1 points",
                "Hand 63: 16 points",
                "Hand 64: 2 points",
                "Hand 65: 2 points",
                "Hand 66: 8 points",
                "Hand 67: 256 points",
                "Hand 68: 2 points",
                "Hand 69: 32 points",
                "Hand 70: 32 points",
                "Hand 71: 2 points",
                "Hand 72: 64 points",
                "Hand 73: 512 points",
                "Hand 74: 2 points",
                "Hand 75: 2 points",
                "Hand 76: 2 points",
                "Hand 77: 2 points",
                "Hand 78: 2 points",
                "Hand 79: 1 points",
                "Hand 80: 16 points",
                "Hand 81: 4 points",
                "Hand 82: 8 points",
                "TOTAL: 3248 points");
        final String inputFileName = "poker_hands.json";
        final String inputFilePath = this.getClass().getClassLoader().getResource(inputFileName).getPath();
        App.main(new String[] { inputFilePath });
        String[] actualOutput = getOutputLines();
        unRedirectOutput();

        assertEquals(expectedOutput.size(), actualOutput.length);
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals("Error at i == " + i, expectedOutput.get(i), actualOutput[i]);
        }
    }

    @Ignore
    @Test
    public void dummyTestToSeeOutput() {
        unRedirectOutput();
        final String inputFileName = "poker_hands.json";
        final String inputFilePath = this.getClass().getClassLoader().getResource(inputFileName).getPath();
        App.main(new String[] { inputFilePath });
    }

    private String[] getOutputLines() {
        capturedPrintStream.flush();
        final String fullOutput = capturedOutputStream.toString();
        return fullOutput.split("\r?\n");
    }
}
