package management.view;

import org.omg.PortableInterceptor.Interceptor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import management.model.Costumer;
import management.model.Credit;

public class CreditEditDialogController {
	@FXML
	private TextField creditNumberField;
	@FXML
	private TextField startDateField;
	@FXML
	private TextField maturityDateField;
	@FXML
	private TextField termField;
	@FXML
	private TextField interestRateField;
	@FXML
	private TextField loanAmountField;

	private Stage dialogStage;
	private Credit credit;
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
	public void setCredit(Credit credit) {
		this.credit = credit;
		creditNumberField.setText(Integer.toString(credit.getCreditNumber()));
		startDateField.setText(credit.getStartDate());
		maturityDateField.setText(credit.getMaturityDate());
		termField.setText(Integer.toString(credit.getTerm()));
		interestRateField.setText(Double.toString(credit.getInterestRate()));
		loanAmountField.setText(Double.toString(credit.getLoanAmount()));
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

			credit.setCreditNumber(Integer.parseInt(creditNumberField.getText()));
			credit.setStartDate(startDateField.getText());
			credit.setMaturityDate(maturityDateField.getText());
			credit.setTerm(Integer.parseInt(termField.getText()));
			credit.setInterestRate(Double.parseDouble(interestRateField.getText()));
			credit.setLoanAmount(Double.parseDouble(loanAmountField.getText()));

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

		if (creditNumberField.getText() == null || creditNumberField.getText().length() == 0) {
			errorMessage += "No valid credit number!\n";
		}
		if (startDateField.getText() == null || startDateField.getText().length() == 0) {
			errorMessage += "No valid start date!\n";
		}
		if (maturityDateField.getText() == null || maturityDateField.getText().length() == 0) {
			errorMessage += "No valid maturity date!\n";
		}

		if (termField.getText() == null || termField.getText().length() == 0) {
			errorMessage += "No valid term!\n";
		}

		if (interestRateField.getText() == null || interestRateField.getText().length() == 0) {
			errorMessage += "No valid interest rate!\n";
		}

		if (loanAmountField.getText() == null || loanAmountField.getText().length() == 0) {
			errorMessage += "No valid loan amount!\n";
		} else {
			// try to parse the credit number into an int
			try {
				Integer.parseInt(creditNumberField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid credit number - must be a number (integer)!\n";
			}
		}

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
