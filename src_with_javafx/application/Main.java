package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * Main class that initiates and runs the GUI
 * @author Amy Wang, Junhao Shen
 */
public class Main extends Application {
	/* Creates stage and starts GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Project3.fxml"));
			Scene scene = new Scene(root,625,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Transaction Management System");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main function for Main.java to run GUI
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
