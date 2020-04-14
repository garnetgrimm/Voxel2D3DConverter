package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import States.StartUpState;

public class SaveModel {	
	public static void writeFile() {
		
		try {
			
			String currentDir = System.getProperty("user.dir");
			currentDir += "\\" + StartUpState.ImageName.substring(0, StartUpState.ImageName.length() - 4) + ".obj";
			File file = new File(currentDir);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(currentDir);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < StartUpState.borderPixelsNum; i++) {
				
				//draw original pos
				//bw.write(StartUpState.boarderPixels[i] + "\n");
				
				//draw a bow around og pos
				bw.write(Transform.translate(StartUpState.boarderPixels[i], 0.5, 0.5) + "\n");
				bw.write(Transform.translate(StartUpState.boarderPixels[i], 0.5, -0.5) + "\n");
				bw.write(Transform.translate(StartUpState.boarderPixels[i], -0.5, 0.5) + "\n");
				bw.write(Transform.translate(StartUpState.boarderPixels[i], -0.5, -0.5) + "\n");
				
				int Vnum = (i * 4) + 1;
				bw.write("f " + (Vnum + 3) + "//" + (Vnum + 0) + " " + (Vnum + 1) + "//" + (Vnum + 0) + " " + (Vnum + 0) + "//" + (Vnum + 0) + " " + (Vnum + 2) + "//" + (Vnum + 0) + "\n");
			}
			
			//SYSTEM IS WORKING PERFECTLY except verticies are doubled
			//blender "remove doubles" function literally connects them all!
			//maybe find out how blender does it?
			//map all pixels, not just border.
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
