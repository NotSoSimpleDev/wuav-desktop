package com.event_bar_easv.gui.models.event;

import com.event_bar_easv.be.Event;
import javafx.collections.ObservableList;

public interface IEventModel {
    ObservableList<Event> getAllEvents();
}
