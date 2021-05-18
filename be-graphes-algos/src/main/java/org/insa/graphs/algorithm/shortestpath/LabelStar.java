package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node ;

public class LabelStar extends Label implements Comparable<Label> {
	
	private double CoutEstime;
	
	public LabelStar (Node SommetCourant) {
		super(SommetCourant);
		this.CoutEstime = Double.POSITIVE_INFINITY ;
	}
	
	@Override
	public double getCoutTotal() {
		return this.CoutEstime + this.getCout() ;
	}
	
	public double getCoutEstime() {
		return this.CoutEstime;
	}
	
	public void setCoutEstime(double CoutEstime) {
		this.CoutEstime = CoutEstime ;
	}
	
	public int compareTo(LabelStar label) {
		return Double.compare(this.getCoutEstime(), label.getCoutEstime());
	}
}