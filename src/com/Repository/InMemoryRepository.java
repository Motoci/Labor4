package com.Repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements ICrudRepository<T> {

    protected List<T> repo;

    /**
     * set up the arraylist for each repository
     */
    public InMemoryRepository() {
        this.repo = new ArrayList<>();
    }

    /**
     * @return all the entities from the list
     */
    @Override
    public List<T> findAll() {
        if (this.repo.isEmpty())
            throw new IndexOutOfBoundsException("The List is empty.");

        return this.repo;
    }

}