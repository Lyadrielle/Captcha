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
	 * Fonction qui s�pare la liste des ressources en fonction de celles
	 * qui ont le(s) tags demand�s et les autres.
	 * Elle prend en param�tre toute la liste des resources et rempli
	 * les deux tableaux (au dessus) si besoin.
	 */
	//tags rpz une collection de tag associ�es � une image, aka un tag g�n�ral(parent) et des sous-tags sp�cifique(filles)
	//il servira de crit�re de r�ussite au test
	//ex : panneau rond -> [panel, round]
	public void sortLabel(ArrayList<Resource> selectedForLabel,ArrayList<String> tags) {
		for(Resource resource :selectedForLabel) {
			resource.getTags();
			System.out.println("wesh");
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
	 * Fonction qui d�finit le succ�s ou l'echec si succ�s libre �
	 * ton imagination sinon augmenter la difficult�
	 */
}
