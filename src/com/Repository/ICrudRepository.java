package com.Repository;

import java.util.List;
import java.io.IOException;
import com.ExceptionHandling.ElementAlreadyExistsException;

public interface ICrudRepository<T> {

    /**
     * returns the entire RepositoryList
     * @return a list of elements of type T
     */
    List<T> findAll();

    /**
     * Stores the given entity
     * @param _entity to be stored in the appropriate container
     */
    void save(T _entity) throws IOException, ElementAlreadyExistsException;

    /**
     * deletes the entity from the List
     * @param _id to be deleted
     */
    void delete(Long _id) throws IOException, IllegalArgumentException;

}