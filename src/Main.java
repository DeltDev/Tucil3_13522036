

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.File;
import javafx.scene.image.Image;
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    
    try{
        Parent root = FXMLLoader.load(getClass().getResource("landscene.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Word Ladder Solver");
        Image icon = new Image("Word Ladder Logo.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
    } catch (Exception e){
        e.printStackTrace();
    }
    
  }

  @Override
  public void stop(){
    File dir = new File("./src/cache");
    DictionaryController.deleteFilesInDirectory(dir);
  }
  public static void main(String[] args) {
    launch(args);
  }

  
}
