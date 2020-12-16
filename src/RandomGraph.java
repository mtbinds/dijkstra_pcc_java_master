import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Dijkstra.Element;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSourceDGS;
import java.io.*;

public class RandomGraph {


    public static void main(String[] args) throws IOException {

        //Création de graphe
    Graph graph = new SingleGraph("Random");

        //Le générateur aléatoire de Graphe
    Generator gen = new RandomGenerator(100);

    gen.addSink(graph);

        //Début de génération un graphes de 1002 noeuds commençant par la génération du  noeud 0 et terminant par générer le noeud 1002

        gen.begin();

        for(int i=0; i<1000; i++)
            gen.nextEvents();

        //Fin de génération de Graphe
        gen.end();

        //Export de graphe sous format DGS

        //new FileSinkDGS().writeAll(graph,"graphes.dgs");

        //Affichage de Graphe

        //graph.display();


        //---------------------------------Algorithme de Dijkstra (Version Graph Stream)---------------------------------------------------

        //Création de graphe à partir de graphe généré par RandomGenerator (le fichier graphes.dgs)

        Graph DJK = new SingleGraph("dijkstra");

        //Lecture du fichier "graphes.dgs"

        BufferedReader lecteurAvecBuffer = null;
        String ligne;
        String ch="";

        try
        {
            FileReader fr=new FileReader("graphes.dgs");
            lecteurAvecBuffer = new BufferedReader(fr);
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture");
        }
        while ((ligne = lecteurAvecBuffer.readLine()) != null)
            ch=ch+ligne+"\n";
        lecteurAvecBuffer.close();

        System.out.println(ch);

        //insersion de noeuds et arcs et poids dans le graphe "DJK" à partir de fichier "graphes.dgs"

        ByteArrayInputStream bs = new ByteArrayInputStream(ch.getBytes());
        FileSourceDGS source = new FileSourceDGS();
        source.addSink(DJK);
        source.readAll(bs);


        //Application de l'algorithme de Dijkstra sur le graphe (DJK)
        Dijkstra dijkstra = new Dijkstra(Element.NODE,null,null);
        dijkstra.init(DJK);

        //La source de graphe est le noeud 0
        dijkstra.setSource(DJK.getNode("0"));
        dijkstra.compute();


        System.out.println(dijkstra.getPath(DJK.getNode("1000")));

        //DJK.display();



    }
}
