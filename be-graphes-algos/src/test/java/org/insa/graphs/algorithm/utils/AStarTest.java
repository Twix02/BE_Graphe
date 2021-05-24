package org.insa.graphs.algorithm.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

import org.junit.BeforeClass;

public class AStarTest extends DijkstraTest {
	
	@BeforeClass
	public static void initAll() throws Exception {
		
		//On charges les graphes à utiliser pour les tests
		mapCarre = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\carre.mapgr"; ;
		mapHauteGaronne = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\haute-garonne.mapgr";
		
		//On crée les lecteurs
		final GraphReader readerCarre = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre)))) ;
		final GraphReader readerHauteGaronne = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapHauteGaronne)))) ;
		
		final Graph Carre = readerCarre.read();
		final Graph HauteGaronne = readerHauteGaronne.read();
		
		readerCarre.close();
		readerHauteGaronne.close();
		
		//On récupère les filtres ArcInspector
		final ArcInspector nofilter_length = ArcInspectorFactory.getAllFilters().get(0);
		final ArcInspector nofilter_time = ArcInspectorFactory.getAllFilters().get(2);
		final ArcInspector cars_length = ArcInspectorFactory.getAllFilters().get(1);
		
		//Les différents scénarios :
		/// Avec la carte non routière
		//Chemin de longueur nulle
		final ShortestPathData CheminNulData_d = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(22), nofilter_length);
	    final ShortestPathData CheminNulData_t = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(22), nofilter_time);
	    final ShortestPathData CheminNulData_cl = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(22), cars_length);
	    
	    //Chemin simple
	    final ShortestPathData CheminStandardCarreData_d = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(15), nofilter_length);
	    final ShortestPathData CheminStandardCarreData_t = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(15), nofilter_time);
	    final ShortestPathData CheminStandardCarreData_cl = new ShortestPathData(Carre, Carre.getNodes().get(22), HauteGaronne.getNodes().get(15), cars_length);
	    
	    
	    ///Avec la carte routière (Haute-Garonne) 
	    //Chemin impossible
	    final ShortestPathData CheminImpossibleData_d = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(66593), HauteGaronne.getNodes().get(121378), nofilter_length);
	    final ShortestPathData CheminImpossibleData_t = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(66593), HauteGaronne.getNodes().get(121378), nofilter_time);
	    final ShortestPathData CheminImpossibleData_cl = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(66593), HauteGaronne.getNodes().get(121378), cars_length);
	    
	    //Chemin simple en distance
	    final ShortestPathData CheminStandardData_d = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(97448), HauteGaronne.getNodes().get(67544), nofilter_length);
	    final ShortestPathData CheminStandardData_t = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(97448), HauteGaronne.getNodes().get(67544), nofilter_time);
	    final ShortestPathData CheminStandardData_cl = new ShortestPathData(HauteGaronne, HauteGaronne.getNodes().get(97448), HauteGaronne.getNodes().get(67544), cars_length);

	    //Initialisation de Dijkstra
	    //Jeu de tests pour le chemin le plus court
	    //Toutes routes
	    CheminNul_d = new AStarAlgorithm(CheminNulData_d);
	    CheminStandardCarre_d = new AStarAlgorithm(CheminStandardCarreData_d);
	    CheminImpossible_d = new AStarAlgorithm(CheminImpossibleData_d);
	    CheminStandard_d = new AStarAlgorithm(CheminStandardData_d);
	    //Routes autorisées pour les voitures seulement
	    CheminNul_vd = new AStarAlgorithm(CheminNulData_cl);
	    CheminStandardCarre_vd = new AStarAlgorithm(CheminStandardCarreData_cl);
	    CheminImpossible_vd = new AStarAlgorithm(CheminImpossibleData_cl);
	    CheminStandard_vd = new AStarAlgorithm(CheminStandardData_cl);
	    
	    //Jeu de tests pour le chemin le plus rapide
	    CheminNul_t = new AStarAlgorithm(CheminNulData_t);
	    CheminStandardCarre_t = new AStarAlgorithm(CheminStandardCarreData_t);
	    CheminImpossible_t = new AStarAlgorithm(CheminImpossibleData_t);
	    CheminStandard_t = new AStarAlgorithm(CheminStandardData_t);
	}

}