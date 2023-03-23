package com.event_bar_easv.dal.mappers;
import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.SpecialTicketType;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface EventMapper {

    List<Event> getAllEvents();

    List<SpecialTicketType> getAllSpecialTickets();

    int createSpecialTicketType(SpecialTicketType ticketType);
    int addSpecialTicketToEvent(@Param("eventId") int eventId, @Param("ticketId") int ticketId);
}