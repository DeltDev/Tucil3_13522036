

// A Java program to demonstrate adjacency
// list using HashMap and TreeSet
// representation of graphs using sets
import java.util.*;
 
public class Graph {

    HashMap<String, TreeSet<String> > graph;
    private ArrayList<String> dictionary; //dijamin panjang semua string sama
    public Graph(ArrayList<String> dict)
    {
        graph = new HashMap<>();
        dictionary = dict;
        for (String word : dictionary) {
            graph.put(word, new TreeSet<>());
        }
        constructGraph();
    }
    public void addEdge(String src, String dest)
    {
        graph.get(src).add(dest);
        graph.get(dest).add(src);
    }
    public void deleteGraph(){
        graph = new HashMap<>();
        dictionary = new ArrayList<>();
    }
    private void constructGraph() {
        for (String word1 : dictionary) {
            for (String word2 : dictionary) {
                if (word1 != word2 && mismatchCounter(word1, word2) == 1) {
                    addEdge(word1, word2);
                }
            }
        }
    }

    private int mismatchCounter(String s1, String s2){
        int mismatch = 0;
        for(int i = 0; i<s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i)){
                mismatch++;
            }
        }
        return mismatch;
    }
    public void printGraph() {
        for (String word : graph.keySet()) {
          System.out.println("Adjacency list of vertex " + word);
          Iterator set = graph.get(word).iterator();
      
          while (set.hasNext())
            System.out.print(set.next() + " ");
      
          System.out.println();
          System.out.println();
        }
      }
}
 
