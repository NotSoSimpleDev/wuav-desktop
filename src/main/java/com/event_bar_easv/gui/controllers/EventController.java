package com.event_bar_easv.gui.controllers;


import com.event_bar_easv.be.Event;
import com.event_bar_easv.gui.models.event.IEventModel;
import com.google.inject.Inject;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Movies with the view
 */
public class EventController extends RootController implements Initializable {


    @FXML
    private MenuButton eventTicketType;
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
        var test = eventModel.getAllEvents();
        System.out.println(test);
        eventTable.setItems(test);
    }


    private void fillTicketTypesWithData() {
        // needs to fill here with all ticket types set by admin
        List<Event> categoryList = eventModel.getAllEvents();
        if (eventTicketType.getItems() != null) {
            eventTicketType.getItems().clear();
            categoryList.stream()
                    .map(event -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(event.getEventName());

                        return menuItem;
                    })
                    .forEach(menuItem -> eventTicketType.getItems().add(menuItem));
        }
    }


}
