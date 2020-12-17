import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** L'idée de la méthode de test principal est de transformer le graphe généré aléatoirement par la classe RandomGraph
 * qui est sous forme d'un fichier .dgs (graphes.dgs) en un graphe compatible avec la classe Graphe puis exécuter le
 * programme naif de dijkstra et comparer les résultat avec ceux de l'exécution de la méthode de la classe Dijkstra
 * incluse dans le package de GraphStream
 * */

public class Dijkstra {

    //Méthode de Test de code (Algorithme de Dijkstra)

    public static void main(String[] args) throws IOException {

        //Le nombre de noeuds de graphe
        int V = 1101;

        //Test de l'algorithme naif de Dijkstra

        //Lecture du fichier "graphes.dgs" et ajouts d'arcs et de poids

        BufferedReader lecteurAvecBuffer = null;
        String ligne;

        try {

            FileReader fr=new FileReader("graphes.dgs");
            lecteurAvecBuffer = new BufferedReader(fr);

        }catch(FileNotFoundException exc) {

            System.out.println("Erreur d'ouverture");
        }


        //Le nombre d'arcs à construire est 10041 arcs (dans le graphe: graphes.dgs)

        int k=55078;
        String[][] str=new String[k][2];

        int w=0;

        //Lecture de fichier (graphes.dgs) ligne par ligne pour la construction du graphe

        while ((ligne = lecteurAvecBuffer.readLine()) != null){

        //On coupe la ligne en chaines de caractères pour définir les arcs entre les noeuds en utilisant un tableau

            StringTokenizer st = new StringTokenizer(ligne);
            String c=st.nextToken();

            if(c.equals("ae")){

                st.nextToken();

                String chaine=st.nextToken();
                String chaine1=st.nextToken();

                str[w][0]=chaine;
                str[w][1]=chaine1;


                w=w+1;
            }
        }


        //On supprime les caractères indésirables ("")

        //Noeud 1

        String c1="";

        for(int i = 0; i < k; i++) {

            String car="";

            for(int j= 1; j< str[i][0].length()-1; j++){

                car=car+str[i][0].charAt(j);
                c1=car;


            }

            str[i][0]=c1;

        }

        //Noeud 2

        String c2="";

        for(int i = 0; i < k ; i++) {

            String car1="";

            for(int j= 1; j < str[i][1].length()-1; j++){

                car1=car1+str[i][1].charAt(j);
                c2=car1;


            }

            str[i][1]=c2;

        }


        lecteurAvecBuffer.close();

        //On ordonne le tableau en fonction croissante de str[i][0]

        String[][] str1=new String[k][2];


        int s=0;

        for(int i = 0; i < V ; i++) {

            for(int j= 0; j < k ; j++) {

                if(str[j][0].equals(String.valueOf(i))){

                    str1[s][0]=str[j][0];
                    str1[s][1]=str[j][1];
                    s=s+1;

                }
            }

        }

        //On construit notre graphe

        Graphe.Arc[] GRAPH=new Graphe.Arc[k];

        //On rajoute des arcs à notre graphe

        for(int i=0;i<k;i++){

            GRAPH[i]=new Graphe.Arc(str1[i][0], str1[i][1], 100);

        }

        Graphe g = new Graphe(GRAPH);

        //La source de notre graphe

        String START="0";

        //Le noeud destinataire de notre graphe (pour exécuter l'algorithme de Dijkstra)

        String END="1000";

        //Ajout de noeud source

        g.dijkstra(START);

        //Ajout de noeud destinataire

        g.ecrireChemin(END);

        //Tout les chemins menant de noeud source vers le noeud destinataire

        g.printAllPaths();
    }
}


class Graphe {


    private final Map<String, Noeud> graph;

    /** Juste un seul noeud de graphe est utilisé par constructeur de Graphe */

    public static class Arc {
        public final String v1, v2;
        public final int dist;

