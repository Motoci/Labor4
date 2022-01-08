package com.Repository;

import java.io.IOException;
import java.util.List;

public interface FileRepository<T> extends ICrudRepository<T> {

    /**
     *
     * @param _id ID of the object we are searching for
     * @return the object or null
     * @throws IOException for writing in file
     */
    T findOne(Long _id) throws IOException;

    /**
     * reads a list of objects from a file
     * @return the text of the file as a List container
     */
    List<T> readFromFile() throws IOException;

    /**
     * writes objects to a file
     */
    void writeToFile() throws IOException;

}
