package fr.upem.capcha.ui;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import fr.upem.capcha.ui.Resource;

public class ResourceManager {
	////////////////////////////////////////////////////////////////////
	///
	///                   METHODS:
	///
	////////////////////////////////////////////////////////////////////
	
	/**
	 * Get All files from the Images directory with extension exclusion.
	 * @param curDir
	 * @param ext
	 */
	public static void getAllFiles(File curDir, String ext, ArrayList<Resource> resources, ArrayList<String> resourceTags) {
		File[] filesList = curDir.listFiles();
		for(File f : filesList) {
			if(f.isDirectory()) {
				resourceTags.add(f.getName());
				
				getAllFiles(new File(
					Paths.get(curDir.toString(),
					f.getName()).toString()),
					ext,
					resources,
					resourceTags
				);
				
				// Remove previous tags for next categories
				resourceTags.remove(resourceTags.size() -1);
			}
			
			if(f.isFile() && f.toString().lastIndexOf("." + ext) > -1) {
				File file = new File(
						Paths.get(curDir.toString(),
						f.getName()).toString()
					);
				resources.add(new Resource((ArrayList<String>)resourceTags.clone(), file.toString()));
			}
		}
	}
	
	/**
	 * Display all resources by calling upon the Resource.display() function.
	 * @param resources
	 */
	public static void display(ArrayList<Resource> resources) {
		for(Resource resource : resources) {
			resource.display();
		}
	}
	
	/**
	 * Clone the given ArrayList<Resource>. 
	 * @param resources
	 * @return
	 */
	public static ArrayList<Resource> clone(ArrayList<Resource> resources) {
		ArrayList<Resource> clone = new ArrayList<Resource>(resources.size());
	    for (Resource resource : resources) clone.add(new Resource(resource));
	    return clone;
	}
}