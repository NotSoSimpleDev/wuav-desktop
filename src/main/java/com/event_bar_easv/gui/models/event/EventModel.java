package com.event_bar_easv.gui.models.event;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.user.AppUser;
import com.event_bar_easv.bll.services.interfaces.IEventService;
import com.event_bar_easv.bll.services.interfaces.IUserService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel implements IEventModel {
    private final IEventService eventService;

    private ObservableList<Event> allEventsObservableList;

    @Inject
    public EventModel(IEventService eventService) {
        this.eventService = eventService;
        this.allEventsObservableList = getAllEvents();
        System.out.println("EventModel: " + allEventsObservableList);
    }

    @Override
    public ObservableList<Event> getAllEvents() {
        allEventsObservableList = FXCollections.observableArrayList(eventService.getAllEvents());
        return allEventsObservableList;
    }

    @Override
    public Event getEventById(int id) {
        return allEventsObservableList.stream()
                .filter(event -> event.getEventId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Event getEventByName(String eventName) {
        return allEventsObservableList.stream()
                .filter(event -> event.getEventName().equals(eventName))
                .findFirst()
                .orElse(null);
    }


}
