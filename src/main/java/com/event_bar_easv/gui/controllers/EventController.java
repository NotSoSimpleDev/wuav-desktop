package com.event_bar_easv.gui.controllers;


import com.event_bar_easv.be.Event;
import com.event_bar_easv.gui.models.event.IEventModel;
import com.google.inject.Inject;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Controller for Movies with the view
 */
public class EventController extends RootController implements Initializable {


    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event,String> colEventTitle;
    @FXML
    private TableColumn<Event,String> colEventDates;
    @FXML
    private TableColumn<Event,String> colEventTimes;
    @FXML
    private TableColumn<Event,String> colEventLocation;
    @FXML
    private TableColumn<Event,String> colEventDescription;
    @FXML
    private TableColumn<Event,String> colEventFreeTicket;


    private final IEventModel eventModel;

    @Inject
    public EventController(IEventModel eventModel) {
        this.eventModel = eventModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTableWithEventsData();
    }

    private void fillTableWithEventsData() {

        colEventTitle.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEventName()));

        colEventDates.setCellValueFactory(cellData -> {
            Date startDate = cellData.getValue().getStartDate();
            Date endDate = cellData.getValue().getEndDate();

            return new SimpleStringProperty(startDate.toString() + endDate.toString());
        });

        colEventTimes.setCellValueFactory(cellData -> {
            String startTime = cellData.getValue().getStartTime();
            String endTime = cellData.getValue().getEndTime();

            return new SimpleStringProperty(startTime + endTime);
        });

        colEventLocation.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLocation()));
        colEventDescription.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));

        trySetEventIntoTable();
    }

    private void trySetEventIntoTable() {
        eventTable.setItems(eventModel.getAllEvents());
    }


}
