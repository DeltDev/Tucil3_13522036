

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.File;
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

  @Override
  public void stop(){
    System.out.println("Window ditutup");
    File dir = new File("./src/cache");
    DictionaryController.deleteFilesInDirectory(dir);
  }
  public static void main(String[] args) {
    launch(args);
  }

  
}
