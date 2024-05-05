
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.*;
import javafx.scene.paint.Color;
public class ResultSceneController {
    
    @FXML
    Label SearchingMethod,ExecTime,NodesVisited,GBFSWarning,PathStart,PathEnd,PathLength;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane anchorPane;
    public void displaySummary(String start, String end, int length){
        PathStart.setText(start);
        PathEnd.setText(end);
        PathLength.setText(Integer.toString(length));
    }
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
    public void displayWarning(String str){
        GBFSWarning.setText(str);
    }
    public void displayPath(ArrayList<String> path){
        double LayoutYVal = 200;
        if(path.size() == 0){//path tidak ditemukan
            Label label = new Label("Path tidak ditemukan!");
            label.setFont(new Font("Segoe UI",36));
            label.setLayoutX(500);
            label.setLayoutY(LayoutYVal);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setTextFill(Color.RED);
            anchorPane.getChildren().add(label);
        } else {
            for (String word : path) {
                Label label = new Label(word);
                label.setFont(new Font("Segoe UI",36));
                label.setLayoutX(550);
                label.setLayoutY(LayoutYVal);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setTextFill(Color.color(17.0/255.0,128.0/255.0,13.0/255.0));
                LayoutYVal += 60;
                anchorPane.getChildren().add(label);
            }
            anchorPane.setPrefHeight(LayoutYVal + 60);
        }
        
        
    }
    private Scene scene;
    private Stage stage;
    private Parent root;
    public void GoToMainScene(ActionEvent e) throws IOException{ //controller tombol kembali ke scene utama
        root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
