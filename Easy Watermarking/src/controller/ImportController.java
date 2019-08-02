/**
 * @author Jose_Gracia_Berenguer
 * @version Jul 21, 2019
 */
package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Class that initializes the view of import.fxml and contains its containers
 * buttons etc.
 *
 * @author Jose Gracia
 *
 */
public class ImportController implements Initializable {
	@FXML
	private Label todayDate;
	@FXML
	private Label errorLabel;
	@FXML
	private JFXButton importPics;
	@FXML
	private JFXButton importWater;
	@FXML
	private JFXButton watermarkAll;
	@FXML
	private JFXButton remove;
	@FXML
	private TreeTableView<String> table;
	@FXML
	private JFXListView<Label> vieww;
	@FXML
	private JFXListView<Label> viewWater;
	private static String OS;

	// Default constructor
	public ImportController() {
		ImportController.OS = System.getProperty("os.name").toLowerCase();
	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		this.todayDate.setText(new Date().toString());
		this.vieww.setExpanded(true);
		this.vieww.depthProperty().set(1);
		this.remove.setDisable(true);
	}

	@FXML
	/**
	 * importPicsAction calls the method ChooseFile and initializes the FileChooser
	 * with a title and a boolean for not only selecting png's
	 *
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	private void importPicsAction() throws FileNotFoundException {
		this.ChooseFile("Choose your images to watermark them: ", false);
	}

	@FXML
	/**
	 * importWaterAction calls the method ChooseFile and initializes the FileChooser
	 * with a title and a boolean for only selecting png's
	 *
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	private void importWaterAction() throws FileNotFoundException {
		this.ChooseFile("Choose your watermark: ", true);
	}

	/**
	 * fillListWater receives a String with the path of the image and set it to the
	 * ListView with the watermark
	 *
	 * @param file string with the path of the image
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	private void fillListWater(String file) throws FileNotFoundException {
		Label lbl = new Label(file);
		ImageView ImageView = new ImageView(new Image(new FileInputStream(file)));
		ImageView.setFitHeight(350);
		ImageView.setFitWidth(350);
		lbl.setGraphic(ImageView);
		if (this.viewWater.getItems().size() > 0) {
			this.viewWater.getItems().clear();
		}
		this.viewWater.getItems().add(lbl);
	}

	/**
	 * fillList receives an ArrayList with all the files with paths of the images
	 * and sets it to the listView
	 *
	 * @param list ArrayList with all the files with paths of the images
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	private void fillList(List<File> list) throws FileNotFoundException {
		for (File i : list) {
			Label lbl = new Label(i.toString());
			ImageView ImageView = new ImageView(new Image(new FileInputStream(i)));
			ImageView.setFitHeight(100);
			ImageView.setFitWidth(100);
			lbl.setGraphic(ImageView);
			this.vieww.getItems().add(lbl);
			this.remove.setDisable(false);
		}
	}

	/**
	 * ChooseFile launches JFileChooser that will prompt the user for images
	 *
	 * @param dialogTitle the title that the window will display
	 *
	 * @param OnlyPng if the jfilechooser will allow only png files or not
	 *
	 * @throws FileNotFoundException if the image was not found in your file system.
	 */

