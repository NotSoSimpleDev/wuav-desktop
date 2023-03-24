package com.event_bar_easv.bll.services;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.SpecialTicketType;
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


    @Override
    public List<SpecialTicketType> getAllSpecialTickets() {
        return eventRepository.getAllSpecialTickets();
    }

    @Override
    public int addSpecialTicketToAllEvent(SpecialTicketType specialTicketType, List<Integer> collectedIds) {
        int finalResult = 0;
        for (Integer id : collectedIds) {
            finalResult =  eventRepository.addSpecialTicketToEvent(specialTicketType, id);
        }
        return finalResult;
    }

    @Override
    public int addSpecialTicketToEvent(SpecialTicketType specialTicketType, int eventId) {
        return eventRepository.addSpecialTicketToEvent(specialTicketType, eventId);
    }

    @Override
    public int createSpecialTicket(SpecialTicketType specialTicketType) {
        return eventRepository.createSpecialTicket(specialTicketType);
    }


}
