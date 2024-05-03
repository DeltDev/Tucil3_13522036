import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class GraphUnitTest {
    static public void main(String args[]){
        
        try (BufferedReader BR = new BufferedReader(new FileReader("./cache/Length"+Integer.toString(5)+".dat"));){
            String line;
            ArrayList<String> arr = new ArrayList<String>();
            while ((line = BR.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                arr.add(line);
            }
            Graph graph = new Graph(arr);
            graph.printGraph();
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
}
