/**
 * @author Jose_Gracia_Berenguer
 * @version Jul 21, 2019
 * @param args Receives the arguments of the program.
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * EasyWatermark main class that initializes import.fxml
 *
 * @author Jose Gracia Berenguer
 */
public class EasyWatermark extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		try {
			root = FXMLLoader.load(this.getClass().getResource("/view/import.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(true);
			Image icon = new Image(this.getClass().getResourceAsStream("/view/icons/MainIcon.png"));
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Easy Watermark - Import your pictures and the watermark");
			primaryStage.show();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}

}
