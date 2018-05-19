package fr.upem.capcha.ui;

import java.util.ArrayList;

public class Logic {
	////////////////////////////////////////////////////////////////////
	///
	///                   ATTRIBUTES:
	///
	////////////////////////////////////////////////////////////////////
	
	ArrayList<Resource> _validResources=new ArrayList<Resource>();
	ArrayList<Resource> _wrongResources=new ArrayList<Resource>();
	
	////////////////////////////////////////////////////////////////////
	///
	///                   METHODS:
	///
	////////////////////////////////////////////////////////////////////
	
	/* TODO:
	 * Fonction qui sépare la liste des ressources en fonction de celles
	 * qui ont le(s) tags demandés et les autres.
	 * Elle prend en paramêtre toute la liste des resources et rempli
	 * les deux tableaux (au dessus) si besoin.
	 */
	//tags rpz une collection de tag associées à une image, aka un tag général(parent) et des sous-tags spécifique(filles)
	//il servira de critère de réussite au test
	//ex : panneau rond -> [panel, round]
	public void sortLabel(ArrayList<Resource> selectedForLabel,ArrayList<String> tags) {
		for(Resource resource :selectedForLabel) {
			resource.getTags();
			if(resource.getTags().equals(tags)) {
				System.out.println("wesh2");
				_validResources.add(resource);
			}else {
				System.out.println("wesh3");
				_wrongResources.add(resource);
			}
		}
		
	}
	/* TODO:
	 * Fonction qui définit le succès ou l'echec si succès libre à
	 * ton imagination sinon augmenter la difficulté
	 */
	public Boolean hasCleared(ArrayList<Resource> submitList) {
		for(Resource resource: submitList) {
			if(_validResources.contains(resource)) {
				System.out.println(resource+" STATE: IN");
			}
			else {
				if(_wrongResources.contains(resource)) {
					System.out.println(resource+" STATE: OUT");
					return false;
				}else {
					System.out.println(" ERROR : RESOURCE OUT OF CHECK");
					return false;
				}
			}
		}
		return true;
	}
}
