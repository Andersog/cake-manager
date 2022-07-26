package com.waracle.cakemgr;

import com.waracle.cakemgr.repository.Cake;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Test utilities
 */
public class CakeTestUtils {

    /**
     * Initialisation utilities.
     */
    public static class Initialisation {

        /**
         * The first cake in our init.json file.
         */
        public static final Cake CAKE_A;

        /**
         *  The second cake in our init.json file.
         */
        public static final Cake CAKE_B;

        static {
            try {
                CAKE_A = new Cake("A", "Cake A", new URL("http://cake-a.com"));
                CAKE_B = new Cake("B", "Cake B", new URL("http://cake-b.com"));
            } catch (MalformedURLException ex) {
                throw new IllegalStateException("Test utils are setup incorrectly - please fix this", ex);
            }
        }
    }
}
