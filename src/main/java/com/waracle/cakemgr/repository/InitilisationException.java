package com.waracle.cakemgr.repository;

/**
 * Exception thrown if there's an error initialising.
 */
public class InitilisationException extends Exception {
    /**
     * Creates a new instance.
     *
     * @param message The error message.
     */
    public InitilisationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     *
     * @param message The error message.
     * @param cause The underlying cause.
     */
    public InitilisationException(String message, Throwable cause) {
        super(message, cause);
    }
}
