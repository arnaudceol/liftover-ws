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

import java.util.Collection;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 	
 * @author Arnaud Ceol arnaud.ceol@iit.it
 *
 */

@WebService
public class LiftOver {
	
	public LiftOver() {
		// Init
		AssemblyUtils.getInstance();
	}
	
	@WebMethod	
	@WebResult(name="availableConvertions")
	public Collection<String[]> getAvailableConvertions() {
		return AssemblyUtils.getInstance().getAvailableConvertions();
	}
	
	
	@WebMethod	
	@WebResult(name="assemblyBySynonym")
	public String getAssemblyBySynonym(String synonym) {
		return AssemblyUtils.getInstance().getAssemblyBySynonym(synonym);
	}
	
	
	@WebMethod	
	@WebResult(name="synonym")
	public Collection<String> getSynonyms(String assembly) {
		return AssemblyUtils.getInstance().getSynonyms(assembly);
	}
	
	@WebMethod	
	@WebResult(name="mapping")
	public Mapping getMapping(String genome, String fromAssembly,
			String toAssembly, String chromosome, int start, int end) {
		// TODO Auto-generated method stub
		return LiftOverRun.runLiftOver(genome, fromAssembly, toAssembly, chromosome, start, end);
	}
}
