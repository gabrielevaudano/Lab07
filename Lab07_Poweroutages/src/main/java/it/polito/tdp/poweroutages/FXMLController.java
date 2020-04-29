/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

	private Model model;

    @FXML
    private ComboBox<Nerc> comboNerc;

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
    	Nerc selectedNerc = comboNerc.getSelectionModel().getSelectedItem();
		if (selectedNerc == null) {
			txtArea.setText("Select a NERC (area identifier)");
			return;
		}    	
		
		Integer maxYears = Integer.parseInt(txtMaxYears.getText());
    	Integer maxHours = Integer.parseInt(txtMaxHours.getText());

    	List<PowerOutage> powerOutages = new ArrayList<PowerOutage>(model.ricercaWorstCase(selectedNerc, maxYears, maxHours));
    	System.out.println(powerOutages);
    	
    	StringBuilder stringa = new StringBuilder();
    	
    	for (PowerOutage po : powerOutages)
    		stringa.append(po.toString() + "\n");
    	
    	txtArea.setText(stringa.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    }

	public void setModel(Model model) {
		this.model = model;
		comboNerc.getItems().addAll(model.getNercList());
		comboNerc.getSelectionModel().selectFirst();
	}
}

