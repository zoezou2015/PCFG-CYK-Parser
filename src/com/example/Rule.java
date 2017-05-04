package com.example;

import java.util.ArrayList;

public class Rule {
	
	String lhand;
	ArrayList<String> rhand1 = new ArrayList<String>();
	ArrayList<String> rhand2 = new ArrayList<String>();
	
	ArrayList<Double> prob = new ArrayList<Double>();
	boolean tag = false;
	
	public Rule(String lhand, String rhand, double prob){
		super();
		this.lhand = lhand;
		this.rhand1.add(rhand);
		this.prob.add(prob);
		this.tag = true;
	}
	
	public Rule(String lhand, String rhand1, String rhand2, double prob){
		super();
		this.lhand = lhand;
		this.rhand1.add(rhand1);
		this.rhand2.add(rhand2);
		this.prob.add(prob);
		this.tag = false;
	}
	
	public void updateUnaryRule(String r1, double d){
		boolean find = false;
		for(int i=0;i<this.rhand1.size();i++){
			if(this.rhand1.get(i).equals(r1))
				find = true;
		}
		if(!find){
			this.rhand1.add(r1);
			this.prob.add(d);
			this.tag = true;
		}
	}

	public void updateBinaryRule(String r1, String r2, double d){
		boolean find = false; 
		for(int i=0;i<this.rhand1.size();i++){
			if(this.rhand1.get(i).equals(r1) && this.rhand2.get(i).equals(r2))
				find = true;
		}
		if(!find){
			this.rhand1.add(r1);
			this.rhand2.add(r2);
			this.prob.add(d);
			this.tag = false;
		}
		
		
		
	}

}
