/**
 * @author Jose_Gracia_Berenguer
 * @version Jul 21, 2019
 * @param args Receives the arguments of the program.
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
 * Class that initialices the view of import.fxml and contains its containers
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
	 * importPicsAction calls the method Choosefile and initialices the FileChooser
	 * with a title and a boolean for not only selecting png's
	 *
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	public void importPicsAction() throws FileNotFoundException {
		this.ChooseFile("Choose your images to watermark them: ", false);
	}

	@FXML
	/**
	 * importWaterAction calls the method Choosefile and initialices the FileChooser
	 * with a title and a boolean for only selecting png's
	 *
	 * @throws FileNotFoundException if the images are not found in your file system
	 */
	public void importWaterAction() throws FileNotFoundException {
		this.ChooseFile("Choose your watermarker: ", true);

	}

	/**
	 * fillListWater receives a string with the path of the image and set it to the
	 * listview with the watermark
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
	 * fillList receives an arraylist with all the files with paths of the images
	 * and sets it to the listview
	 *
	 * @param list arraylist with all the files with paths of the images
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
	 * ChooseFile launchs JFileChooser that will prompt the user for images
	 *
	 * @throws FileNotFoundException if the image was not found in your file system.
	 */
	private void ChooseFile(String dialogTitle, boolean OnlyPng) throws FileNotFoundException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle(dialogTitle);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter;
		if (OnlyPng) {
			filter = new FileNameExtensionFilter("PNG files", "png");
			jfc.setMultiSelectionEnabled(false);
		} else {
			jfc.setMultiSelectionEnabled(true);
			filter = new FileNameExtensionFilter("PNG, jpeg or jpg Files", "png", "jpg", "jpeg");

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
	public void watermarkAllAction() throws IOException {
		if (this.vieww.getItems().size() == 0) {
			this.errorLabel.setText("You must import your images.");
		} else if (this.viewWater.getItems().size() == 0) {
			this.errorLabel.setText("You must import your watermark");
		} else {

			double startTime, endTime;
			startTime = System.nanoTime();
			ImageController imageController = new ImageController(ImportController.OS, this.vieww.getItems(),
					this.viewWater.getItems().get(0).getText());
			endTime = System.nanoTime() - startTime;

			// this.todayDate.setText((endTime / 1000000) + " miliseconds");
			if (imageController.CheckDifferentFolder().size() > 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Images created in: " + (endTime / 1000000) + " miliseconds");
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
				alert.setHeaderText("Images created in: " + (endTime / 1000000) + " miliseconds");
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
	 * get the selected item from the JFXlistview and removes it when the button is
	 * pressed.
	 */
	public void removeItem() {
		this.vieww.getItems().remove(this.vieww.getSelectionModel().getSelectedIndex());
		if (this.vieww.getItems().size() == 0) {
			this.remove.setDisable(true);
		}
	}

	@FXML
	/**
	 * quit directly exits the program.
	 */
	public void quit() {
		System.exit(0);
	}

	@FXML
	/**
	 * about launchs the default browser and searchs for an URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	public void about() throws IOException, URISyntaxException {

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
	 * license launchs the default browser and searchs for an URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	public void license() throws IOException, URISyntaxException {
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
	 * josee9988 launchs the default browser and searchs for an URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	public void josee9988() throws IOException, URISyntaxException {
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
	 * sourceCode launchs the default browser and searchs for an URI
	 *
	 * @throws IOException        if there has been an IO exception, executing the
	 *                            browser
	 * @throws URISyntaxException if there has been a problem with the given URI
	 */
	public void sourceCode() throws IOException, URISyntaxException {
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
	public boolean isUnix() {
		return (ImportController.OS.indexOf("nix") >= 0 || ImportController.OS.indexOf("nux") >= 0
				|| ImportController.OS.indexOf("aix") > 0);
	}

}
