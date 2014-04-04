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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 
 * @author Arnaud Ceol arnaud.ceol@iit.it
 *
 */

public class LiftOverRun {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("liftover.properties");

	public static Mapping runLiftOver(String genome, String fromAssembly,
			String toAssembly, String chromosome, int start, int end) {
		
		String liftOverCommand = RESOURCE_BUNDLE.getString("liftOver");

		String liftOverPath = RESOURCE_BUNDLE.getString("liftOverPath");

		String mapChainDir = RESOURCE_BUNDLE.getString("mapChainDir");

		String tmpDir = RESOURCE_BUNDLE.getString("tmpDir");
		
		Mapping mapping = null;
		
		Runtime r = Runtime.getRuntime();

		String rootFilename = String.format("%s",
				RandomStringUtils.randomAlphanumeric(8));

		String inputFilename = rootFilename + "-" + fromAssembly + ".bed";
		String outputFilename = rootFilename + "-" + toAssembly + ".bed";
		String unmappedFilename = rootFilename + "-" + "unmapped.bed";
		
		String mapChain = fromAssembly.toLowerCase() + "ToH" + toAssembly.toLowerCase().substring(1) + ".over.chain.gz";

		try {

			 File tmpDirFile = new File(tmpDir);

			  // if the directory does not exist, create it
			  if (false == tmpDirFile.exists()) {
			    System.out.println("creating directory: " + tmpDir);
			    boolean result = tmpDirFile.mkdir();  

			     if(result) {    
			       System.out.println("DIR created");  
			     }
			  }
			
			// Write input bed file
			File inputFile = new File(tmpDir + inputFilename);
			// if file doesnt exists, then create it
			if (!inputFile.exists()) {
				inputFile.createNewFile();
			}

			FileWriter fw = new FileWriter(inputFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(chromosome + "\t" + start + "\t" + end +"\n");
			bw.close();

			String commandArgs = String.format("%s %s %s %s %s",
					liftOverPath + "/" + liftOverCommand , tmpDir + inputFilename, mapChainDir + mapChain, tmpDir + outputFilename, tmpDir + unmappedFilename);
			System.out.println(commandArgs);
			
			Process p = r.exec(commandArgs);

			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = b.readLine()) != null) {
			  System.out.println(line);
			}
			b.close();
			
			b = new BufferedReader(new FileReader(tmpDir + outputFilename));
			
			while ((line = b.readLine()) != null) {
				String[] cells = line.split("\t"); 
				String newChromosome = cells[0];
				int newStart = Integer.parseInt(cells[1]);
				int newEnd = Integer.parseInt(cells[2]);
				mapping = new Mapping(genome, toAssembly, newChromosome,
						newStart, newEnd);
			}
			b.close();
			
			// delete
			File delete = new File(tmpDir + inputFilename);
			delete.delete();
			
			delete = new File(tmpDir + outputFilename);
			delete.delete();
			
			delete = new File(tmpDir + unmappedFilename);
			delete.delete();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();		
		}
		
		return mapping;
	}
	
}
