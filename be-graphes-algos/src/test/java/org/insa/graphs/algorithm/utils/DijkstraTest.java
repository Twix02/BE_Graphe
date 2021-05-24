package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.*;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Path ;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraTest {
	
	protected static String mapCarre, mapHauteGaronne ;
	protected static DijkstraAlgorithm CheminNul_d, CheminStandardCarre_d, CheminStandard_d, CheminImpossible_d, CheminNul_t, CheminStandardCarre_t, CheminStandard_t, CheminImpossible_t, CheminNul_vd, CheminStandardCarre_vd, CheminStandard_vd, CheminImpossible_vd;
	
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
	    CheminNul_d = new DijkstraAlgorithm(CheminNulData_d);
	    CheminStandardCarre_d = new DijkstraAlgorithm(CheminStandardCarreData_d);
	    CheminImpossible_d = new DijkstraAlgorithm(CheminImpossibleData_d);
	    CheminStandard_d = new DijkstraAlgorithm(CheminStandardData_d);
	    //Routes autorisées pour les voitures seulement
	    CheminNul_vd = new DijkstraAlgorithm(CheminNulData_cl);
	    CheminStandardCarre_vd = new DijkstraAlgorithm(CheminStandardCarreData_cl);
	    CheminImpossible_vd = new DijkstraAlgorithm(CheminImpossibleData_cl);
	    CheminStandard_vd = new DijkstraAlgorithm(CheminStandardData_cl);
	    
	    //Jeu de tests pour le chemin le plus rapide
	    CheminNul_t = new DijkstraAlgorithm(CheminNulData_t);
	    CheminStandardCarre_t = new DijkstraAlgorithm(CheminStandardCarreData_t);
	    CheminImpossible_t = new DijkstraAlgorithm(CheminImpossibleData_t);
	    CheminStandard_t = new DijkstraAlgorithm(CheminStandardData_t);
	}
	
	@Test
	public void testSolutionIsValid() {
		//Plus court chemin, toutes routes
		assertTrue(CheminNul_d.run().getPath().isValid());
		assertTrue(CheminStandardCarre_d.run().getPath().isValid());
		assertTrue(CheminImpossible_d.run().getPath().isValid()); 
		assertTrue(CheminStandard_d.run().getPath().isValid());
		
		//Plus court chemin, voitures autorisées
		assertTrue(CheminNul_vd.run().getPath().isValid());
		assertTrue(CheminStandardCarre_vd.run().getPath().isValid());
		assertTrue(CheminImpossible_vd.run().getPath().isValid()); 
		assertTrue(CheminStandard_vd.run().getPath().isValid());
		
		//Chemin le plus rapide, toutes routes
		assertTrue(CheminNul_t.run().getPath().isValid());
		assertTrue(CheminStandardCarre_t.run().getPath().isValid());
		assertTrue(CheminImpossible_t.run().getPath().isValid()); 
		assertTrue(CheminStandard_t.run().getPath().isValid());
	}
	
	public void testLongueurSolution(DijkstraAlgorithm D) throws IllegalArgumentException{
		ShortestPathSolution solution = D.run();
		
		//Construction de la liste des nodes de la solution
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Arc a : solution.getPath().getArcs()) {
			nodes.add(a.getOrigin());
		}
		nodes.add(solution.getPath().getDestination());
		if (D.getInputData().getMode()==Mode.LENGTH) assertEquals(solution.getPath().getLength(), Path.createShortestPathFromNodes(D.getInputData().getGraph(), nodes).getLength(), 0);
		else assertEquals(solution.getPath().getMinimumTravelTime(), Path.createFastestPathFromNodes(D.getInputData().getGraph(), nodes).getMinimumTravelTime(), 0);
	}
	
	//Note : on ne teste pas la taille ni l'optimalité de la solution vide et de celle de longueur nulle, celles-ci ont leurs propres tests plus précis
		@Test
		public void testLongueurSolution() {
			//Plus court chemin, toutes routes
			testLongueurSolution(CheminStandardCarre_d);
			testLongueurSolution(CheminStandard_d);
			
			//Plus court chemin, voitures autorisée
			testLongueurSolution(CheminStandardCarre_vd);
			testLongueurSolution(CheminStandard_vd);
			
			//Chemin le plus rapide, toutes routes
			testLongueurSolution(CheminStandardCarre_t);
			testLongueurSolution(CheminStandard_t);
		}
		
		@Test
		public void testSolutionImpossible() {
			assertTrue(CheminImpossible_d.run().getPath().isEmpty());
			assertTrue(CheminImpossible_vd.run().getPath().isEmpty());
			assertTrue(CheminImpossible_t.run().getPath().isEmpty());
		}
		

		@Test
		public void testSolutionLongueurNulle() {
			assertEquals(CheminNul_d.run().getPath().getLength(), 0, 0);
			assertEquals(CheminNul_d.run().getPath().getOrigin(), CheminNul_d.getInputData().getOrigin());
			assertEquals(CheminNul_vd.run().getPath().getLength(), 0, 0);
			assertEquals(CheminNul_vd.run().getPath().getOrigin(), CheminNul_vd.getInputData().getOrigin());
			assertEquals(CheminNul_t.run().getPath().getLength(), 0, 0);
			assertEquals(CheminNul_t.run().getPath().getOrigin(), CheminNul_t.getInputData().getOrigin());
		}
		
		public void testOptimaliteBellmanFord(DijkstraAlgorithm D) {
			ShortestPathSolution solutionD = D.run();
			ShortestPathSolution solutionB = new BellmanFordAlgorithm(D.getInputData()).run();
			
			if (D.getInputData().getMode()==Mode.LENGTH) assertEquals(solutionD.getPath().getLength(), solutionB.getPath().getLength(), 0);
			else assertEquals(solutionD.getPath().getMinimumTravelTime(), solutionB.getPath().getMinimumTravelTime(), 0);	
		}
		
		@Test 
		public void testOptimaliteBellmanFord() {
			//Plus court chemin, toutes routes
			testOptimaliteBellmanFord(CheminStandardCarre_d);
			testOptimaliteBellmanFord(CheminStandard_d);
			
			//Plus court chemin, voitures autorisées
			testOptimaliteBellmanFord(CheminStandardCarre_vd);
			testOptimaliteBellmanFord(CheminStandard_vd);
			
			//Chemin le plus rapide, toutes routes
			testOptimaliteBellmanFord(CheminStandardCarre_t);
			testOptimaliteBellmanFord(CheminStandard_t);	
		}
		
}
	


