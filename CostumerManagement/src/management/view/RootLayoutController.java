package management.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.stage.FileChooser;
import management.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootLayoutController {

	// Reference to the main application
	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Creates an empty address book.
	 */
	@FXML
	private void handleNew() {
		mainApp.getCostumerData().clear();
		mainApp.setCostumerFilePath(null);
	}

	/**
	 * Opens a FileChooser to let the user select an address book to load.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (file != null) {
			mainApp.loadCostumerDataFromFile(file);
		}
	}

	/**
	 * Saves the file to the costumer file that is currently open. If there is
	 * no open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		File costumerFile = mainApp.getCostumerFilePath();
		if (costumerFile != null) {
			mainApp.saveCostumerDataToFile(costumerFile);
		} else {
			handleSaveAs();
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveCostumerDataToFile(file);
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Dialogs.showInformationDialog(mainApp.getPrimaryStage(), "Author: Kaloyan Kalchev", "Costumer Management",
				"About");
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
