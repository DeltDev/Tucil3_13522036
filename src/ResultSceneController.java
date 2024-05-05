
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.*;
public class ResultSceneController {
    
    @FXML
    Label SearchingMethod,ExecTime,NodesVisited;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane anchorPane;
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

    public void displayPath(ArrayList<String> path){
        for (String word : path) {
            Label label = new Label(word);
            // Optional: Set preferred height for spacing
            // label.setPrefHeight(20);  // Adjust spacing as needed
            anchorPane.getChildren().add(label);
        }
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
