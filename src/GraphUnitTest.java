import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class GraphUnitTest {
    static public void main(String args[]){
        
        try (BufferedReader BR = new BufferedReader(new FileReader("./Length"+Integer.toString(4)+".dat"));){
            String line;
            ArrayList<String> arr = new ArrayList<String>();
            while ((line = BR.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                arr.add(line);
            }
            Graph graph = new Graph(arr,"fart");
            graph.printGraphDebug();
            System.out.println(graph.UCS("mozz", "jump"));
            System.out.println(graph.Astar("mozz", "jump"));
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
}
