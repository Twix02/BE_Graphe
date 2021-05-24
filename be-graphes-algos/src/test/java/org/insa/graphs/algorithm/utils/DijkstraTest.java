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
		
		mapMaroc = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\morocco.mapgr";
		mapGuadeloupe = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\guadeloupe.mapgr";
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
	
	/*
	//Nous allons utilisé 4 graphes pour les Tests
	private static Graph HauteGaronne = null ;
	private static Graph Carre = null ;
	private static Graph Maroc = null ;
	private static Graph Guadeloupe = null ;
	
	//Initialisation des graphes
	@BeforeClass
	public static void init() throws Exception {
		final String mapHauteGaronne ="C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\haute-garonne.mapgr";
		final String mapCarre = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\carre.mapgr";
		final String mapMaroc = "C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\morocco.mapgr";
		final String mapGuadeloupe ="C:\\Users\\Lahlou\\Desktop\\BE_Graphe\\Maps\\guadeloupe.mapgr";
		
		final GraphReader readerHauteGaronne = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapHauteGaronne)))) ;
		final GraphReader readerCarre = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre)))) ;
		final GraphReader readerMaroc = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapMaroc)))) ;
		final GraphReader readerGuadeloupe = new BinaryGraphReader (new DataInputStream(new BufferedInputStream(new FileInputStream(mapGuadeloupe)))) ;
		
		DijkstraTest.HauteGaronne = readerHauteGaronne.read();
		DijkstraTest.Carre = readerCarre.read();
		DijkstraTest.Maroc = readerMaroc.read();
		DijkstraTest.Guadeloupe = readerGuadeloupe.read() ;		
	}
	
/*	public ShortestPathSolution runDijkstra (ShortestPathData data) {
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solution = Dijkstra.run();
		return solution;
	}
	
	//Test 1 : Chemin nul, ie origine et destination confondues
	// On tete sur le graphe du Maroc et de la Haute-Garonne
	@Test
	public void CheminNul () {
		ShortestPathData dataMaroc = new ShortestPathData(Maroc, Maroc.get(101501), Maroc.get(101501), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraMaroc = new DijkstraAlgorithm(dataMaroc);
		ShortestPathSolution solutionMaroc = DijkstraMaroc.run();
		assertFalse(solutionMaroc.isFeasible());
		
		ShortestPathData dataHauteGaronne = new ShortestPathData(HauteGaronne, HauteGaronne.get(10991), Maroc.get(10991), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraHauteGaronne = new DijkstraAlgorithm(dataHauteGaronne);
		ShortestPathSolution solutionHauteGaronne = DijkstraHauteGaronne.run();
		assertFalse(solutionHauteGaronne.isFeasible());	
	}
	
	//Test 2 : Chemin simple, ie deux éléments connexes
	//Test sur toutes les cartes
	@Test
	public void CheminSimple() {
		ShortestPathData dataMaroc = new ShortestPathData(Maroc, Maroc.get(101501), Maroc.get(104327), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraMaroc = new DijkstraAlgorithm(dataMaroc);
		ShortestPathSolution solutionMaroc = DijkstraMaroc.run();
		assertFalse(solutionMaroc.isFeasible());
		
		ShortestPathData dataHauteGaronne = new ShortestPathData(HauteGaronne, HauteGaronne.get(10991), Maroc.get(63104), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraHauteGaronne = new DijkstraAlgorithm(dataHauteGaronne);
		ShortestPathSolution solutionHauteGaronne = DijkstraHauteGaronne.run();
		assertFalse(solutionHauteGaronne.isFeasible());			

		ShortestPathData dataGuadeloupe = new ShortestPathData(Guadeloupe, Guadeloupe.get(31333), Guadeloupe.get(32648), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraGuadeloupe = new DijkstraAlgorithm(dataGuadeloupe);
		ShortestPathSolution solutionGuadeloupe = DijkstraGuadeloupe.run();
		assertFalse(solutionGuadeloupe.isFeasible());
		
		ShortestPathData dataCarre = new ShortestPathData(Carre, Carre.get(22), Carre.get(1), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraCarre = new DijkstraAlgorithm(dataCarre);
		ShortestPathSolution solutionCarre = DijkstraCarre.run();
		assertFalse(solutionCarre.isFeasible());	
	}
	
	//Test 3 : Sommets non connexes
	//Test sur la carte de la Guadeloupe
	@Test
	public void CheminImpossible () {
		ShortestPathData dataGuadeloupe = new ShortestPathData(Guadeloupe, Guadeloupe.get(27193), Guadeloupe.get(15892), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraGuadeloupe = new DijkstraAlgorithm(dataGuadeloupe);
		ShortestPathSolution solutionGuadeloupe = DijkstraGuadeloupe.run();
		assertFalse(solutionGuadeloupe.isFeasible());
	}
	
	//Test 4 : Sommet inexistant 
	//Test sur la carte du Maroc
	//expected = IndexOutofBoundsException.class
	@Test(expected = IndexOutOfBoundsException.class)
	public void CheminInexistant () {
		//Origine inexistante 
		ShortestPathData dataMaroc1 = new ShortestPathData(Maroc, Maroc.get(1471307), Maroc.get(104327), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraMaroc1 = new DijkstraAlgorithm(dataMaroc1);
		ShortestPathSolution solutionMaroc1 = DijkstraMaroc1.run();
		assertFalse(solutionMaroc1.isFeasible());

		//Destination inexistante 
		ShortestPathData dataMaroc2 = new ShortestPathData(Maroc, Maroc.get(101501), Maroc.get(1471307), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraMaroc2 = new DijkstraAlgorithm(dataMaroc2);
		ShortestPathSolution solutionMaroc2 = DijkstraMaroc2.run();
		assertFalse(solutionMaroc2.isFeasible());

		//Destination et Origine inexistante
		ShortestPathData dataMaroc3 = new ShortestPathData(Maroc, Maroc.get(1015012), Maroc.get(1471307), ArcInspectorFactory.getAllFilters().get(0) );
		DijkstraAlgorithm DijkstraMaroc3 = new DijkstraAlgorithm(dataMaroc3);
		ShortestPathSolution solutionMaroc3 = DijkstraMaroc3.run();
		assertFalse(solutionMaroc3.isFeasible());
	}
	
	//Comparaison entre Bellman-Ford et Dijkstra dans différents cas
	//On teste sur la carte du Maroc
	@Test
	public void ComparaisonBellmanDijkstra () {
		ShortestPathData data ;
		Random random=new Random() ;
		
		int origin=0;
		int destination=0;
		
		int max=Maroc.size();
		int mode=-1 ;
		
		//On test pour 10 trajets différents
		for (int i = 0; i<10; i++) {
			origin = random.nextInt(max);
			destination = random.nextInt(max);
			mode = random.nextInt(5) ;
			data=new ShortestPathData(Maroc ,Maroc.get(origin), Maroc.get(destination), ArcInspectorFactory.getAllFilters().get(mode) );
			BellmanFordAlgorithm BellmanMaroc = new BellmanFordAlgorithm(data);
			DijkstraAlgorithm DijkstraMaroc = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstraMaroc = DijkstraMaroc.run();
			ShortestPathSolution solutionBellmanMaroc = BellmanMaroc.run();
			
			if (solutionDijkstraMaroc.isFeasible() && solutionBellmanMaroc.isFeasible()) {
				if (solutionBellmanMaroc.getPath()==null) {
					assertTrue(solutionDijkstraMaroc.getPath()==null);
				} else {
					assertEquals(solutionDijkstraMaroc.getPath().getLength(), solutionBellmanMaroc.getPath().getLength(), 1e-6);
					assertEquals(solutionDijkstraMaroc.getPath().getMinimumTravelTime(), solutionBellmanMaroc.getPath().getMinimumTravelTime(), 1e-6);
				}
			}else {
				assertFalse(solutionDijkstraMaroc.isFeasible());
				assertFalse(solutionBellmanMaroc.isFeasible());
			}	
		}
	}
	
*/

