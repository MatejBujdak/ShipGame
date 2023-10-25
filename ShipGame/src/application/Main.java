package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
		Game g = new Game(1024, 500, "images/pozadie.png", 10, root);
		root.getChildren().add(g);
		

		Scene scene = new Scene(root, 1024, 500);
		stage.setScene(scene);
		stage.show();

		MyTimer t = new MyTimer(g);
		t.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
