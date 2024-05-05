

// A Java program to demonstrate adjacency
// list using HashMap and TreeSet
// representation of graphs using sets
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
 
public class Graph {

    HashMap<String, TreeSet<String> > graph; //Representasi graf dalam bentuk adjacency list
    HashMap<String, Integer> graphHeuristicValues; //nilai heuristic untuk A* dan GBFS
    private ArrayList<String> dictionary; //dijamin panjang semua string sama
    public Graph(ArrayList<String> dict,String dest) //KONSTRUKTOR
    {
        graph = new HashMap<>();
        graphHeuristicValues = new HashMap<>();
        dictionary = dict; 
        for (String word : dictionary) {
            graph.put(word, new TreeSet<>()); //taruh semua kata ke graf (belum disambung)
            graphHeuristicValues.put(word, mismatchCounter(word, dest)); //heuristik yang digunakan di graf ini adalah banyak huruf yang BERBEDA dari suatu kata dengan kata akhir
        }
        constructGraph(); //sambungkan graf
    }
    public void addEdge(String src, String dest) //method untuk menyambung antar dua node
    {
        //perhatikan bahwa problem word ladder adalah graf UNWEIGHTED dan UNDIRECTED
        graph.get(src).add(dest);
        graph.get(dest).add(src);
    }
    public void deleteGraph(){ //hapus graf
        graph = new HashMap<>();
        dictionary = new ArrayList<>();
    }
    private void constructGraph() { //sambungkan graf
        for (String word1 : dictionary) {
            for (String word2 : dictionary) {
                if (word1 != word2 && mismatchCounter(word1, word2) == 1) {
                    //sambungkan graf jika kata tidak sama dan banyak huruf yang berbeda antara KATA 1 dan KATA 2 adalah 1
                    addEdge(word1, word2);
                }
            }
        }
    }

