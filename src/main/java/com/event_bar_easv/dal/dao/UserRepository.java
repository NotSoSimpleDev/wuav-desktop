package com.event_bar_easv.dal.dao;


import com.event_bar_easv.dal.interfaces.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserRepository implements IUserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public List<String> getAllUsers() {
       return List.of();
    }
}