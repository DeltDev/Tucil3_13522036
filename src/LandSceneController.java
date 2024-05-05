import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
public class LandSceneController { //Clas untuk mengendalikan semua hal yang ada di scene landing
    @FXML
    private Scene scene;
    private Stage stage;
    private Parent root;
    public void GoToMainScene(ActionEvent e) throws IOException{ //controller tombol mulai ke scene utama/input kata
        DictionaryController.SplitDictionary(); //pisahkan src/dictionary/dictionary.txt berdasarkan panjang hurufnya ke file .dat masing-masing
        root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}