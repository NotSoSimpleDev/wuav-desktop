package com.event_bar_easv.bll.services;

import com.event_bar_easv.be.AppUser;
import com.event_bar_easv.dal.interfaces.IUserRepository;
import com.event_bar_easv.dal.reporitory.UserRepository;
import com.google.inject.Inject;

import java.util.List;

public class UserService {

//    private static final IUserRepository userRepository;

    public static void main(String[] args) {
        System.out.println(getAllUsers());
    }

//    @Inject
//    public UserService(IUserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    private static List<AppUser> getAllUsers() {
        IUserRepository userRepository = new UserRepository();
        return userRepository.getAllUsers();
    }


}