	private void ChooseFile(String dialogTitle, boolean OnlyPng) throws FileNotFoundException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle(dialogTitle);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter;
		if (OnlyPng) {
			filter = new FileNameExtensionFilter("PNG watermark with transparency", "png");
			jfc.setMultiSelectionEnabled(false);
		} else {
			jfc.setMultiSelectionEnabled(true);
			filter = new FileNameExtensionFilter("PNG, TIFF, BMP, GIF, JPEG or JPG Files", "png", "jpg", "jpeg", "bmp",
					"tiff", "gif");

		}
		jfc.setFileFilter(filter);
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (OnlyPng) {
				String file = jfc.getSelectedFile().getAbsoluteFile().toString();
				this.fillListWater(file);
			} else {
				File[] files = jfc.getSelectedFiles();
				List<File> list = Arrays.asList(files);
				this.fillList(list);
			}
		}
	}

	@FXML
	/**
	 * watermarkAllAction when you press the middle button to perform the task.
	 * calls ImageController to perform the action and when it ends shows an alert
	 * with the paths of the folders with the images watermaked
	 *
	 * @throws IOException if there has been an error while doing the image parse.
	 */
	private void watermarkAllAction() throws IOException {
		if (this.vieww.getItems().size() == 0) {
			this.errorLabel.setText("You must import your images.");
		} else if (this.viewWater.getItems().size() == 0) {
			this.errorLabel.setText("You must import your watermark");
		} else {
			double startTime, endTime;
			startTime = System.nanoTime();
			ImageController imageController = new ImageController(ImportController.OS, this.vieww.getItems(),
					this.viewWater.getItems().get(0).getText());
			imageController.initializeThreadsAndRun();
			endTime = System.nanoTime() - startTime;

			if (imageController.CheckDifferentFolder().size() > 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Images created in: " + Math.floor((endTime / 1000000000)) + " seconds");
				alert.setTitle("Folders with your images watermarked:");
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.setResizable(true);
				String content = "";
				for (String i : imageController.CheckDifferentFolder()) {
					content += i + "\n";
				}
				alert.setContentText(content);
				alert.show();
				imageController.clearPaths();
			} else if (imageController.CheckDifferentFolder().size() == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Image created in: " + (endTime / 1000000000) + " seconds");
				alert.setTitle("Folder with your images watermarked:");
				alert.setContentText(imageController.CheckDifferentFolder().get(0));
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.setResizable(true);
				alert.show();
				imageController.clearPaths();
			}
		}
	}

	@FXML
	/**
	 * removeItem get the selected item from the JFXlistview and removes it when the
	 * button is pressed.
	 */
	private void removeItem() {
		if (this.vieww.getSelectionModel().getSelectedIndex() != -1) {
			this.vieww.getItems().remove(this.vieww.getSelectionModel().getSelectedIndex());
			if (this.vieww.getItems().size() == 0) {
				this.remove.setDisable(true);
			}
		}
	}

	@FXML
	/**
	 * quit directly exits the program with an exit code of 0.
	 */
	private void quit() {
		System.exit(0);
	}

	@FXML
	/**
	 * about launches the default browser and looks for a URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	private void about() throws IOException, URISyntaxException {

		if (this.isUnix()) {
			if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
				Runtime.getRuntime().exec(new String[] { "xdg-open",
						"https://github.com/Josee9988/Easy-watermarking/blob/master/README.md" });
			}
		} else {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(
						"http://github.com/https://github.com/Josee9988/Easy-watermarking/blob/master/LICENSE"));
			}
		}
	}

	@FXML
	/**
	 * license launches the default browser and looks for a URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	private void license() throws IOException, URISyntaxException {
		if (this.isUnix()) {
			if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
				Runtime.getRuntime().exec(new String[] { "xdg-open",
						"https://github.com/Josee9988/Easy-watermarking/blob/master/LICENSE" });
			}
		} else {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(
						"http://github.com/https://github.com/Josee9988/Easy-watermarking/blob/master/LICENSE"));
			}
		}
	}

	@FXML
	/**
	 * josee9988 launches the default browser and looks for a URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	private void josee9988() throws IOException, URISyntaxException {
		if (this.isUnix()) {
			if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
				Runtime.getRuntime().exec(new String[] { "xdg-open", "http://github.com/Josee9988" });
			}
		} else {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI("http://github.com/Josee9988"));
			}
		}
	}

	@FXML
	/**
	 * sourceCode launches the default browser and looks for a URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	private void sourceCode() throws IOException, URISyntaxException {
		if (this.isUnix()) {
			if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
				Runtime.getRuntime()
						.exec(new String[] { "xdg-open", "https://github.com/Josee9988/Easy-watermarking" });
			}
		} else {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI("https://github.com/Josee9988/Easy-watermarking"));
			}
		}
	}

	/**
	 * isUnix checks if the operating system is from unix
	 *
	 * @return a boolean true if the operating system if unix
	 */
	private boolean isUnix() {
		return (ImportController.OS.indexOf("nix") >= 0 || ImportController.OS.indexOf("nux") >= 0
				|| ImportController.OS.indexOf("aix") > 0);
	}

}
