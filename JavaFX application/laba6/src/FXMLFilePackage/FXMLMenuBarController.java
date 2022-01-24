package FXMLFilePackage;

import classFilePackage.FunctionPointT;
import classFilePackage.TabulatedFunctionDoc;
import functions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLMenuBarController extends FXMLMainFormController implements Initializable {

    @FXML
    private Button MenuBar_Button_Cancel;

    @FXML
    private Button MenuBar_Button_OK;

    @FXML
    private Spinner<Integer> MenuBar_PointsCount;

    @FXML
    private TextField MenuBar_LeftDomainBorder;

    @FXML
    private TextField MenuBar_RightDomainBorder;

    @FXML
    private TableView<FunctionPointT> MainWindow_Table;

    private FXMLMainFormController MainController;

    public void setMainController(FXMLMainFormController Main){
        MainController = Main;
    }

    public double getLeftDomainBorder() {
        try {
            return Double.parseDouble((String) MenuBar_LeftDomainBorder.getText());
        } catch (NumberFormatException e) {
            error("Incorrect borders");
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getRightDomainBorder() {
        try {
            return Double.parseDouble((String) MenuBar_RightDomainBorder.getText());
        } catch (NumberFormatException e) {
            error("Incorrect borders");
        }
        return Double.NEGATIVE_INFINITY;
    }

    public int getPointsCount() {
        return (int) MenuBar_PointsCount.getValue();
    }

    private void error(String str) {
        Alert er = new Alert(Alert.AlertType.INFORMATION);
        er.setTitle("Error");
        er.setHeaderText("Reason:");
        er.setContentText(str);
        er.showAndWait();
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(2);
        MenuBar_PointsCount.setValueFactory(valueFactory);

        MenuBar_PointsCount.setEditable(true);

        MenuBar_LeftDomainBorder.setOnMouseClicked(event -> MenuBar_LeftDomainBorder.setText(""));
        MenuBar_RightDomainBorder.setOnMouseClicked(event -> MenuBar_RightDomainBorder.setText(""));

        MenuBar_Button_Cancel.setOnAction(event -> {
            Stage stage = (Stage) MenuBar_Button_Cancel.getScene().getWindow();
            stage.close();
        });

        MenuBar_Button_OK.setOnAction(event -> {
            try {
                Main.tabFDoc.newFunction(this.getLeftDomainBorder(), this.getRightDomainBorder(), this.getPointsCount());
                Stage stage = (Stage) MenuBar_Button_OK.getScene().getWindow();
                stage.close();
            } catch (Exception ex) { error("Check borders and points count"); }
        });
    }
}
