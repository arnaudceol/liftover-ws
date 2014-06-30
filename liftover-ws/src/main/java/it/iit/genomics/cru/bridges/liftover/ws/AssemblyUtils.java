/*******************************************************************************
 * Copyright 2014 Arnaud Ceol
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
			.getBundle("/synonyms");

	private static AssemblyUtils instance;

	private AssemblyUtils() {

		// load synonyms
		for (String assembly: SYNOMYMS_BUNDLE.keySet()) {
			ArrayList<String> assemblySynonyms = new ArrayList<String>();
			assemblySynonyms.addAll(Arrays.asList(SYNOMYMS_BUNDLE.getString(assembly).split(",")));
			synomyms.put(assembly, assemblySynonyms);
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
				availableConvertions.add(mapping);
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
	
	private ArrayList<String[]> availableConvertions = new ArrayList<String[]>();

	public ArrayList<String[]> getAvailableConvertions() {
		return availableConvertions;
	}

	public String getAssemblyBySynonym(String synonym) {
		
		for (String assembly : synomyms.keySet()) {
			if (synomyms.get(assembly).contains(synonym)) {
				return assembly;
			}
		}
		return null;
	}

	public boolean areSynonyms(String assemblyName1, String assemblyName2) {
		
		String mainName1 = getAssemblyBySynonym(assemblyName1);
		String mainName2 = getAssemblyBySynonym(assemblyName2);
		
		return mainName1 != null && mainName2 != null && mainName1.equals(mainName2);
	}

	public Collection<String> getSynonyms(String synonym) {

		for (String assembly : synomyms.keySet()) {
			if (synomyms.get(assembly).contains(synonym)) {
				return synomyms.get(assembly);
			}
		}
		return null;
	}

	public boolean isConvertible(String sourceAssembly, String targetAssembly) {
		for (String[] assemblies : availableConvertions) {
			if (sourceAssembly.equals(assemblies[0])
					&& targetAssembly.equals(assemblies[1])) {
				return true;
			}
		}
		return false;
	}

}
