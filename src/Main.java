

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
public class Main extends Application {

  @Override
  public void start(Stage stage) {

    try{
        Parent root = FXMLLoader.load(getClass().getResource("landscene.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
    
  }

  public static void main(String[] args) {
    launch(args);
  }
}
