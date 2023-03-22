package com.event_bar_easv.bll.services;

import com.event_bar_easv.be.AppUser;
import com.event_bar_easv.bll.services.interfaces.IUserService;
import com.event_bar_easv.dal.interfaces.IUserRepository;
import com.google.inject.Inject;

import java.util.List;

public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Inject
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
