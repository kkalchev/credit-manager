package management.view;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import management.MainApp;
import management.model.Costumer;
import management.model.Credit;

public class CostumerOverviewController {
	@FXML
	private TableView<Costumer> costumerTable;
	@FXML
	private TableColumn<Costumer, String> nameColumn;
	@FXML
	private TableColumn<Costumer, String> cityColumn;

	@FXML
	private TableView<Credit> creditsTable;
	@FXML
	private TableColumn<Credit, String> creditNumberColumn;
	@FXML
	private TableColumn<Credit, String> startDateColumn;
	@FXML
	private TableColumn<Credit, String> maturityDateColumn;
	@FXML
	private TableColumn<Credit, String> termColumn;
	@FXML
	private TableColumn<Credit, String> interestRateColumn;
	@FXML
	private TableColumn<Credit, String> loanAmountColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label managerLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label ucnUicLabel;
	@FXML
	private Label birthdayLabel;

	// Reference to the main application
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public CostumerOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the Costumer table
		nameColumn.setCellValueFactory(new PropertyValueFactory<Costumer, String>("name"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<Costumer, String>("city"));

		// Initialize the Credit table
		creditNumberColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("creditNumber"));
		startDateColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("startDate"));
		maturityDateColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("maturityDate"));
		termColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("term"));
		interestRateColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("interestRate"));
		loanAmountColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("loanAmount"));

		// clear costumer
		showCostumerDetails(null);

		// Listen for selection changes
		costumerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Costumer>() {

			@Override
			public void changed(ObservableValue<? extends Costumer> observable, Costumer oldValue, Costumer newValue) {
				showCostumerDetails(newValue);
			}
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the tables
		costumerTable.setItems(mainApp.getCostumerData());
	}

	/**
	 * Fills all text fields to show details about the costumer. If the
	 * specified costumer is null, all text fields are cleared.
	 *
	 * @param costumer
	 *            the costumer or null
	 */
	private void showCostumerDetails(Costumer costumer) {
		if (costumer != null) {
			// use setText(...) on all labels with info from the costumer object
			nameLabel.setText(costumer.getName());
			cityLabel.setText(costumer.getCity());
			managerLabel.setText(costumer.getManager());
			addressLabel.setText(costumer.getAddress());
			emailLabel.setText(costumer.getEmail());
			ucnUicLabel.setText(String.valueOf(costumer.getUcnUic()));
			birthdayLabel.setText(costumer.getBirthday());

			creditsTable.setItems(costumer.getCreditsData());
			// use setText("") on all labels if the costumer is null
		} else {
			nameLabel.setText("");
			cityLabel.setText("");
			managerLabel.setText("");
			addressLabel.setText("");
			emailLabel.setText("");
			ucnUicLabel.setText("");
			birthdayLabel.setText("");

		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteCostumer() {
		try {
			int selectedIndex = costumerTable.getSelectionModel().getSelectedIndex();
			costumerTable.getItems().remove(selectedIndex);
		} catch (Exception e) {
			// Nothing selected
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Costumer Selected");
			alert.setContentText("Please select a costumer in the table.");
			alert.showAndWait();
		}

	}

	/**
	 * Called when the user clicks on the Delete Credit button.
	 */
	@FXML
	private void handleDeleteCredit() {
		try {
			int selectedIndex = creditsTable.getSelectionModel().getSelectedIndex();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Credit Delete Confirmation!");
			alert.setHeaderText("Do you realy want to delete the selected credit?");
			alert.setContentText("Click OK for Delete or Cancel for keep it!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				creditsTable.getItems().remove(selectedIndex); // ... user chose OK
			} else {
			    // ... user chose CANCEL or closed the dialog
			}

		} catch (Exception e) {
			// Nothing selected
			// System.out.println("v exception-a sme ihuuuuuuuuuuuuuuuu :)");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Credit Selected");
			alert.setContentText("Please select a credit in the table.");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new costumer.
	 */
	@FXML
	private void handleNewCostumer() {
		Costumer tempCostumer = new Costumer();
		boolean okClicked = mainApp.showCostumerEditDialog(tempCostumer);
		if (okClicked) {
			mainApp.getCostumerData().add(tempCostumer);
		}
	}

	/**
	 * Called when the user clicks the New Credit button. Opens a dialog to edit
	 * details for a new credit.
	 */
	@FXML
	private void handleNewCredit() {
		Costumer selectedCostumer = costumerTable.getSelectionModel().getSelectedItem();
		Credit tempCredit = new Credit();
		tempCredit.setCreditNumber(selectedCostumer.getNextNumber());
		boolean okClicked = mainApp.showCreditEditDialog(tempCredit);
		if (okClicked) {
			selectedCostumer.getCreditsData().add(tempCredit);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected costumer.
	 */
	@FXML
	private void handleEditCostumer() {
		Costumer selectedCostumer = costumerTable.getSelectionModel().getSelectedItem();
		if (selectedCostumer != null) {
			boolean okClicked = mainApp.showCostumerEditDialog(selectedCostumer);
			if (okClicked) {
				refreshCostumerTable();
				showCostumerDetails(selectedCostumer);
			}

		} else {
			// Nothing selected
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Costumer Selected");
			alert.setContentText("Please select a costumer in the table.");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the Edit Credit button. Opens a dialog to
	 * edit details for the selected costumer.
	 */
	@FXML
	private void handleEditCredit() {
		Credit selectedCredit = creditsTable.getSelectionModel().getSelectedItem();
		if (selectedCredit != null) {
			boolean okClicked = mainApp.showCreditEditDialog(selectedCredit);
			if (okClicked) {
				refreshCreditsTable();
			}

		} else {
			// Nothing selected
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Credit Selected");
			alert.setContentText("Please select a credit in the table.");
			alert.showAndWait();
		}
	}

	/**
	 * Refreshes the table. This is only necessary if an item that is already in
	 * the table is changed. New and deleted items are refreshed automatically.
	 *
	 * This is a workaround because otherwise we would need to use property
	 * bindings in the model class and add a *property() method for each
	 * property. Maybe this will not be necessary in future versions of JavaFX
	 * (see http://javafx-jira.kenai.com/browse/RT-22599)
	 */
	private void refreshCostumerTable() {
		int selectedIndex = costumerTable.getSelectionModel().getSelectedIndex();
		costumerTable.setItems(null);
		costumerTable.layout();
		costumerTable.setItems(mainApp.getCostumerData());
		// Must set the selected index again
		costumerTable.getSelectionModel().select(selectedIndex);
	}

	private void refreshCreditsTable() {
		Costumer costumer = costumerTable.getSelectionModel().getSelectedItem();
		int selectedIndex = creditsTable.getSelectionModel().getSelectedIndex();
		creditsTable.setItems(null);
		creditsTable.layout();
		creditsTable.setItems(costumer.getCreditsData());
		// Must set the selected index again
		creditsTable.getSelectionModel().select(selectedIndex);
	}

}
