package com.Controller;

import java.io.IOException;
import java.util.List;
import com.ExceptionHandling.ElementAlreadyExistsException;
import com.ExceptionHandling.ListIsEmptyException;

public interface Controller<T> {

    /**
     * searches for an entity by ID
     * @param _id used for searching
     * @return the entity or null
     * @throws IOException for writing in file
     */
    T findOne(Long _id) throws IOException;


    /**
     * @return the list of elements of type T
     */
    List<T> findAll();


    /**
     * saves an entity to the list
     * @param _entity to be saved
     * @throws IOException for writing in file
     * @throws ElementAlreadyExistsException if entity already present in list
     */
    void save(T _entity) throws IOException, ElementAlreadyExistsException;


    /**
     * searches by ID and removes the found entity
     * @param _id used for searching
     * @throws IOException for writing in file
     * @throws ListIsEmptyException if the list is empty
     */
    void delete(Long _id) throws IOException, ListIsEmptyException;

    /**
     * reads from a file
     * @return the list of objects from the file
     * @throws IOException for writing in file
     */
    List<T> readFromFile() throws IOException;

    /**
     * writes to a file
     * @throws IOException for writing in file
     */
    void writeToFile() throws IOException;

}
