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

/**
 * 
 * @author Arnaud Ceol arnaud.ceol@iit.it
 *
 */
public class LiftOverClient {

	LiftOver port;
	
	public LiftOverClient() {
		LiftOverService service = new LiftOverService();
	    port = service.getLiftOverPort();  
	}
	
	public Mapping getMapping(String genome, String fromAssembly,
			String toAssembly, String chromosome, int start, int end) {
		return port.getMapping("Human", "hg18", "hg19"
				, "chr1",    743267,  743268);
	}
	
	public static void main(String[] args) throws Exception{
	
		LiftOverClient client = new LiftOverClient();  
        
	    Mapping mapping = client.getMapping("Human", "hg18", "hg19"
				, "chr1",    743267,  743268);
	    
	    System.out.println("lift over: "+mapping.getChromosome() + " " + mapping.getStart() + " " + mapping.getEnd());
        
	}
	
}
