module com.event_bar_easv {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;
    requires org.mybatis;
    requires org.slf4j;
    requires feign.core;
    requires feign.gson;
    requires com.google.common;

    exports com.event_bar_easv.gui.controllers;
    exports com.event_bar_easv.bll.helpers;
    exports com.event_bar_easv.bll.services;
    exports com.event_bar_easv.bll.utilities;
    exports com.event_bar_easv to javafx.graphics;
    exports com.event_bar_easv.dal.dao;
    exports com.event_bar_easv.dal.interfaces;
    exports com.event_bar_easv.gui.models;
    exports com.event_bar_easv.config;
    exports com.event_bar_easv.di;
    exports com.event_bar_easv.gui.controllers.event;

    opens com.event_bar_easv.gui.controllers to javafx.fxml, com.google.guice, com.google.common;
    opens com.event_bar_easv to javafx.fxml, com.google.guice, org.slf4j;
    opens com.event_bar_easv.gui.controllers.abstractController to com.google.guice, javafx.fxml, com.google.common;
    opens com.event_bar_easv.gui.controllers.controllerFactory to com.google.guice, javafx.fxml, com.google.common;
    opens com.event_bar_easv.config to com.google.guice, javafx.fxml;
    opens com.event_bar_easv.di to com.google.guice, javafx.fxml;
    opens com.event_bar_easv.dal.myBatis to org.mybatis, javafx.fxml, org.slf4j;
    opens com.event_bar_easv.dal.mappers to org.mybatis;
    exports com.event_bar_easv.bll.services.interfaces;
}