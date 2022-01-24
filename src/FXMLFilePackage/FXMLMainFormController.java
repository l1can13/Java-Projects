package FXMLFilePackage;

import classFilePackage.*;
import functions.*;

import functions.basic.Cos;
import functions.basic.Sin;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainFormController implements Initializable {

    @FXML
    private Label pointsLabel = new Label();

    @FXML
    private TextField edX;

    @FXML
    private TextField edY;

    @FXML
    private Button MainWindow_Button_AddPoint;

    @FXML
    private Button MainWindow_Button_DeletePoint;

    @FXML
    private TableView<FunctionPointT> MainWindow_Table = new TableView<FunctionPointT>();

    @FXML
    private TableColumn<FunctionPointT, Double> MainWindow_TableColumn_X = new TableColumn<FunctionPointT, Double>("X");

    @FXML
    private TableColumn<FunctionPointT, Double> MainWindow_TableColumn_Y = new TableColumn<FunctionPointT, Double>("Y");

    @FXML
    private MenuBar MainWindow_MenuBar;

    @FXML
    private MenuItem MainWindow_MenuItem_New;

    @FXML
    private MenuItem MainWindow_MenuItem_Load;

    @FXML
    private MenuItem MainWindow_MenuItem_OpenFile;

    @FXML
    private MenuItem MainWindow_MenuItem_SaveFile;

    @FXML
    private MenuItem MainWindow_MenuItem_SaveFileAs;

    @FXML
    private MenuItem MainWindow_MenuItem_Close;

    private ClassLoaderForFunction functionClassLoader = new ClassLoaderForFunction();
    public MenuItem getMainWindow_MenuItem_New() { return MainWindow_MenuItem_New; }
    private void error(String str) {
        Alert er = new Alert(Alert.AlertType.INFORMATION);
        er.setTitle("Error");
        er.setHeaderText("Reason:");
        er.setContentText(str);
        er.showAndWait();
    }

    private int kek;
    private Stage MainStage;
    private File file;
    private ClassLoaderForFunction Loader;
    private FXMLMenuBarController ctrl;
    private Alert dialog;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        Main.tabFDoc.registerRedrawFunctionController(this);
        MainWindow_TableColumn_X.setPrefWidth(185);
        MainWindow_TableColumn_Y.setPrefWidth(185);

        MainWindow_TableColumn_X.setCellValueFactory(new PropertyValueFactory <FunctionPointT, Double>("X"));
        MainWindow_TableColumn_Y.setCellValueFactory(new PropertyValueFactory <FunctionPointT, Double>("Y"));

        MainWindow_Table.getColumns().addAll(MainWindow_TableColumn_X,MainWindow_TableColumn_Y);

        MainWindow_Table.setItems(Main.list);

        kek = MainWindow_Table.getSelectionModel().selectedIndexProperty().get() + 1;
        pointsLabel.setText("Point " + kek + " of " + Main.tabFDoc.getPointsCount());

        MainWindow_Table.setOnMouseClicked(event -> {
            kek = MainWindow_Table.getSelectionModel().selectedIndexProperty().get() + 1;
            pointsLabel.setText("Point " + kek + " of " + Main.tabFDoc.getPointsCount());
        });

        MainWindow_MenuItem_New.setOnAction(event -> {
            try {
                openWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MainWindow_MenuItem_Load.setOnAction(event -> {
            try {
                TabulatedFunctionDoc temp = new TabulatedFunctionDoc();
                temp.setLink(Main.tabFDoc.getLink());
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open class file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Classes", "*.class"));
                file = fileChooser.showOpenDialog(MainWindow_Table.getScene().getWindow());
                if (file != null) {
                    openWindow();
                    if (ctrl.getLeftDomainBorder() != Double.POSITIVE_INFINITY && ctrl.getRightDomainBorder() != Double.NEGATIVE_INFINITY && ctrl.getPointsCount() > 2) {
                        try {
                        Main.tabFDoc.tabulateFunction((Function) functionClassLoader.loadClassFromFile(file).getConstructor().newInstance(), ctrl.getLeftDomainBorder(), ctrl.getRightDomainBorder(), ctrl.getPointsCount());
                        } catch (InstantiationException e) {
                            error("Unidentified error, Try again");
                        } catch (IllegalAccessException e) {
                            error("Unidentified error, Try again");
                        } catch (InvocationTargetException e) {
                            error("Unidentified error, Try again");
                        } catch (NoSuchMethodException e) {
                            error("Unidentified error, Try again");
                        } catch (IOException e) {
                            error("Unidentified error, Try again");
                        }catch(ClassFormatError e){
                            error("Incorrect type of file, Try again");
                        }
                        catch(LinkageError e){
                            error("Unidentified error, Try again");
                        }
                    }
                }
            } catch (Exception e) { }

        });

        MainWindow_MenuItem_SaveFileAs.setOnAction(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose pass");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.json"));
                file = fileChooser.showSaveDialog(MainWindow_Table.getScene().getWindow());
                if (file != null) {
                    Main.tabFDoc.saveFunctionAs(file.getAbsolutePath());
                }
            } catch (Exception e) { error("Error!"); }
        });

        MainWindow_MenuItem_SaveFile.setOnAction(event -> {
            try {
                Main.tabFDoc.saveFunction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MainWindow_MenuItem_OpenFile.setOnAction(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.json"));
                file = fileChooser.showOpenDialog(MainWindow_Table.getScene().getWindow());
                if (file != null) {
                    Main.tabFDoc.loadFunction(file.getAbsolutePath());
                }
            } catch (Exception e) { }
        });

        MainWindow_MenuItem_Close.setOnAction(event -> {
            if (Main.tabFDoc.isSaved()) {
                Stage stage = (Stage) MainWindow_Button_AddPoint.getScene().getWindow();
                stage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setContentText("File is not saved!");
                alert.showAndWait();
            }
        });
    }

    private void openWindow() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXMLMenuBar.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            ctrl = fxmlLoader.getController();
            ctrl.setMainController(this);
            stage.setTitle("Function parameters");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(MainStage);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void addPoint(ActionEvent av) {
        double x, y;
        try {
            x = Double.parseDouble(edX.getText());
            edX.clear();
            y = Double.parseDouble(edY.getText());
            edY.clear();
            if (x < Main.list.get(0).getX() || x > Main.list.get(Main.list.size() - 1).getX())
                    throw new ArrayIndexOutOfBoundsException();
            for (FunctionPointT i : MainWindow_Table.getItems())
                    if (Math.abs(x - i.getX()) < 0.0001) throw new InappropriateFunctionPointException();
            int n = 0;
            for (FunctionPointT i : MainWindow_Table.getItems())
                if (x < i.getX()) ++n;
            Main.tabFDoc.addPoint(new FunctionPoint(x, y));
        } catch (NumberFormatException error) {
                System.out.println("wrong input");
                edX.clear();
                edY.clear();
//                ErrorShow(e.getStackTrace().toString());
            } catch (InappropriateFunctionPointException error) {
                System.out.println("wrong point");
//                ErrorShow(e.getStackTrace().toString());
            } catch (ArrayIndexOutOfBoundsException error) {
                System.out.println("out of bound");
//                ErrorShow(e.getStackTrace().toString());
            }
    }

    public void setStage(Stage stage){
        MainStage = stage;
    }

    @FXML
    private void deletePoint(ActionEvent av) {
        try {
            if (MainWindow_Table.getItems().size() < 3)
                throw new IllegalArgumentException();
            else {
                int i = MainWindow_Table.getSelectionModel().getSelectedIndex();
                double t = MainWindow_Table.getItems().get(i).getX();
                for (int j = 0; j < Main.tabFDoc.getPointsCount(); j++) {
                    if (Math.abs(Main.tabFDoc.getPointX(j) - t) < 0.001) {
                        Main.tabFDoc.deletePoint(j + 1);
                        break;
                    }
                }
            }
            } catch (IllegalArgumentException error) {
                System.out.println("less then 3 point");
//                ErrorShow(e.getStackTrace().toString());
            }
        }

    public void redraw() {
        Main.list.clear();
        for (int i = 0; i < Main.tabFDoc.getPointsCount(); ++i) {
            Main.list.add(new FunctionPointT(Main.tabFDoc.getPointX(i), Main.tabFDoc.getPointY(i)));
        }
        MainWindow_TableColumn_X.setCellValueFactory(new PropertyValueFactory <FunctionPointT, Double>("X"));
        MainWindow_TableColumn_Y.setCellValueFactory(new PropertyValueFactory <FunctionPointT, Double>("Y"));
        MainWindow_Table.setItems(Main.list);
    }

}
