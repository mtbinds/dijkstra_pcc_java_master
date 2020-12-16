                        **Rapport de TP ** *(Implémentation d'une version naïve de l'algorithme de DIJKSTRA)* 
 
 **1/Introduction ** 
 
 -->Le TP consiste à implémenter une version naïve de l'algorithme de DIJKSTRA et en comparer les résultats avec les résultats de la version 
 
 Graph Stream de l'algorithme DIJKSTRA sur le même graphe. 
 
 -->Pour bien comparer les résultats des deux versions de l'algorithme de DIJKSTRA on doit comparer plusieurs graphes avec des tailles différentes,
 
 il est préférable de comparer l'exécution de l'algorithme sur des graphes d'une grande taille pour assurer le fonctionnement de la version naive 
 
 de l'algorithme de dijkstra qu'on doit développer. 
 
 
 -->Pour bien comparer les deux versions de l'algorithme de DIJKSTRA on doit comparer l'exécution des deux versions de l'algorithme sur le même
 
 graphe, et de préférence un graphe qui a une grande taille (1000 noeuds ou plus par exemple). 
 
 
 **2/Structure de programme**
 
  -->Pour l'implémentation de l'algorithme on a utilisé trois classes réparties dans deux fichiers (.java) : 
  
     
       > 1/Les deux classes (Dijkstra et Graphe): 
       
        ->Ces deux classes nous permettent d'imlémenter et de tester la version naive de l'algorithme de Dijkstra, la classe (Graphe) nous permet 
         
        de créer le graphe et lui appliquer l'algorithme de dijkstra qu'on a implémenté, alors que la classe (Dijkstra) nous permet de tester notre
        
        algorithme qu'on a implémenté dans la classe (Graphe). 
        
        
        > 1/La classes (RandomGraph):
        
        ->Cette classe nous permet de générer un graphe aléatoirement en utilisant les (méthodes/fonctions) de GraphStream, le graphe généré on 
        
        lui applique les méthodes de la classe (Dijkstra) de Graph Stream pour avoir le plus petit chemin de la source vers un noeud spécifique de
        
        graphe généré.
        
        
**2/Le générateur des graphes (RandomGenerator()) de GraphStream**        
        
        
        
        















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































