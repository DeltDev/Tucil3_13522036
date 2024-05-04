

// A Java program to demonstrate adjacency
// list using HashMap and TreeSet
// representation of graphs using sets
import java.io.BufferedWriter;
import java.io.FileWriter;
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

    public ArrayList<String> UCS(String src, String target){
        //NOTE: Graph ini memiliki cost yang sama di semua edge (graph word ladder adalah graph UNWEIGHTED)
        //karena semua edge memiliki cost yang sama, maka UCS bekerja sama persis dengan Breadth first search
        //BFS adalah kasus khusus dari UCS di mana semua edge memiliki cost yang sama

        //BFS
        Queue<String> nodeQueue = new LinkedList<>();
        ArrayList<String> visitedNodes = new ArrayList<>();
        HashMap<String,String> parentMap = new HashMap<>();
        ArrayList<String> path = new ArrayList<>();
        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/visitednodes.dat"))){
            visitedNodes.add(src);
            nodeQueue.add(src);
            parentMap.put(src, null);
            String top = new String();
            while(!nodeQueue.isEmpty()){
                top = nodeQueue.poll();
                BW.write(top);
                BW.newLine();
                if(top.equals(target)){
                    break;
                }
                TreeSet<String> neighbors = graph.get(top);
                for (String neighbor : neighbors) {
                    if (!visitedNodes.contains(neighbor)) {
                        visitedNodes.add(neighbor);
                        nodeQueue.add(neighbor);
                        parentMap.put(neighbor,top);
                    }
                }
            }
            
            if(top.equals(target)){
                
                String cur = target;
                while (cur != null) {
                    path.add(0, cur); // Prepend to maintain order (source -> target)
                    cur = parentMap.get(cur);
                }
            }
        } catch (Exception e){
            e.getStackTrace();
        }
        return path;
    }

    public ArrayList<String> Astar(String src, String target){
        return new ArrayList<>();
    }
    public void printGraphDebug() {
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
 
