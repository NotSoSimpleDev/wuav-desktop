package com.event_bar_easv.dal.reporitory;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.user.AppUser;
import com.event_bar_easv.dal.interfaces.IEventRepository;
import com.event_bar_easv.dal.mappers.EventMapper;
import com.event_bar_easv.dal.mappers.UserMapper;
import com.event_bar_easv.dal.myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EventRepository implements IEventRepository {
    static Logger logger = LoggerFactory.getLogger(UserRepository.class);

//    public static void main(String[] args) {
//        var test =  getTestEvent();
//        System.out.println(test);
//    }
    @Override
    public List<Event> getAllEvents() {
        List<Event> fetchedEvents = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            EventMapper mapper = session.getMapper(EventMapper.class);
            fetchedEvents = mapper.getAllEvents();
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        System.out.println(fetchedEvents);
        return fetchedEvents;
    }

//    private static List<Event> getTestEvent(){
//        List<Event> fetchedEvents = new ArrayList<>();
//        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
//            EventMapper mapper = session.getMapper(EventMapper.class);
//            fetchedEvents = mapper.getAllEvents();
//        } catch (Exception ex) {
//            logger.error("An error occurred mapping tables", ex);
//        }
//        System.out.println(fetchedEvents);
//        return fetchedEvents;
//    }
}
