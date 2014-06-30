package it.iit.genomics.cru.bridges.liftover.ws;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AssemblyUtils {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle("/liftover");

	private static final ResourceBundle SYNOMYMS_BUNDLE = ResourceBundle
			.getBundle("/synomyns");

	private static AssemblyUtils instance;

	private AssemblyUtils() {

		// load synonyms
		for (String assembly: SYNOMYMS_BUNDLE.keySet()) {
			ArrayList<String> synonyms = new ArrayList<String>();
			synonyms.addAll(Arrays.asList(SYNOMYMS_BUNDLE.getString(assembly).split(",")));
		}

		// load mappings 
		String mapChainDirectory = RESOURCE_BUNDLE.getString("mapChainDir");
		File directory = new File(mapChainDirectory);
		if (directory.isDirectory()) {
			
			File[] files = directory.listFiles();

			for (File file : files) {
				if (false == file.getName().endsWith(".over.chain.gz")) {
					continue;
				}
				String mappingFile = file.getName().split("\\.")[0];
				String from = mappingFile.split("To")[0].toLowerCase();
				String to = mappingFile.split("To")[1].toLowerCase();		
						
				String[] mapping = {from, to}; 
				mappings.add(mapping);
			}		
		}
	}

	public static AssemblyUtils getInstance() {
		if (instance == null) {
			instance = new AssemblyUtils();
		}

		return instance;
	}

	private HashMap<String, Collection<String>> synomyms = new HashMap<String, Collection<String>>();
	
	private ArrayList<String[]> mappings = new ArrayList<String[]>();

	public ArrayList<String[]> getMappings() {
		return mappings;
	}

	public String getAssemblyName(String assembly) {
		
		for (String mainAssemblyName : synomyms.keySet()) {
			if (synomyms.get(mainAssemblyName).contains(assembly)) {
				return mainAssemblyName;
			}
		}
		return null;
	}

	public boolean areSynonyms(String assemblyName1, String assemblyName2) {
		
		String mainName1 = getAssemblyName(assemblyName1);
		String mainName2 = getAssemblyName(assemblyName2);
		
		return mainName1 != null && mainName2 != null && mainName1.equals(mainName2);
	}

	public Collection<String> getSynonyms(String assemblyName) {

		for (String mainAssemblyName : synomyms.keySet()) {
			if (synomyms.get(mainAssemblyName).contains(assemblyName)) {
				return synomyms.get(mainAssemblyName);
			}
		}
		return null;
	}

	public boolean isMappable(String sourceAssembly, String targetAssembly) {
		for (String[] assemblies : mappings) {
			if (sourceAssembly.equals(assemblies[0])
					&& targetAssembly.equals(assemblies[1])) {
				return true;
			}
		}
		return false;
	}

}
