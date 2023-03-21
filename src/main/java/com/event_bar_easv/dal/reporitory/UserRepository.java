package com.event_bar_easv.dal.reporitory;


import com.event_bar_easv.be.AppUser;
import com.event_bar_easv.dal.interfaces.IUserRepository;
import com.event_bar_easv.dal.mappers.UserMapper;
import com.event_bar_easv.dal.myBatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public List<AppUser> getAllUsers() {
        List<AppUser> allUsers = new ArrayList<>();
        try (SqlSession session = MyBatisConnectionFactory.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            allUsers = mapper.getAllUsers();
        } catch (Exception ex) {
            logger.error("An error occurred mapping tables", ex);
        }
        return allUsers;
    }
}