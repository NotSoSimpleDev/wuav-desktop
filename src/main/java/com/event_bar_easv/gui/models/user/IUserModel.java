package com.event_bar_easv.gui.models.user;

import com.event_bar_easv.be.AppUser;
import javafx.collections.ObservableList;

public interface IUserModel {

    ObservableList<AppUser> getAllUsers();


    AppUser createUser(AppUser movie);
}
