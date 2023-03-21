package com.event_bar_easv.dal.mappers;

import com.event_bar_easv.be.AppUser;

import java.util.List;

public interface UserMapper {
    /**
     * Retrieves all Movies from the database and store into a list
     *
     * @return list of Movies
     */
    List<AppUser> getAllUsers();

}
