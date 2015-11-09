package management.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import management.model.Costumer;

public class CostumerEditDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField managerField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField ucnUicField;
	@FXML
	private TextField birthdayField;

	private Stage dialogStage;
	private Costumer costumer;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the costumer to be edited in the dialog.
	 *
	 * @param costumer
	 */
	public void setCostumer(Costumer costumer) {
		this.costumer = costumer;

		nameField.setText(costumer.getName());
		cityField.setText(costumer.getCity());
		managerField.setText(costumer.getManager());
		addressField.setText(costumer.getAddress());
		emailField.setText(costumer.getEmail());
		ucnUicField.setText(Integer.toString(costumer.getUcnUic()));
		// !!! TODO - Data Picker !!!
		birthdayField.setText(costumer.getBirthday());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            costumer.setName(nameField.getText());
            costumer.setCity(cityField.getText());
            costumer.setManager(managerField.getText());
            costumer.setAddress(addressField.getText());
            costumer.setEmail(emailField.getText());
            costumer.setUcnUic(Integer.parseInt(ucnUicField.getText()));
            costumer.setBirthday(birthdayField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "No valid city!\n";
		}
		if (managerField.getText() == null || managerField.getText().length() == 0) {
			errorMessage += "No valid manager name!\n";
		}

		if (addressField.getText() == null || addressField.getText().length() == 0) {
			errorMessage += "No valid address!\n";
		}

		if (emailField.getText() == null || emailField.getText().length() == 0) {
			errorMessage += "No valid email address!\n";
		}
		// !!! TODO - email validation !!!

		if (ucnUicField.getText() == null || ucnUicField.getText().length() == 0) {
			errorMessage += "No valid UCN/UIC!\n";
		} else {
			// try to parse the ucnUic into an int
			try {
				Integer.parseInt(ucnUicField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid UCN/UIC - must be a number (integer)!\n";
			}
		}

		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "No valid Birthday!\n";
		}
		// !!! TODO - birthday (data picker) validation
		// if (birthdayField.getText() == null ||
		// birthdayField.getText().length() == 0) {
		// errorMessage += "No valid birthday!\n";
		// } else {
		// if (!CalendarUtil.validString(birthdayField.getText())) {
		// errorMessage += "No valid birthday. Use the format yyyy-mm-dd!\n";
		// }
		// }

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Alert alert = new Alert(AlertType.ERROR, errorMessage);
			alert.show();
			return false;
		}
	}
}
