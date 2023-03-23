package com.event_bar_easv.gui.controllers;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.SpecialTicketType;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.models.event.EventModel;
import com.google.inject.Inject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TicketController extends RootController implements Initializable {


    @FXML
    private TextField ticketNameField;
    @FXML
    private TextField benfitField;
    @FXML
    private MenuButton eventTicketType;
    @FXML
    private TableView<SpecialTicketType> ticketTable;
    @FXML
    private TableColumn<SpecialTicketType,String> colEventTitle;
    @FXML
    private TableColumn<SpecialTicketType,String> colEventBenefit;
    @FXML

    private TableColumn<SpecialTicketType,String> colEvents;

    private final EventModel eventModel;

    @Inject
    public TicketController(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTableWithEventsData();
    }


    private void fillTableWithEventsData() {

        colEventTitle.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType()));
        colEventBenefit.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBenefit()));


        trySetEventIntoTable();
    }

    private void trySetEventIntoTable() {
        var test = eventModel.getAllSpecialTickets();
        ticketTable.setItems(test);
    }



    @FXML
    private void createSpecialTicket(ActionEvent actionEvent) {

        if(!ticketNameField.getText().isEmpty() || !benfitField.getText().isEmpty() && eventTicketType != null){
            ticketNameField.getText();
            benfitField.getText();

            SpecialTicketType specialTicketType = new SpecialTicketType();
            specialTicketType.setId(564789);
            specialTicketType.setType(ticketNameField.getText());
            specialTicketType.setBenefit(benfitField.getText());

            int createdTicket = eventModel.createSpecialTicket(specialTicketType);

            if(createdTicket > 0){
                System.out.println("Assigning for all events");
                int result = eventModel.addSpecialTicketToAllEvent(specialTicketType);
                if(result > 0){
                    System.out.println("Ticket created");
                }else {
                    System.out.println("Failed to create ticket");
                }
            }


        }

    }
}
