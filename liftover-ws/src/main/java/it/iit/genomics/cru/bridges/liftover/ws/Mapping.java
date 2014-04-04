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
package it.iit.genomics.cru.bridges.liftover.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Arnaud Ceol arnaud.ceol@iit.it
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mapping")
public class Mapping {

	@XmlElement
	public String genome;
	@XmlElement
	public String assembly;
	@XmlElement
	public String chromosome;
	@XmlElement
	public int start;
	@XmlElement
	public int end;
	
	public Mapping(String genome, String assembly, String chromosome,
			int start, int end) {
		super();
		this.genome = genome;
		this.assembly = assembly;
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
	}

	public String getGenome() {
		return genome;
	}

	public String getAssembly() {
		return assembly;
	}

	public String getChromosome() {
		return chromosome;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public void setGenome(String genome) {
		this.genome = genome;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	
	
}
