package com.ExceptionHandling;

public class ListIsEmptyException extends Exception {

    /**
     * writes an error message in case the List is empty
     * @param _message to be displayed on the screen
     */
    public ListIsEmptyException(String _message) {
        super(_message);
    }
}
