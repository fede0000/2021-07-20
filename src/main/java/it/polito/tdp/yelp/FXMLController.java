/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.Vertici;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<String> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	
    	String input = txtN.getText();
    	
    	if(input == "") {
    		txtResult.setText("Numero recensioni mancante\n");
    	}
    	
    	
    	
    	try {
    		int inputNum = Integer.parseInt(input);
    		
    		Integer anno =  this.cmbAnno.getValue();
        	
        	if(anno==null) {
        		txtResult.appendText("Anno mancante");

        	}else {
        		
        		model.creaGrafo(inputNum, anno);
        		String stringa= "I vertici sono: " + model.getNVertici()+ "\nGli archi sono: "+ model.getNArchi();
        		txtResult.setText(stringa);
        		
        		this.cmbUtente.getItems().setAll(model.getVertici());
        	}
        	
        	
    		
    		
    	}catch(NumberFormatException e) {
    		txtResult.setText("Errore");
    	}

    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	
    	String user = this.cmbUtente.getValue();
    	
    	if(user==null) {
    		txtResult.setText("User mancante");
    	}
    	
    	List<Vertici> connessi = model.TrovaPesoMaxTraVicini(user);
		/*
		for (Vertici v : connessi) {
		    this.txtResult.appendText("\n" + v.getUserid() + " Numero vicini: " + connessi.size());
		}*/

    }
    
    @FXML
    void doSimula(ActionEvent event) {

    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbAnno.getItems().add(2005);
    	this.cmbAnno.getItems().add(2006);
    	this.cmbAnno.getItems().add(2007);
    	this.cmbAnno.getItems().add(2008);
    	this.cmbAnno.getItems().add(2009);
    	this.cmbAnno.getItems().add(2010);
    	this.cmbAnno.getItems().add(2011);
    	this.cmbAnno.getItems().add(2012);
    	this.cmbAnno.getItems().add(2013);
    }
}
