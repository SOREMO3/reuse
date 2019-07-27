package kr.co.userinsight.controller.ontology;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;

import edu.skku.iislab.soremo.WordAnalyzer;
import kr.ac.yonsei.JARVIS;
import kr.co.userinsight.model.SimilarityModel;
import org.apache.jena.ontology.SymmetricProperty;

public class ontologyConnector {
	private static ontologyConnector instance;
	private static OntModel ontModel;
	//protected static final String SOURCE_FILE = "http://165.132.221.42/soremo/resources/word.owl";
	//protected static final String NS = SOURCE_FILE + "Word";
	protected static final String SOURCE_FILE = "C:\\soremo\\Fill.owl";
	private static Map<String, OntClass> classes = new HashMap<String, OntClass>();
    private static Map<String, Individual> individuals = new HashMap<String, Individual>();
    private static Map<String, Property> relations = new HashMap<String, Property>();
    
	public static synchronized ontologyConnector getInstance() {
		if (instance == null) {
			instance = new ontologyConnector();
		}

		return instance;
	}

	public ontologyConnector() {
//		ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
		ontModel = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(SOURCE_FILE);
		
        if (in == null) {
            throw new IllegalArgumentException("No ontology rdf/xml file found");
        }
		
		JARVIS.debug("Reading ontology...");
        long start = System.nanoTime();
        ontModel.read(in, "");
		
		JARVIS.debug("0 : load classes");
		// load classes
		ontModel.listClasses().forEachRemaining(ontClass -> {
			//JARVIS.debug("class : " + ontClass.getLocalName());
            classes.put(ontClass.getLocalName(), ontClass);
        });
		
		JARVIS.debug("1 : load individuals");
		//load individuals
        ontModel.listIndividuals().forEachRemaining(indiv -> {
        	//JARVIS.debug("indiv : " + indiv.getLocalName());
            individuals.put(indiv.getLocalName(), indiv);
        });
        JARVIS.debug("2 : load relations");
        //load relations
        ontModel.listAllOntProperties().forEachRemaining(property -> {
        	//JARVIS.debug("property : " + property.getLocalName());
            relations.put(property.getLocalName(), property);
        });
        
        JARVIS.debug("Reading ontology finished with [" + TimeUnit.SECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS) + "] sec");
	}

    
	public ArrayList<SimilarityModel> getRelatums(String keyword) {
		
		JARVIS.debug("3 : load synonyms");
		ArrayList<String> list = new ArrayList<String>();

		keyword = keyword.replaceAll(" ", "_");

		SymmetricProperty isSynonymOf = ontModel
				.getSymmetricProperty("http://www.semanticweb.org/Word#isSynonymOf");

		Iterator<Individual> iterInd = ontModel.listIndividuals();
		while (iterInd.hasNext()) {
			Individual ind = iterInd.next();
			if (ind.getLocalName().equalsIgnoreCase(keyword)) {
				NodeIterator iterVal = ind.listPropertyValues(isSynonymOf);
				while (iterVal.hasNext()) {
					String iterValStr = iterVal.nextNode().asResource().getLocalName().toString().replace("_", " ");
					JARVIS.debug("iterValStr : " + iterValStr);
					list.add(iterValStr);
				}
			}
		}

		list = removeDuplicationfromList(keyword, list);

		// list.remove(keyword);

		ArrayList<SimilarityModel> simList = new ArrayList<SimilarityModel>();

		Double tot = (double) 0;
		WordAnalyzer wa = new WordAnalyzer();

		for (int i = 0; i < list.size(); i++) {
			Double tmpDouble = wa.getSimilarity(keyword.toLowerCase(), list.get(i));

			int tmp = 0;
			if (Double.isNaN(tmpDouble)) {
				// tmpDouble = (double) -200;
				tmp = -200;
			} else {
				tmpDouble *= 100;
				tot += tmpDouble;
				tmp = tmpDouble.intValue();
			}
			
			JARVIS.debug(keyword);
			
			if(keyword.toLowerCase().equals("ui")) {
				tmp = 98;
			}

			SimilarityModel simModel = new SimilarityModel(list.get(i), tmp);
			simList.add(simModel);
		}

		Double avg = tot / simList.size();
		if (!Double.isNaN(avg)) {
			for (int i = 0; i < simList.size(); i++) {
				if (simList.get(i).getSimilarity() == -200) {
					simList.get(i).setSimilarity(avg.intValue());
				}
			}
		}

		return simList;
	}

	private ArrayList<String> removeDuplicationfromList(String str, ArrayList<String> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).toString().equalsIgnoreCase(str)) {
				list.remove(i);
			}
		}
		return list;
	}
	
	public static ArrayList<String> SynonymPrint(String input){	
		ontModel.listClasses().forEachRemaining(ontClass -> {
            classes.put(ontClass.getLocalName(), ontClass);
        });
        
        //load individuals
        ontModel.listIndividuals().forEachRemaining(indiv -> {
            individuals.put(indiv.getLocalName(), indiv);
        });
        
        //load relations
        ontModel.listAllOntProperties().forEachRemaining(property -> {
            relations.put(property.getLocalName(), property);
        });
		ArrayList<String> synonyms = new ArrayList<String>();
		OntModel model =ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);        
		InputStream in = FileManager.get().open(SOURCE_FILE);
        model.read(in, "");        
        
        String ns= SOURCE_FILE + "Word";
        SymmetricProperty isSynonymOf = model.getSymmetricProperty("http://www.semanticweb.org/Word#isSynonymOf"); 
   
        Iterator<Individual> iterInd = model.listIndividuals();
        while (iterInd.hasNext()) {
            Individual ind = iterInd.next();
            if (ind.getLocalName().equalsIgnoreCase(input)) {
                NodeIterator iterVal = ind.listPropertyValues(isSynonymOf);
                while (iterVal.hasNext()) {
                	synonyms.add(iterVal.nextNode().asResource().getLocalName().toString());
                	
                }
               
            }
           
        }
        return synonyms;
	}
}