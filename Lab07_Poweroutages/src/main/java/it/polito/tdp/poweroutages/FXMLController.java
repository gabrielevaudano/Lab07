/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

	private Model model;

    @FXML
    private TextField txtSelectNerc;

    @FXML
    private TextField txtMaxYears;

    @FXML
    private TextField txtMaxHours;

    @FXML
    private Button btnCalculate;

    @FXML
    private TextArea txtArea;

    @FXML
    void calculate(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

	public void setModel(Model model) {
		this.model = model;
	}
}

