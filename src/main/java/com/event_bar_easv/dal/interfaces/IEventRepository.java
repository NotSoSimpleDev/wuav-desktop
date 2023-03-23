package com.event_bar_easv.dal.interfaces;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.SpecialTicketType;

import java.util.List;

public interface IEventRepository {
    List<Event> getAllEvents();

    int addSpecialTicketToEvent(SpecialTicketType specialTicketType, int eventId);

    int createSpecialTicket(SpecialTicketType specialTicketType);

    List<SpecialTicketType> getAllSpecialTickets();
}
