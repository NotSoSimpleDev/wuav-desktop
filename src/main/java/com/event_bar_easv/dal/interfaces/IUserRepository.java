package com.event_bar_easv.dal.interfaces;

import java.util.List;

public interface IUserRepository {

    /**
     * Retrieves all Categories from the database and store into a list
     *
     * @return a list of all the Categories from database
     */
    List<String> getAllUsers();
}