    private int mismatchCounter(String s1, String s2){
        //ini adalah fungsi heuristik yang digunakan untuk A* dan GBFS
        //fungsi ini menghitung banyak huruf yang berbeda antara s1 dan s2
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
        HashMap<String,String> parentMap = new HashMap<>(); //map yang menunjukkan relasi (A dan B, B adalah parent dari A)
        ArrayList<String> path = new ArrayList<>();
        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/visitednodes.dat"))){ //catat ke visitednodes.dat di folder cache
            visitedNodes.add(src); //tandai sudah dikunjungi
            nodeQueue.add(src); //masukkan ke queue
            parentMap.put(src, null); //node awal tidak punya parent
            String top = new String();
            while(!nodeQueue.isEmpty()){//selama queue tidak kosong
                top = nodeQueue.poll(); //dapatkan node yang paling depan di queue
                BW.write(top); //tulis ke visitednodes.dat
                BW.newLine(); //tulis newline ke visitednodes.dat
                if(top.equals(target)){ //hentikan pencarian jika elemen terdepan queue = kata akhir
                    break;
                }
                TreeSet<String> neighbors = graph.get(top); //dapatkan semua neighbor dari node terdepan queue
                for (String neighbor : neighbors) {
                    if (!visitedNodes.contains(neighbor)) { //jika neighbor yang diperiksa belum dikunjungi
                        visitedNodes.add(neighbor); //tandai sudah dikunjungi
                        nodeQueue.add(neighbor);//masukkan ke queue
                        parentMap.put(neighbor,top); //node terdepan saat ini adalah parent dari neighbor
                    }
                }
            }
            
            if(top.equals(target)){ //jika path ditemukan
                //rekonstruksi path yang dicari dari belakang (target)
                String cur = target;
                while (cur != null) {
                    path.add(0, cur); //tambahkan node yang ditandai sekarang ke path dari depan
                    cur = parentMap.get(cur); //pindahkan tanda ke parentnya
                }
            }
        } catch (Exception e){
            e.getStackTrace();
        }
        return path; //kembalikan pathnya
    }

    public ArrayList<String> Astar(String src, String target){
        //Algoritma A*
        PriorityQueue<HeuristicNode> pq = new PriorityQueue<>((n1,n2) -> n1.getF() - n2.getF()); //prioritasnya adalah node dengan nilai f yang terkecil di depan
        HashMap<String,Integer> gVal = new HashMap<>(); // g adalah cost:jarak dari node saat ini ke start
        HashSet<String> visited = new HashSet<>();

        for(String word: dictionary){ //inisialisasi nilai g dari semua node dengan nilai maksimum
            gVal.put(word,Integer.MAX_VALUE);
        }
        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/visitednodes.dat"))){        
            gVal.put(src, 0); //jarak dari start ke dirinya sendiri adalah 0
            pq.add(new HeuristicNode(src, 0, mismatchCounter(src, target))); //buat node baru dengan

            while(!pq.isEmpty()){
                HeuristicNode cur = pq.poll();//dapatkan node yang terletak di depan priority queue
                if (visited.contains(cur.getWord())) { //lewati node saat ini jika sudah pernah dikunjungi
                    continue;
                }
                visited.add(cur.getWord());//tandai sudah dikunjungi
                BW.write(cur.getWord());//tulis ke visitednodes.dat
                BW.newLine();
                if(cur.getWord().equals(target)){ //jika sudah ketemu
                    //rekonstruksi path dari source dilakukan secar mundur dari target
                    ArrayList<String> path = new ArrayList<>();
                    HeuristicNode curNode = cur;

                    while(curNode != null){
                        path.add(0,curNode.getWord());//tambahkan node dari depan
                        curNode = curNode.getParent(); //lanjut ke parent dari node saat ini
                    }

                    return path;//kembalikan path
                }

                for(String neighbor : graph.get(cur.getWord())){
                    int newGVal = gVal.get(cur.getWord()) + 1; //semua cost edgenya bisa dianggap 1 karena ini adalah problem graf unweighted, jadi cukup tambahkan g(x) baru dengan 1

                    if(newGVal < gVal.get(neighbor)){ //jika cost yang baru lebih rendah dari cost tetangganya
                        gVal.put(neighbor, newGVal);//ganti nilai g dari tetangganya saat ini dengan nilai g yang baru dihitung
                        int newFVal = newGVal + mismatchCounter(neighbor, target); //F(x) = G(x) + H(x) H(x) adalah nilai heuristik
                        //nilai heuristik yang digunakan adalah banyak huruf yang berbeda dari neighbor saat ini dengan string tujuan
                        HeuristicNode neighborNode = new HeuristicNode(neighbor, newGVal, newFVal); //buat node baru dengan nama neighbor saat ini dan dengan nilai G baru dan F baru
                        neighborNode.setParent(cur); //parent dari neighbor node adalah node saat ini
                        pq.add(neighborNode); //masukkan node tetangga ke priority queue
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>(); //jawabannya tidak ada karena pathnya tidak ditemukan
    }

    public ArrayList<String> GBFS(String src, String target){
        //Nilai F dari GBFS hanyalah nilai heuristik node yang bersangkutan dan G pasti sama dengan 0
        //Nilai heuristik adalah banyak huruf yang berbeda dari kata suatu node dengan node tujuan
        PriorityQueue<HeuristicNode> pq = new PriorityQueue<>((n1,n2) -> n1.getF() - n2.getF()); //prioritasnya adalah node dengan nilai f yang terkecil di depan
        HashSet<String> visited = new HashSet<>();


        try(BufferedWriter BW = new BufferedWriter(new FileWriter("./src/cache/visitednodes.dat"))){//catat semua node yang dikunjungi
            pq.add(new HeuristicNode(src, 0, mismatchCounter(src, target)));//tambahkan node baru ke priority queue
            while(!pq.isEmpty()){
                HeuristicNode cur = pq.poll();// dapatkan node paling depan
                if (visited.contains(cur.getWord())) { //lewati node paling depan sekarang jika sudah pernah dikunjungi
                    continue;
                }
                visited.add(cur.getWord()); //tandai node saat ini sudah dikunjungi
                BW.write(cur.getWord()); //tulis node saat ini ke visitednodes.dat
                BW.newLine();
                if(cur.getWord().equals(target)){ //sudah ketemu
                    //rekonstruksi path dari source
                    ArrayList<String> path = new ArrayList<>();
                    HeuristicNode curNode = cur;

                    while(curNode != null){
                        path.add(0,curNode.getWord()); //tambahkan node dari depan
                        curNode = curNode.getParent();  //lanjut ke parent dari node saat ini
                    }

                    return path; //kembalikan path saat ini
                }

                for(String neighbor : graph.get(cur.getWord())){ //cek semua tetangga dari node saat ini
                    HeuristicNode neighborNode = new HeuristicNode(neighbor, 0, mismatchCounter(neighbor, target));
                    //buat node baru dengan g = 0 (karena tidak digunakan di GBF)
                    neighborNode.setParent(cur);
                    //parent dari tetangga saat ini adalah node saat ini
                    pq.add(neighborNode); //masukkan node tetangga ke priority queue
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public void printGraphDebug() { //hanya untuk debug, print semua adjacency list
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
    private String word; //kata yang disimpan oleh node
    private int g; // jarak dari start ke node ini
    private int f; // f = g + nilai heuristik
    private HeuristicNode parent; //parent dari node ini
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