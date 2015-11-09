package management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.thoughtworks.xstream.XStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import management.model.Costumer;
import management.model.Credit;
import management.util.FileUtil;
import management.view.CostumerEditDialogController;
import management.view.CostumerOverviewController;
import management.view.CreditEditDialogController;
import management.view.RootLayoutController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Costumer> costumerData = FXCollections.observableArrayList();
//	private Costumer costumer = new Costumer();

    public MainApp() {
    	// If I want to add some sample data:
    }

    /**
     * Returns the data as an observable list of Costumers.
     * @return
     */
    public ObservableList<Costumer> getCostumerData() {
        return costumerData;
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Costumer Management");

        try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

         // Give the controller access to the main app
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        showCostumerOverview();

     // Try to load last opened costumer file
        File file = getCostumerFilePath();
        if (file != null) {
          loadCostumerDataFromFile(file);
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Shows the costumer overview scene.
     */
    public void showCostumerOverview() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CostumerOverview.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);

            // Give the controller access to the main app
            CostumerOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified costumer. If the user
     * clicks OK, the changes are saved into the provided costumer object and
     * true is returned.
     *
     * @param costumer - the costumer object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showCostumerEditDialog(Costumer costumer) {
    	  try {
    	    // Load the fxml file and create a new stage for the popup
    	    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CostumerEditDialog.fxml"));
    	    GridPane page = (GridPane) loader.load();
    	    Stage dialogStage = new Stage();
    	    dialogStage.setTitle("Edit Costumer");
    	    dialogStage.initModality(Modality.WINDOW_MODAL);
    	    dialogStage.initOwner(primaryStage);
    	    Scene scene = new Scene(page);
    	    dialogStage.setScene(scene);

    	    // Set the costumer into the controller
    	    CostumerEditDialogController controller = loader.getController();
    	    controller.setDialogStage(dialogStage);
    	    controller.setCostumer(costumer);

    	    // Show the dialog and wait until the user closes it
    	    dialogStage.showAndWait();

    	    return controller.isOkClicked();

    	  } catch (IOException e) {
    	    // Exception gets thrown if the fxml file could not be loaded
    	    e.printStackTrace();
    	    return false;
    	  }
    	}

    public boolean showCreditEditDialog(Credit credit) {
    	try {
    	    // Load the fxml file and create a new stage for the popup
    	    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CreditEditDialog.fxml"));
    	    GridPane page = (GridPane) loader.load();
    	    Stage dialogStage = new Stage();
    	    dialogStage.setTitle("Edit Credit");
    	    dialogStage.initModality(Modality.WINDOW_MODAL);
    	    dialogStage.initOwner(primaryStage);
    	    Scene scene = new Scene(page);
    	    dialogStage.setScene(scene);

    	    // Set the Credit into the controller
    	    CreditEditDialogController controller = loader.getController();
    	    controller.setDialogStage(dialogStage);
    	    controller.setCredit(credit);

    	    // Show the dialog and wait until the user closes it
    	    dialogStage.showAndWait();

    	    return controller.isOkClicked();

    	  } catch (IOException e) {
    	    // Exception gets thrown if the fxml file could not be loaded
    	    e.printStackTrace();
    	    return false;
    	  }
	}

    /**
     * Returns the costumer file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getCostumerFilePath() {
      Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
      String filePath = prefs.get("filePath", null);
      if (filePath != null) {
        return new File(filePath);
      } else {
        return null;
      }
    }

    /**
     * Sets the file path of the currently loaded file.
     * The path is persisted in the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setCostumerFilePath(File file) {
      Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
      if (file != null) {
        prefs.put("filePath", file.getPath());

        // Update the stage title
        primaryStage.setTitle("CostumerManagement - " + file.getName());
      } else {
        prefs.remove("filePath");

        // Update the stage title
        primaryStage.setTitle("CostumerManagement");
      }
    }

    /**
     * Loads costumer data from the specified file. The current costumer data will
     * be replaced.
     *
     * @param file
     */
    @SuppressWarnings("unchecked")
    public void loadCostumerDataFromFile(File file) {
      XStream xstream = new XStream();
      xstream.alias("costumer", Costumer.class);

      try {
        String xml = FileUtil.readFile(file);

        ArrayList<Costumer> costumerList = (ArrayList<Costumer>) xstream.fromXML(xml);

        costumerData.clear();
        costumerData.addAll(costumerList);

        setCostumerFilePath(file);
      } catch (Exception e) { // catches ANY exception
    	  Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());
			alert.showAndWait();
      }
    }

    /**
     * Saves the current costumer data to the specified file.
     *
     * @param file
     */
    public void saveCostumerDataToFile(File file) {
      XStream xstream = new XStream();
      xstream.alias("costumer", Costumer.class);

      // Convert ObservableList to a normal ArrayList
      ArrayList<Costumer> costumerList = new ArrayList<>(costumerData);

      String xml = xstream.toXML(costumerList);
      try {
        FileUtil.saveFile(xml, file);

        setCostumerFilePath(file);
      } catch (Exception e) { // catches ANY exception
    	  Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			alert.showAndWait();
      }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
