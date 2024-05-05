

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.File;
import javafx.scene.image.Image;
public class Main extends Application {

  @Override
  public void start(Stage stage) { //buka layar landing
    
    try{
        Parent root = FXMLLoader.load(getClass().getResource("landscene.fxml")); //buka landscene.fxml (layar landing)
        //setup scene landing
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
  public void stop(){ //perintah di eksekusi jika program ditutup
    File dir = new File("./src/cache");
    //hapus semua file yang berada di dalam dolder src/cache kecuali .gitkeep 
    DictionaryController.deleteFilesInDirectory(dir);
  }
  public static void main(String[] args) {
    launch(args);
  }

  
}
