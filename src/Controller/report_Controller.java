package Controller;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class report_Controller implements Initializable {
    public TableView monthlyReportTable;
    public TableColumn monthCol;
    public TableColumn scheduleTypeCol;
    public TableColumn countCol;
    public TableView contactScheduleReport;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public ComboBox reportMonth;
    public ComboBox reportContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportMonth.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
    }
}
