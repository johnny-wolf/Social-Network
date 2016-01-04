package org.jschropf.edu.pia.domain;

/**
 * Exception thrown when Post object fails internal state validation.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class PostValidationException extends Exception {

    public PostValidationException(String message) {
        super(message);
    }

}