

// A Java program to demonstrate adjacency
// list using HashMap and TreeSet
// representation of graphs using sets
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
 
public class Graph {

    HashMap<String, TreeSet<String> > graph;
    HashMap<String, Integer> graphHeuristicValues;
    private ArrayList<String> dictionary; //dijamin panjang semua string sama
    public Graph(ArrayList<String> dict,String dest)
    {
        graph = new HashMap<>();
        graphHeuristicValues = new HashMap<>();
        dictionary = dict;
        for (String word : dictionary) {
            graph.put(word, new TreeSet<>());
            graphHeuristicValues.put(word, mismatchCounter(word, dest));
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
        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./cache/UCSvisitednodes.dat"))){
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
        PriorityQueue<HeuristicNode> pq = new PriorityQueue<>((n1,n2) -> n1.getF() - n2.getF()); //prioritasnya adalah node dengan nilai f yang terkecil di depan
        HashMap<String,Integer> gVal = new HashMap<>(); // jarak dari start ke node ini
        HashSet<String> visited = new HashSet<>();

        for(String word: dictionary){ //inisialisasi nilai g dari semua node dengan nilai maksimum
            gVal.put(word,Integer.MAX_VALUE);
        }
        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./cache/Astarvisitednodes.dat"))){        
            gVal.put(src, 0); //jarak dari start ke dirinya sendiri adalah 0
            pq.add(new HeuristicNode(src, 0, mismatchCounter(src, target)));

            while(!pq.isEmpty()){
                HeuristicNode cur = pq.poll();
                if (visited.contains(cur.getWord())) {
                    continue; // Skip adding node if already visited
                }
                visited.add(cur.getWord());
                BW.write(cur.getWord());
                BW.newLine();
                if(cur.getWord().equals(target)){ //sudah ketemu
                    //rekonstruksi path dari source
                    ArrayList<String> path = new ArrayList<>();
                    HeuristicNode curNode = cur;

                    while(curNode != null){
                        path.add(0,curNode.getWord());
                        curNode = curNode.getParent();
                    }

                    return path;
                }

                for(String neighbor : graph.get(cur.getWord())){
                    int newGVal = gVal.get(cur.getWord()) + 1; //semua cost edgenya bisa dianggap 1 karena ini adalah problem graf unweighted

                    if(newGVal < gVal.get(neighbor)){
                        gVal.put(neighbor, newGVal);
                        int newFVal = newGVal + mismatchCounter(neighbor, target);
                        HeuristicNode neighborNode = new HeuristicNode(neighbor, newGVal, newFVal);
                        neighborNode.setParent(cur);
                        pq.add(neighborNode);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>(); //jawabannya tidak ada karena pathnya tidak ditemukan
    }

    public ArrayList<String> GBFS(String src, String target){
        PriorityQueue<HeuristicNode> pq = new PriorityQueue<>((n1,n2) -> n1.getF() - n2.getF()); //prioritasnya adalah node dengan nilai f yang terkecil di depan
        HashSet<String> visited = new HashSet<>();
        HashMap<String, HeuristicNode> cameFrom = new HashMap<>();
        for (String word : dictionary) {
          cameFrom.put(word, null); // Set predecessor to null initially
        }

        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./cache/GBFSvisitednodes.dat"))){        
            pq.add(new HeuristicNode(src, 0, mismatchCounter(src, target)));
            
            while(!pq.isEmpty()){
                HeuristicNode cur = pq.poll();
                if (visited.contains(cur.getWord())) {
                    continue; // Skip adding node if already visited
                }
                visited.add(cur.getWord());
                BW.write(cur.getWord());
                BW.newLine();
                if(cur.getWord().equals(target)){ //sudah ketemu
                    //rekonstruksi path dari source
                    ArrayList<String> path = new ArrayList<>();
                    HeuristicNode curNode = cur;

                    while(curNode != null){
                        path.add(0,curNode.getWord());
                        curNode = curNode.getParent();
                    }

                    return path;
                }

                for(String neighbor : graph.get(cur.getWord())){
                    HeuristicNode neighborNode = new HeuristicNode(neighbor, 0, mismatchCounter(neighbor, target));
                    neighborNode.setParent(cur);
                    pq.add(neighborNode);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public void printGraphDebug() {
        for (String word : graph.keySet()) {
          System.out.println("Adjacency list of vertex " + word);
          Iterator<String> set = graph.get(word).iterator();
      
          while (set.hasNext()){
            String neighbor = set.next();
            System.out.print(neighbor + " ("+graphHeuristicValues.get(neighbor)+") ");
          }
            
      
          System.out.println();
          System.out.println();
        }
      }
}
 
class HeuristicNode {
    private String word;
    private int g; // jarak dari start ke node ini
    private int f; // f = g + nilai heuristik
    private HeuristicNode parent;
    public HeuristicNode(String word, int g, int f) {
        this.word = word;
        this.g = g;
        this.f = f;
        this.parent = null;
    }

    public int getG(){
        return g;
    }

    public int getF(){
        return f;
    }

    public String getWord(){
        return word;
    }

    public HeuristicNode getParent(){
        return parent;
    }

    public void setParent(HeuristicNode parent){
        this.parent = parent;
    }
}