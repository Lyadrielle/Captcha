package fr.upem.capcha.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Logic {
	////////////////////////////////////////////////////////////////////
	///
	///                   ATTRIBUTES:
	///
	////////////////////////////////////////////////////////////////////
	
	ArrayList<String> _selectedTags;
	ArrayList<Resource> _validResources;
	ArrayList<Resource> _wrongResources;
	
	int _state = 1;
	
	
	////////////////////////////////////////////////////////////////////
	///
	///                   METHODS:
	///
	////////////////////////////////////////////////////////////////////
	public Logic() {
		_validResources = new ArrayList<Resource>();
		_wrongResources = new ArrayList<Resource>();
	}
	
	public ArrayList<String> getSelectedTags() {
		return _selectedTags;
	}
	
	public void generate(ArrayList<Resource> resources, ArrayList<String> tags, int numberOfTags) {
		Objects.requireNonNull(resources);
		Objects.requireNonNull(tags);
		
		Resource res = resources.get(ThreadLocalRandom.current().nextInt(0, resources.size() - 1));
		int index = res.getTags().size() <= numberOfTags ? res.getTags().size() : numberOfTags;
		_selectedTags = new ArrayList<String>(res.getTags().subList(0, index));
		
		_validResources.clear();
		_wrongResources.clear();
		
		for (Resource resource : resources) {
			ArrayList<String> tagsResource = resource.getTags();
			int index2 = tagsResource.size() <= index ? tagsResource.size()  : index;
			ArrayList<String> subTagsResource = new ArrayList<String>(tagsResource.subList(0, index2));
			
			if (_selectedTags.equals(subTagsResource)) {
				_validResources.add(resource);
			} else {
				_wrongResources.add(resource);
			}
		}
	}
	
	public ArrayList<Resource> getListOfResource(int numberOfValid, int numberOfResource) {
		ArrayList<Resource> res = new ArrayList<Resource>(); 

		this.getRandomResources(_validResources, numberOfValid, res);
		System.out.println("NUMBER OF VALID RESOURCES : " + res.size());
		this.getRandomResources(_wrongResources, numberOfResource - numberOfValid, res);
		
		System.out.println("RANDOM LIST : ");
		for(Resource r : res) {
			r.display();
		}
		
		return res;
	}
	
	public void getRandomResources(ArrayList<Resource> input, int number, ArrayList<Resource> res)  {
		ArrayList<Resource> imagesClone = ResourceManager.clone(input);
		int n = number > input.size() ? input.size() : number;

		for (int i = 0; i < n; ++i) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, imagesClone.size() - 1);
			res.add(imagesClone.get(randomNum));	
			imagesClone.remove(randomNum);
		}	
	}
	
	public void display() {
		
		System.out.println("SELECTED TAGS : ");
		for(String tag : _selectedTags) {
			System.out.println(tag);;
		}
		
		System.out.println("VALID RESOURCES : ");
		for(Resource resource : _validResources) {
			resource.display();
		}
		
		System.out.println("WRONG RESOURCES : ");
		for(Resource resource : _wrongResources) {
			resource.display();
		}
	}
	
	public void nextState() {
		_state = _state == 3 ? 1 : _state + 1;
	}
	
	public ArrayList<Resource> run(ArrayList<Resource> resources, ArrayList<String> tags) {
		ArrayList<Resource> selectedResources = new ArrayList<Resource>();
		
		switch (_state) {
		case 1:
			this.generate(resources, tags, 1);
			this.display();
			selectedResources = this.getListOfResource(2, 9);
			break;
		case 2: 
			this.generate(resources, tags, 2);
			this.display();
			selectedResources = this.getListOfResource(ThreadLocalRandom.current().nextInt(3, 4), 9);
			break;
		case 3:
			this.generate(resources, tags, 3);
			this.display();
			selectedResources = this.getListOfResource(ThreadLocalRandom.current().nextInt(4, 6), 16);
			break;
		default: 
			throw new RuntimeException("Invalid State : " + _state);
		}
		
		return selectedResources;
	}	
	
}
