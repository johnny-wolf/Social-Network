package org.jschropf.edu.pia.domain;

/**
 * Exception thrown when Post object fails internal state validation.
 *
 * Date: 26.11.15
 *
 * @author Jan Schropfer
 */
public class PostValidationException extends Exception {

    public PostValidationException(String message) {
        super(message);
    }

}