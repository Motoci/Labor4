package com.ExceptionHandling;

public class ElementAlreadyExistsException extends Exception {

    /**
     * displays an error message in case the element already exists
     * @param _message to appear on the screen
     */
    public ElementAlreadyExistsException(String _message) {
        super(_message);
    }
}
