package org.insa.graphs.algorithm.shortestpath;

import java.util.* ;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.ElementNotFoundException ;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm { 
		protected final ShortestPathData data = getInputData() ;
		
		/*Import du graph depuis la data */
		protected Graph graphe = data.getGraph();
		protected int nbNoeuds = graphe.size();

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label [] InitialiseLabels () {
    	
    	/*Tableau des labels intialisée avec les valeurs du noeud donné */
    	Label [] ArrayLabels = new Label[nbNoeuds] ;
    	
    	List<Node> nodes = graphe.getNodes();
    	
    	for (Node node : nodes) {
    		ArrayLabels[node.getId()] = new Label(node) ;
    	}
    	
    	return ArrayLabels ;
    }
    @Override
    protected ShortestPathSolution doRun() {
    	Label [] ArrayLabels ;
    	
    	ArrayLabels = InitialiseLabels () ;
    	
    	int nb_explored = 0 ;
    	int nb_marked = 0 ;
    	
    	//Notifiy observers about the first event (origin processed)
    	notifyOriginProcessed(data.getOrigin());
    	
    	/*Initialisation du tas */
    	BinaryHeap<Label> Tas = new BinaryHeap <>();
    	
    	Node Origin = data.getOrigin() ;
    	Label OriginLabel = ArrayLabels[Origin.getId()];
    	
    	Node Destination = data.getDestination() ;
    	Label DestinationLabel = ArrayLabels[Destination.getId()];
    	
    	/*Insertion de Origin dans le tas */
    	OriginLabel.setCout(0);
    	Tas.insert(OriginLabel);
    	
    	Label CurrentLabel = null ;
    	ShortestPathSolution solution = null ;
    	
    	while (!DestinationLabel.getMarque() && !Tas.isEmpty() && solution == null) {
    			CurrentLabel = Tas.deleteMin() ;
    			CurrentLabel.setMarqueTrue();
    			nb_marked ++ ;
    			
    			System.out.println("Cout :" + CurrentLabel.getCout());
    			
    			/*Notify observers about the node being marked */
    			notifyNodeMarked(CurrentLabel.getSommetCourrant()) ;
    			
    			List<Arc> ListSuccessors = CurrentLabel.getSommetCourrant().getSuccessors();
    			
    			/*Parcours des successeurs du sommet courant */
    			for (Arc ArcIter : ListSuccessors) {
    				/*Test pour vérifier si la route est allowed*/
    				if (data.isAllowed(ArcIter)) {
    					Label IterDestination = ArrayLabels[ArcIter.getDestination().getId()];
    					
    					/*Notify observers about the node being riched*/
    					notifyNodeReached(ArcIter.getDestination());
    					
    					if(!IterDestination.getMarque()) {
    						if (!IterDestination.getMarque() && IterDestination.getCout() > CurrentLabel.getCout() + data.getCost(ArcIter)) {
    							try {
    								Tas.remove(IterDestination);
    							} catch(ElementNotFoundException e) {}
    							
    							nb_explored ++ ;
    							
    							IterDestination.setCout(CurrentLabel.getCout() + data.getCost(ArcIter));
    							IterDestination.setPere(ArcIter);
    							
    							Tas.insert(IterDestination);
    						}
    					}
    				}
    			}
    	}
    	
    	//La destination n'a pas de prédecesseur, la solution est infeasible
    	if ((DestinationLabel.getPere()==null && (data.getOrigin().compareTo(data.getDestination()) != 0) ) || !DestinationLabel.getMarque()) {
    		System.out.println("Chemin impossible") ;
    		solution = new ShortestPathSolution (data, Status.INFEASIBLE);
    	} else {
    		//La destination a été trouvée, on informe l'observateur
    		notifyDestinationReached(data.getDestination());
    		
    		//Empty Path
    		if (data.getOrigin().compareTo(data.getDestination()) == 0) {
    			// Creation de la solution finale
    			System.out.println("Chemin vide");
    			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graphe)) ;
    		} else {
    			//Creation du path à partir du tableau des prédecceseurs
    			ArrayList<Arc> arcs = new ArrayList<>() ;
    			
    			while (!CurrentLabel.equals(OriginLabel)) {
    				arcs.add(CurrentLabel.getPere());
    				CurrentLabel = ArrayLabels[CurrentLabel.getPere().getOrigin().getId()] ;
    			}
    			
    			//Reverse the path..
    			Collections.reverse(arcs);
    			
    			Path path = new Path(graphe, arcs) ;
    			
    			if(path.isValid()) {
    				//Creation de la solution finale
    				solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
    				System.out.println("Chemin Valide") ;
    			} else {
    				System.out.println("Chemin non valide");
    			}
    		}
    	}
    	
    	System.out.println("Nombre Noeuds visités" + nb_explored) ;
    	System.out.println("Nombre Noeuds marqués" + nb_marked) ;
    	
        return solution;
    }

}
