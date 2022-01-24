package FXMLFilePackage;

import FXMLFilePackage.FXMLMainFormController;
import classFilePackage.FunctionPointT;
import classFilePackage.TabulatedFunctionDoc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static ObservableList<FunctionPointT> list = FXCollections.observableArrayList();
    public static TabulatedFunctionDoc tabFDoc;

    public void start(Stage primaryStage) throws Exception {
        tabFDoc = new TabulatedFunctionDoc();
        tabFDoc.newFunction(-5, 5, 10);
        tabFDoc.saveFunctionAs("tabFDoc.json");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainForm.fxml"));
        Parent root = loader.load();
        FXMLMainFormController ctrl = loader.getController();
        ctrl.setStage(primaryStage);
        tabFDoc.registerRedrawFunctionController(ctrl);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Tabulated function");
        primaryStage.show();

//        ctrl.getMainWindow_MenuItem_New().setOnAction((actionEvent -> {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("FXMLMenuBar.fxml"));
//                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//                Stage stage = new Stage();
//                stage.setTitle("Function parameters");
//                stage.setScene(scene);
//                stage.initModality(Modality.APPLICATION_MODAL);
//                stage.initOwner(primaryStage);
//                stage.setResizable(false);
//                stage.showAndWait();
//            } catch (IOException e) { e.printStackTrace(); }
//        }));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
