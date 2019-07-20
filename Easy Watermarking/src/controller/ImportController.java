package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImportController {
	@FXML
	private Label todayDate;
	@FXML
	private JFXButton importPics;
	@FXML
	private JFXButton importWater;
	@FXML
	private TreeTableView<String> table;
	@FXML
	private JFXListView<Label> vieww;
	@FXML
	private JFXListView<Label> viewWater;

	public ImportController() throws FileNotFoundException {

	}

	@FXML
	public void initialize() throws FileNotFoundException {
		this.todayDate.setText(new Date().toString());
		this.vieww.setExpanded(true);
		this.vieww.depthProperty().set(1);
		// this.vieww = new JFXListView<>();


	}

	@FXML
	public void importPicsAction() throws FileNotFoundException {
		this.ChooseFile("Choose your pictures to watermark them: ", false);

	}

	public void fillListWater(File file) throws FileNotFoundException {
		Label lbl = new Label();
		ImageView ImageView = new ImageView(new Image(new FileInputStream(file)));
		ImageView.setFitHeight(350);
		ImageView.setFitWidth(350);
		lbl.setGraphic(ImageView);
		this.viewWater.getItems().add(lbl);
	}

	public void fillList(List<File> list) throws FileNotFoundException {
		for (File i : list) {
			Label lbl = new Label();
			ImageView ImageView = new ImageView(new Image(new FileInputStream(i)));
			ImageView.setFitHeight(100);
			ImageView.setFitWidth(100);
			lbl.setGraphic(ImageView);
			this.vieww.getItems().add(lbl);
		}
	}

	/**
	 * ChooseFile launchs JFileChooser that will prompt the user for pictures
	 *
	 * @throws FileNotFoundException
	 */
	public void ChooseFile(String dialogTitle, boolean OnlyPng) throws FileNotFoundException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle(dialogTitle);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter;
		if (OnlyPng) {
			filter = new FileNameExtensionFilter("png", ".png");
			jfc.setMultiSelectionEnabled(false);
		} else {
			jfc.setMultiSelectionEnabled(true);
			filter = new FileNameExtensionFilter("png", ".png", ".jpg", "jpg");

		}
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (OnlyPng) {
				File file = jfc.getSelectedFile();
				this.fillListWater(file);
			} else {
				File[] files = jfc.getSelectedFiles();
				List<File> list = Arrays.asList(files);

				this.fillList(list);
			}

		}

	}

	@FXML
	public void importWaterAction() throws FileNotFoundException {
		this.ChooseFile("Choose your watermarker: ", true);

	}

}
