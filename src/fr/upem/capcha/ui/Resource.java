package fr.upem.capcha.ui;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Resource extends java.lang.Object implements java.lang.Cloneable {
	////////////////////////////////////////////////////////////////////
	///
	///                   ATTRIBUTES:
	///
	////////////////////////////////////////////////////////////////////
	
	ArrayList<String> _tags;
	String _path;
	
	////////////////////////////////////////////////////////////////////
	///
	///                   METHODS:
	///
	////////////////////////////////////////////////////////////////////
	
	// CONSTRUCTORS
	
	/**
	 * 
	 * @param toClone
	 */
	public Resource(Resource toClone) {
		_tags = toClone.getTags();
		_path = toClone.getPath();
	}
	
	
	/**
	 * 
	 * @param tags
	 * @param path
	 */
	public Resource(ArrayList<String> tags, String path) {
		_tags = tags;
		
		String dirPath = Paths.get(Paths.get(
				System.getProperty("user.dir")).toString(),
			"src",
			"fr",
			"upem",
			"capcha"
		).toString();
		if(path.startsWith(dirPath)) {
			_path = path.replace(dirPath, "..");
		} else {
			_path = Paths.get("..", path).toString();	
		}
	}
	
	// GETTERS
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getTags() {
		return _tags;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPath() {
		return _path;
	}
	
	// METHODS
	
	/**
	 * 
	 */
	public void display() {
		System.out.println("PATH:\t" + _path + "\tTAGS:\t" + _tags.toString());
	}
	
	/**
	 * 
	 * @param validTags
	 * @return
	 */
	public Boolean isValid(ArrayList<String> validTags) {
		if (_tags.size() < validTags.size()) {
			return false;
		}
		
		int index = 0;
		for(String tag : validTags) {
			if(!_tags.get(index).equals(tag)) {
				return false;
			}
			++index;
		}
		return true;
	}
}
