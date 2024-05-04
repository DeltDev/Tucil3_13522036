
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class ResultSceneController {
    
    @FXML
    Label SearchingMethod,ExecTime,NodesVisited;
    
    public void displayMethod(String chosenMethod){
        SearchingMethod.setText(chosenMethod);
    }

    public void displayExecTime(long execTime){
        String output = Long.toString(execTime) + " ms";
        ExecTime.setText(output);
    }

    public void displayNodesVisited(int nodesVisited){
        String output = Integer.toString(nodesVisited);
        NodesVisited.setText(output);
    }
    private Scene scene;
    private Stage stage;
    private Parent root;
    public void GoToMainScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Mulai");
    }
}
