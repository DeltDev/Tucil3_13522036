import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.net.URL;
public class MainSceneController implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;
    private String[] MethodList = {"UCS","A*","Greedy Best First Search"};
    @FXML
    private ChoiceBox<String> MethodChoiceBox;
    public void GoToLandScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        root = FXMLLoader.load(getClass().getResource("landscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Mulai");
    }
    
    public void GoToResultScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        root = FXMLLoader.load(getClass().getResource("resultscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Mulai");
    }

    public void GoToWordListScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        root = FXMLLoader.load(getClass().getResource("wordlistscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Mulai");
    }

    @Override
    public void initialize(URL arg1, ResourceBundle arg2){
        MethodChoiceBox.getItems().addAll(MethodList);
    }
}
