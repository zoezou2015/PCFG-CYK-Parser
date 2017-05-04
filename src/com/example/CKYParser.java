package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.registry.RegistryHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class CKYParser {
	
	public static void main(String[] args){
		String grammarPath = "data/grammar.txt";
		String input = "the man saw the dog with the telescope";
		String [] sentence = input.split(" ");
		CKYParser p = new CKYParser(sentence, grammarPath);
		p.CKY();
	}
	
	Scanner scanner;
	String[] sentence;
	
	ArrayList<Rule> CKYTree = new ArrayList<Rule>();

	
	ArrayList<String> nonTerminal = new ArrayList<String>();
	ArrayList<String> terminal = new ArrayList<String>();
	String start = "S"; 
	ArrayList<Rule> binaryRule = new ArrayList<Rule>();
	ArrayList<Rule> unaryRule = new ArrayList<Rule>();
	 
	
	public CKYParser(String[] sentence, String filePath){
		super();
		this.sentence = sentence;
		GrammarReader(filePath);
	}
	
	
	
	public void CKY(){
		String [][] tags = new String[this.sentence.length][this.sentence.length];
		double [][] probs = new double[this.sentence.length][this.sentence.length];
		int [][] points = new int[this.sentence.length][this.sentence.length];
		
		for(int l=0; l<this.sentence.length;l++){
			boolean flag = false;
			for(Rule r : unaryRule){
				if(this.sentence[l].equals(r.rhand1.get(0))){					
					flag = true;
					tags[l][l] = r.lhand;
					probs[l][l] = r.prob.get(0);
//					System.out.println(tags[l][l]);
//					System.out.println(this.sentence[l]);
//					System.out.println(r.rhand1.get(0));
//					System.out.println(probs[l][l]);

				}
					
			}	
			if(!flag){
				System.out.println("Bug, the word: "+this.sentence[l]+" is not in terminal set");
			}
		}
		
		for(int l=1; l<this.sentence.length-1; l++){
			for(int i=0; i<this.sentence.length-l;i++){
				int j = i+l;
				double max_prob = 0.0;
				String max_tag = null;
				for(int s=i;s<j;s++){
//					System.out.println(i+" "+j+" "+" "+s);
					String r1 = tags[i][s];
					String r2 = tags[s+1][j];
					double p1= probs[i][s];
					double p2 = probs[s+1][j];
//					System.out.println(r1+" "+r2);
					boolean flag = false;
					for(Rule r: binaryRule){
						if(r.rhand1.contains(r1)){
							int index = r.rhand1.indexOf(r1);
							if(r.rhand2.get(index).equals(r2)){
								double prob3 = r.prob.get(index);
								double temp = prob3*p1*p2;
								if(temp>max_prob){
									max_prob = temp;
									max_tag = r.lhand;
									points[i][j] = s;
									tags[i][j] = max_tag;
									probs[i][j] = max_prob;
									System.out.println(this.sentence[i]+" "+this.sentence[j]+" "+" "+s);
									System.out.println(r1+" "+r2);
									System.out.println(max_tag);
								}
								flag = true;
							}
						} 												
					}
//					if(!flag){
//						System.out.println("BUG: Cannot find the rule for: " + r1 +" "+ r2);
//					} else {
//						System.out.println("***");
//					}
					
				}
				
			}
		}
		
		
		
		
		
	}
	
	public void GrammarReader(String filepath) {
		File file = new File(filepath);
		try {
			scanner = new Scanner(file);
			GrammerParser();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void GrammerParser(){
		while(scanner.hasNext()){
			String root = scanner.next();
			scanner.next();
			String child = scanner.next();
			scanner.next();
			double prob = Double.parseDouble(scanner.next());
			
//			System.out.println(root);
			if(child.contains("_")){
//				System.out.println(child);
				String lhand = child.split("_")[0];
				String rhand = child.split("_")[1];
//				System.out.println(lhand);
//				System.out.println(rhand);
				Rule rule = new Rule(root,lhand,rhand, prob);
				binaryRule.add(rule);
				if(!nonTerminal.contains(lhand))
					nonTerminal.add(lhand);
				if(!nonTerminal.contains(rhand))
					nonTerminal.add(rhand);
			}
			else{ 
				Rule rule = new Rule(root, child, prob);
				unaryRule.add(rule);
				if(!terminal.contains(child))
					terminal.add(child);
			}
				
		}
//		for(int i=0;i<nonTerminal.size();i++){
//			System.out.println(nonTerminal.get(i)+"88");
//		}
	}
	
	
	
	
	
	
}
