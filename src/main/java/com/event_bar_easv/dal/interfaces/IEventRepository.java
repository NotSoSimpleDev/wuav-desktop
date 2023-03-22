package com.event_bar_easv.dal.interfaces;

import com.event_bar_easv.be.Event;
import java.util.List;

public interface IEventRepository {
    List<Event> getAllEvents();
}
