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

    exports com.event_bar_easv.gui.controllers to  com.google.guice, javafx.fxml, com.google.common;
    exports com.event_bar_easv.bll.helpers;
    exports com.event_bar_easv.bll.services;
    exports com.event_bar_easv.bll.utilities;
    exports com.event_bar_easv to javafx.graphics;
    exports com.event_bar_easv.dal.reporitory;
    exports com.event_bar_easv.dal.interfaces;
    exports com.event_bar_easv.gui.models to javafx.graphics, org.mybatis, com.google.guice;
    exports com.event_bar_easv.config;
    exports com.event_bar_easv.be to javafx.graphics, org.mybatis;
    exports com.event_bar_easv.di;
    exports com.event_bar_easv.gui.controllers.event;
    exports com.event_bar_easv.gui.controllers.controllerFactory;
    exports com.event_bar_easv.bll.services.interfaces to com.google.guice;
    exports com.event_bar_easv.gui.models.user to com.google.guice;

    opens com.event_bar_easv.gui.controllers to javafx.fxml, com.google.guice, com.google.common;
    opens com.event_bar_easv to javafx.fxml, com.google.guice, org.slf4j;
    opens com.event_bar_easv.gui.controllers.abstractController to com.google.guice, javafx.fxml, com.google.common;
    opens com.event_bar_easv.gui.controllers.controllerFactory to com.google.guice, javafx.fxml, com.google.common;
    opens com.event_bar_easv.config to com.google.guice, javafx.fxml;
    opens com.event_bar_easv.di to com.google.guice, javafx.fxml;
    opens com.event_bar_easv.dal.myBatis to org.mybatis, javafx.fxml, org.slf4j;
    opens com.event_bar_easv.dal.mappers to org.mybatis, com.google.guice;
    opens com.event_bar_easv.be to org.mybatis, javafx.fxml, org.slf4j, com.google.guice;
    opens com.event_bar_easv.bll.services to org.mybatis, javafx.fxml, org.slf4j, com.google.guice;
    opens com.event_bar_easv.bll.services.interfaces to  javafx.fxml, com.google.guice, com.google.common, org.slf4j;
    opens com.event_bar_easv.gui.models.user to javafx.fxml, com.google.guice, com.google.common, org.slf4j;
    opens com.event_bar_easv.gui.models to javafx.fxml, com.google.guice, com.google.common, org.slf4j;
    opens com.event_bar_easv.dal.reporitory to org.mybatis,javafx.fxml, com.google.guice, com.google.common, org.slf4j;

}