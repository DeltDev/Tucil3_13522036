import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;

public class DictionarySplitter {
   
    public static void SplitDictionary(){
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
                BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/Length"+Integer.toString(key)+".txt"));
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
    
}
