package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.util.List;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label[] InitialiseLabels() {
    	LabelStar ArrayLabels[] = new LabelStar[nbNoeuds];
    	List<Node> nodes = graphe.getNodes() ;
    	
    	double Cout = 0;
    	int vitesse_max = Speed() ;
    	
    	//Point de destination
    	Point DestinationPoint = data.getDestination().getPoint();
    	
    	for (Node node : nodes) {
    		ArrayLabels[node.getId()] = new LabelStar(node) ;
    		
    		//Le coût est la distance entre ce point et le point de destination
    		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
    			Cout = node.getPoint().distanceTo(DestinationPoint);
    			
    			//ou c'est le temps (c'est à dire Distance/Vitesse)
    		} else {
    			Cout = 3.6 * node.getPoint().distanceTo(DestinationPoint) / vitesse_max;
    		}
    		
    		ArrayLabels[node.getId()].setCoutEstime(Cout);	
    	}
    	
    	return ArrayLabels;
    }
    
    private int Speed() {
    	int MaxSpeedData = data.getMaximumSpeed();
    	int MaxSpeedGraph = graphe.getGraphInformation().getMaximumSpeed();
    	int Speed = Math.min(MaxSpeedData, MaxSpeedGraph);
    	
    	if (MaxSpeedData ==  GraphStatistics.NO_MAXIMUM_SPEED && MaxSpeedGraph ==  GraphStatistics.NO_MAXIMUM_SPEED ) {
    		Speed= 130;
    	}
    	
    	if (MaxSpeedData ==  GraphStatistics.NO_MAXIMUM_SPEED) {
    		Speed = MaxSpeedGraph ;
    	}
 		   
    	if (MaxSpeedGraph ==  GraphStatistics.NO_MAXIMUM_SPEED) {
 		   Speed = MaxSpeedData; 
    	}
    	
    	return Speed ;
    }
}
