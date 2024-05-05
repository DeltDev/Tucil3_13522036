import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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

public class MainSceneController implements Initializable { //Class untuk mengendalikan semua hal di scene utama/input kata
    private Scene scene;
    private Stage stage;
    private Parent root;
    private long execTime,startTime,endTime; //untuk menghitung waktu eksekusi program
    private int nodesVisited; //untuk menghitung banyak node yang dikunjungi saat proses pencarian
    private String[] MethodList = {"UCS","A*","Greedy Best First Search"}; //daftar algortima yang digunakan
    @FXML
    private ChoiceBox<String> MethodChoiceBox;
    @FXML
    private Label ErrorMessage;
    @FXML TextField StartingWord,EndingWord; //textfield untuk menerima input kata asal dan tujuan
    public void GoToLandScene(ActionEvent e) throws IOException{ //controller tombol kembali ke scene landing
        root = FXMLLoader.load(getClass().getResource("landscene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void GoToResultScene(ActionEvent e) throws IOException{ //controller tombol untuk pindah ke scene result
        String chosenMethod = MethodChoiceBox.getValue(); //dapatkan algoritma yang dipilih dari dropdown
        String startingWord = StartingWord.getText().toLowerCase(); //dapatkan kata yang berada di posisi start (disimpan sebagai huruf kecil semua agar dapat melakukan input case insensitive dan mencocokan dengan dictionary menjadi lebih mudah)
        String endingWord = EndingWord.getText().toLowerCase(); //dapatkan kata yang berada di posisi akhir (disimpan sebagai huruf kecil semua agar dapat melakukan input case insensitive dan mencocokan dengan dictionary menjadi lebih mudah)
        if(StartingWord.getText().isEmpty()|| EndingWord.getText().isEmpty()){ //validasi user tidak menginput kata
            ErrorMessage.setText("Anda belum menginput kata! Silakan menginput kata.");
        } else if(startingWord.length() != endingWord.length()){ //validasi panjang kedua kata, kedua kata tidak boleh memiliki panjang yang berbeda karena problem ini hanya boleh mengubah huruf, tidak boleh menambah atau mengurangi huruf
            ErrorMessage.setText("Panjang kata awal dan akhir berbeda! Harap input dua kata yang panjangnya sama.");
        } else if(chosenMethod == null){ //validasi user belum memilih metode pencarian
            ErrorMessage.setText("Anda belum memilih metode pencarian! Silakan pilih metode pencarian");
        } else if(!ValidateWord(startingWord, startingWord.length()) || !ValidateWord(endingWord, endingWord.length())){ //validasi kata yang tidak ada di dalam kamus
            ErrorMessage.setText("Kata yang dimasukkan tidak ada di dalam kamus/daftar kata program ini!\nSilakan buka daftar kata untuk melihat daftar kata yang valid.");
        }else { //kasus validasi lolos semua
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resultscene.fxml")); //load scene hasil pencarian
            root = loader.load();
            ResultSceneController resController = loader.getController(); //dapatkan controllernya
            nodesVisited = 0;
            startTime = System.currentTimeMillis();// hitung waktu mulai
            ArrayList<String> dictionary = createDictionary(startingWord.length()); //dapatkan semua kata yang panjangnya sama dengan kedua kata input, masukkan ke array dictionary
            Graph graph = new Graph(dictionary,endingWord); //buat graf dari dictionary tersebut
            ArrayList<String> path = new ArrayList<>();
            if(chosenMethod == "UCS"){ //metode yang dipilih adalah UCS
                path = graph.UCS(startingWord,endingWord);
                resController.displayWarning("");
            } else if(chosenMethod == "A*"){ //metode yang dipilih adalah A*
                path = graph.Astar(startingWord,endingWord);
                resController.displayWarning("");
            } else if(chosenMethod == "Greedy Best First Search"){ //metode yang dipilih adalah GBFS
                path = graph.GBFS(startingWord,endingWord);
                //beri peringatan bahwa GBFS mungkin tidak memberikan path terpendek
                resController.displayWarning("PERINGATAN: Karena Anda memilih metode Greedy Best First Search, path ini mungkin bukan path terpendek. Gunakan A* atau UCS jika Anda ingin path terpendek.");
            }
            
            //hitung banyak node yang dikunjungi dari cache visitednodes.dat
            try(BufferedReader BR = new BufferedReader(new FileReader("./src/cache/visitednodes.dat"))){
                while (BR.readLine() != null) {
                    nodesVisited++;
                }
            } catch (Exception ex){
                ex.getStackTrace();
            }
            endTime = System.currentTimeMillis(); //stop waktu
            execTime = endTime - startTime;
            
            
            resController.displayMethod(chosenMethod); //tampilkan metode yang dipilih ke scene hasil
            resController.displayExecTime(execTime); //tampilkan waktu eksekusi program ke scene hasil
            resController.displayNodesVisited(nodesVisited); //tampilkan banyak node yang dikunjungi ke scene hasil
            resController.displayPath(path); //tampilkan path yang ditemukan ke scene hasil
            resController.displaySummary(startingWord, endingWord, path.size()); //tampilkan data rangkuman penggunaan yang dilakukan ke scene hasil
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL arg1, ResourceBundle arg2){ //inisialisasi error message dan dropdown algoritma yang akan dipilih
        MethodChoiceBox.getItems().addAll(MethodList);
        ErrorMessage.setText("");
    }

    public boolean ValidateWord(String check, int len){ //periksa apakah kata yang diperiksa ada di dalam dictionary
        //cukup periksa semua kata yang panjangnya sama dengan check
        try (BufferedReader BR = new BufferedReader(new FileReader("./src/cache/Length"+Integer.toString(len)+".dat"));){
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

    private ArrayList<String> createDictionary(int len){
        //membuat dictionary dari semua kata yang panjangnya sama dengan kedua string yang diinput
        try (BufferedReader BR = new BufferedReader(new FileReader("./src/cache/Length"+Integer.toString(len)+".dat"));){
            String line;
            ArrayList<String> arr = new ArrayList<String>();
            while ((line = BR.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                arr.add(line);
            }
            return arr;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
