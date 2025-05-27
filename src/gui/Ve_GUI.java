package gui;

import java.io.File;
import java.io.IOException;

import entity.Ve;
import gui.controllers.QuanLyVe_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Ve_GUI {
	private Ve ve;
	private Stage primaryStage;
    private String maNhanVien;
	public Ve getVe() {
		return ve;
	}
	public void setVe(Ve ve) {
		this.ve = ve;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public Ve_GUI(Ve ve, Stage primaryStage, String maNhanVien) {
		super();
		this.ve = ve;
		this.primaryStage = primaryStage;
		this.maNhanVien = maNhanVien;
	}
	
	
}
