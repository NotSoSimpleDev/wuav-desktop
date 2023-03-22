package com.event_bar_easv.bll.services;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.bll.services.interfaces.IEventService;
import com.event_bar_easv.dal.interfaces.IEventRepository;
import com.google.inject.Inject;

import java.util.List;

public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    @Inject
    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }
}
