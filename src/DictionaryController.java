import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;

public class DictionaryController {
   
    public static void SplitDictionary(){
        //Fungsi ini berfungsi untuk membuat cache dictionary yang dipisahkan berdasarkan panjang/banyak hurufnya ke file .dat nya masing-masing
        HashMap<Integer, ArrayList<String>> dictLength = new HashMap<>();
    
        try {
            BufferedReader BR = new BufferedReader(new FileReader("./src/dictionary/dictionary.txt"));
            String line;
            while ((line = BR.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                int len = line.length();
                ArrayList<String> words = dictLength.get(len);
                if (words == null) {
                    words = new ArrayList<>();
                    dictLength.put(len, words);
                }
                words.add(line);
            }
    
            BR.close();

            for (Integer key : dictLength.keySet()) {
                ArrayList<String> words = dictLength.get(key);
                BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/Length"+Integer.toString(key)+".dat"));
                for(int i = 0; i<words.size(); i++){
                    BW.write(words.get(i));
                    if(i != words.size() -1){
                        BW.newLine();
                    }
                }
                BW.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteFilesInDirectory(File directory) {
        //menghapus semua file yang berada di src/cache kecuali .gitkeep (agar folder cache tidak hilang di repository)
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && !file.getName().equals(".gitkeep")) {
                    file.delete();
                }
            }
        }
    }
}
