/**
 * @author Jose_Gracia_Berenguer
 * @version Jul 21, 2019
 * @param args Receives the arguments of the program.
 */
package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jose Gracia berenguer
 */
public class EasyWatermark extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		try {
			// ClassLoader.getSystemResource("com/xyz/resources/camera.png");
			// ImageIcon iChing = new
			// ImageIcon("C:\\Users\\RrezartP\\Documents\\NetBeansProjects\\Inventari\\src\\Gui\\icon\\report-go-icon.png");
			root = FXMLLoader.load(this.getClass().getResource("/view/import.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(true);
			// Image icon = new
			// Image(this.getClass().getResourceAsStream("/view/jc-favicon.png"));
			// primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Easy Watermark - Import your pictures + the watermark");
			primaryStage.show();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}

}
