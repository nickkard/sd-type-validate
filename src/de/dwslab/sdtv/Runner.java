package de.dwslab.sdtv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class that runs SDType&Validate
 * @author Heiko
 */
public class Runner {
	public static void main(String[] args) {
            
            int i = 0;
            
            int iterations = 20;
            //String dataset = "BNF_subset";
            String dataset = "kenza_conference";
            //String dataset = "DBpedia_subset";
            //String dataset = "histmunic";
            
            for(i=0; i<iterations; i++){
                    LoadFiles loadFiles = new LoadFiles();
                    ComputeBaseStatistics computeBaseStatistics = new ComputeBaseStatistics();
                    MaterializeSDTypes materializeSDTypes = new MaterializeSDTypes();
                    MaterializeSDValidate materializeSDValidate = new MaterializeSDValidate();
                    try {
                            List<String> untyped_instances = new ArrayList<String>();
                            untyped_instances = loadFiles.loadUntypedInstances("/home/nickkard/schemadiscovery/LSH/output/" + dataset + "/" + dataset + "_untyped_instances_" + i + ".txt");
                            loadFiles.loadProperties("/home/nickkard/schemadiscovery/LSH/output/" + dataset + "/" + dataset + "_properties_" + i + ".txt");
                            loadFiles.createPropertyIndices();
                            loadFiles.loadTypes("/home/nickkard/schemadiscovery/LSH/output/" + dataset + "/" + dataset + "_types_" + i + ".txt");
                            loadFiles.createTypeIndices();
                            //loadFiles.loadDisambiguations("./enwiki-20151002-disambiguations-unredirected.ttl");
                            //loadFiles.createDisambiguationIndices();
                            computeBaseStatistics.computeGlobalTypeDistribution();
                            computeBaseStatistics.computePerPredicateDistribution();
                            materializeSDTypes.computeSDTypes(untyped_instances);
                            materializeSDTypes.writeTypeFile("./sdtypes_" + i + ".ttl", 0.6f);
                            materializeSDValidate.computeSDValidateScores();
                            materializeSDValidate.writeWrongStatementsFile("./sdinvalid.ttl", 0.15f);
                            if(1<0)
                                    throw new IOException();
                    } catch (IOException e) {
                            System.out.println("Error loading input files");
                            e.printStackTrace();
                    }
            }
	}
}