        public Arc(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /** Un seul Noeud de graphe a plusieurs voisins (le mapping)*/

    public static class Noeud implements Comparable<Noeud> {
        public final String nom;
        // MAX_VALUE supposée à l'infinité
        public int dist = Integer.MAX_VALUE;
        public Noeud précédent = null;
        public final Map<Noeud, Integer> voisins = new HashMap<>();

        public Noeud(String name) {
            this.nom = name;
        }

        private void printPath() {
            if (this == this.précédent) {
                System.out.printf("%s", this.nom);
            } else if (this.précédent == null) {
                System.out.printf("%s(inatteignable)", this.nom);
            } else {
                this.précédent.printPath();
                System.out.printf(" -> %s(%d)", this.nom, this.dist);
            }
        }

        public int compareTo(Noeud other) {
            if (dist == other.dist) return nom.compareTo(other.nom);

            return Integer.compare(dist, other.dist);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            Noeud vertex = (Noeud) object;

            if (dist != vertex.dist) return false;
            if (nom != null ? !nom.equals(vertex.nom) : vertex.nom != null) return false;
            if (précédent != null ? !précédent.equals(vertex.précédent) : vertex.précédent != null)
                return false;
            if (voisins != null ? !voisins.equals(vertex.voisins) : vertex.voisins != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (nom != null ? nom.hashCode() : 0);
            result = 31 * result + dist;
            result = 31 * result + (précédent != null ? précédent.hashCode() : 0);
            result = 31 * result + (voisins != null ? voisins.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + nom + ", " + dist + ")";
        }
    }

    /** Création d'un graphe à partir d'un ensemble d'arcs*/

    public Graphe(Arc[] arcs) {
        graph = new HashMap<>(arcs.length);

        //Pour trouver tout les Noeuds
        
        for (Arc e : arcs) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Noeud(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Noeud(e.v2));
        }

        //Pour définir les Noeuds voisins
        
        for (Arc e : arcs) {
            graph.get(e.v1).voisins.put(graph.get(e.v2), e.dist);
            
            // graph.get(e.v2).voisins.put(graph.get(e.v1), e.dist);
            // graph
        }
    }

    /** Exécuter dijkstra en utilisant un noeud source spécifique*/

    public void dijkstra(String nomSource) {
        if (!graph.containsKey(nomSource)) {
            System.err.printf("Le Graphe ne contient pas un Noeud source \"%s\"%n", nomSource);
            return;
        }
        final Noeud source = graph.get(nomSource);
        NavigableSet<Noeud> q = new TreeSet<>();

        // Définition des Noeuds
        
        for (Noeud v : graph.values()) {
            v.précédent = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /** Implémentation de l'algorithme dijkstra en utilisant un binary heap */

    private void dijkstra(final NavigableSet<Noeud> q) {
        Noeud u, v;
        while (!q.isEmpty()) {

            //Le Noeud avec la plus petite distance (la première itération retourne la source)

            u = q.pollFirst();
            
            if (u.dist == Integer.MAX_VALUE)
            
                break; // On peut ignorer u (et tout les autres noeuds) car ils sont inaccessibles

            // Voir les distances vers chaque voisin

            for (Map.Entry<Noeud, Integer> a : u.voisins.entrySet()) {
                
                v = a.getKey(); // Le voisin dans cette itération

                final int alternateDist = u.dist + a.getValue();
                
                if (alternateDist < v.dist) { // Le plus petit chemin vers le voisin est trouvé
                    q.remove(v);
                    v.dist = alternateDist;
                    v.précédent = u;
                    q.add(v);
                }
            }
        }
    }

    /** Ecrit un chemin de la source vers le noeud destinataire */

    public void ecrireChemin(String nomFin) {
        if (!graph.containsKey(nomFin)) {
            System.err.printf("Le Graphe ne contient pas un noeud destinataire \"%s\"%n", nomFin);
            return;
        }

        graph.get(nomFin).printPath();
        System.out.println();
    }

    /** Ecrit le chemin de la source vers chaque Noeud de Graphe */
    
    public void printAllPaths() {
        for (Noeud v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}