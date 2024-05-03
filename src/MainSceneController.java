import java.io.BufferedReader;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.FileReader;

public class MainSceneController implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;
    private int execTime;
    private int nodesVisited;
    private String[] MethodList = {"UCS","A*","Greedy Best First Search"};
    @FXML
    private ChoiceBox<String> MethodChoiceBox;
    @FXML
    private Label ErrorMessage;
    @FXML TextField StartingWord,EndingWord;
    public void GoToLandScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        root = FXMLLoader.load(getClass().getResource("landscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Mulai");
    }
    
    public void GoToResultScene(ActionEvent e) throws IOException{ //controller tombol mulai di scene utama
        String chosenMethod = MethodChoiceBox.getValue();
        String startingWord = StartingWord.getText().toLowerCase();
        String endingWord = EndingWord.getText().toLowerCase();
        System.out.println(startingWord);
        System.out.println(endingWord);
        if(StartingWord.getText().isEmpty()|| EndingWord.getText().isEmpty()){
            ErrorMessage.setText("Anda belum menginput kata! Silakan menginput kata.");
        } else if(startingWord.length() != endingWord.length()){
            ErrorMessage.setText("Panjang kata awal dan akhir berbeda! Harap input dua kata yang panjangnya sama.");
        } else if(chosenMethod == null){
            ErrorMessage.setText("Anda belum memilih metode pencarian! Silakan pilih metode pencarian");
        } else if(!ValidateWord(startingWord, startingWord.length()) || !ValidateWord(endingWord, endingWord.length())){
            ErrorMessage.setText("Kata yang dimasukkan tidak ada di dalam kamus/daftar kata program ini!\nSilakan buka daftar kata untuk melihat daftar kata yang valid.");
        }else {
            System.out.println(chosenMethod);
            execTime = 6969;
            nodesVisited = 420;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resultscene.fxml"));
            root = loader.load();
            ResultSceneController resController = loader.getController();
            resController.displayMethod(chosenMethod);
            resController.displayExecTime(execTime);
            resController.displayNodesVisited(nodesVisited);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Mulai");
        }
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
        ErrorMessage.setText("");
    }

    public boolean ValidateWord(String check, int len){ //periksa apakah kata ada di dalam dictionary
        
        try (BufferedReader BR = new BufferedReader(new FileReader("./src/dictionary/dictionary.txt"))){
            String line;
            while ((line = BR.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if(line.equals(check)){
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
