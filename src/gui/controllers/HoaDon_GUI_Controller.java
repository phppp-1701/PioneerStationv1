package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entity.HoaDon;
import javafx.fxml.Initializable;

public class HoaDon_GUI_Controller implements Initializable {
	private HoaDon hoaDon;
	public HoaDon_GUI_Controller(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}