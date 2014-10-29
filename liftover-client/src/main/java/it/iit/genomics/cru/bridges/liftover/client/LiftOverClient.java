/*******************************************************************************
 * Copyright 2014 Fondazione Istituto Italiano di Tecnologia
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
package it.iit.genomics.cru.bridges.liftover.client;

import it.iit.genomics.cru.bridges.liftover.ws.LiftOver;
import it.iit.genomics.cru.bridges.liftover.ws.LiftOverService;
import it.iit.genomics.cru.bridges.liftover.ws.Mapping;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import net.java.dev.jaxb.array.StringArray;

/**
 * 
 * @author Arnaud Ceol arnaud.ceol@iit.it
 *
 */
public class LiftOverClient {

	private LiftOver port;

	private ArrayList<String[]> availableConvertions;
	
	public LiftOverClient() {
		LiftOverService service = new LiftOverService();
		port = service.getLiftOverPort();
		
		availableConvertions = new ArrayList<String[]>();
		
		for (StringArray mappingArray : port.getAvailableConvertions()) {
			availableConvertions.add(mappingArray.getItem().toArray(new String[2]));
		}
		
	}

	public LiftOverClient(String endPointURL) throws MalformedURLException {
		LiftOverService service = new LiftOverService(new URL(endPointURL));
		
		port = service.getLiftOverPort();
//		
//		((BindingProvider) port).getRequestContext().put(
//				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
		
		availableConvertions = new ArrayList<String[]>();
		
		for (StringArray mappingArray : port.getAvailableConvertions()) {
			availableConvertions.add(mappingArray.getItem().toArray(new String[2]));
		}
		
	}

	public Mapping getMapping(String genome, String fromAssembly,
			String toAssembly, String chromosome, int start, int end) {
		return port.getMapping(genome, fromAssembly, toAssembly, chromosome,
				start, end);
	}

	public Collection<String[]> getAvailableConvertions() {
		return availableConvertions;
	}


	public boolean isConvertionAvailable(String sourceAssembly, String targetAssembly) {
		for (String[] mapping : getAvailableConvertions()) {
			if (mapping[0].equals(sourceAssembly) && mapping[1].equals(targetAssembly)) {
				return true;
			}
		}
		return false;
	}	
	
	public String getAssemblyBySynonym(String synonym) {
		return port.getAssemblyBySynonym(synonym);
	}
	

	public Collection<String> getSynonyms(String assembly) {
		return port.getSynonyms(assembly);
	}
	

	public static void main(String[] args) throws Exception {
		LiftOverClient client = new LiftOverClient("http://37.34.38.126:8080/liftover-ws/LiftOverService?wsdl");
				
		System.out.println("GRCh38: " + client.getAssemblyBySynonym("GRCh38"));
		System.out.println("GRCh37: " + client.getAssemblyBySynonym("GRCh37"));

		for (String[] mapping : client.getAvailableConvertions()) {
			System.out.println(mapping[0] + "->" + mapping[1]);
		}

		Mapping mapping = client.getMapping("Human", "hg19", "hg18", "chr1",
				44474106, 44474107);

		System.out.println("lift over: " + mapping.getChromosome() + " "
				+ mapping.getStart() + " " + mapping.getEnd());

	}

}
