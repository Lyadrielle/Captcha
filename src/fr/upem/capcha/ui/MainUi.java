package fr.upem.capcha.ui;

import fr.upem.capcha.images.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainUi {
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	
	public static void main(String[] args) throws IOException {
		////////////////////////////////////////////////////////////////////
		///
		///                   PROPERTIES:
		///
		////////////////////////////////////////////////////////////////////
		
		// "GLOBAL" VARIABLES
		Path filePath = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "fr", "upem", "capcha", "images");
		ArrayList<Resource> resources = new ArrayList<Resource>();
		ArrayList<String> imgTags = new ArrayList<String>();
		
		// SORT & STORE ALL PATHS & USEFUL INFORMATIONS
		ResourceManager.getAllFiles(new File(filePath.toString()), "jpg", resources, imgTags);
		ResourceManager.display(resources);
		
		
		// JAVA WINDOW PRESET ATTRIBUTS & LAYOUTS/FRAME
		JFrame frame = new JFrame("Capcha");
		GridLayout layout = createLayout();
		
		frame.setLayout(layout);
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// CREATE ELEMENTS OF THE PROGRAM
		JButton okButton = createOkButton();
		
		ArrayList<Resource> selectedForLabel = new ArrayList<Resource>();
		ArrayList<JLabel> labelImages = createAllLabelImages(resources, 9,selectedForLabel);
		Logic logic = new Logic();
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, selectedForLabel.size() - 1);
		System.out.println("choisissons un element au hasard: "+randomNum);
		ArrayList<String> successTags= selectedForLabel.get(randomNum).getTags();
		System.out.println("son taglist: "+successTags);
		
		logic.sortLabel(selectedForLabel, successTags);
		ResourceManager.display(logic._validResources);
		ResourceManager.display(logic._wrongResources);
		
		
		// ADD ALL ELEMENTS TO THE FRAME/LAYOUT
		setAllFrames(frame, labelImages);
		frame.add(new JTextArea("You Must select all the pictures containing a: "));
		frame.add(okButton);
		
		// SET FRAME VISIBLE
		frame.setVisible(true);
		
		
	}
	
   ////////////////////////////////////////////////////////////////////
   ///
   ///                   METHODS:
   ///
   ////////////////////////////////////////////////////////////////////
	
	/**
	 * Create a new grid Layout with x rows and y columns.
	 * @return
	 */
	private static GridLayout createLayout(){
		return new GridLayout(4,3);
	}
	
	/**
	 * Create the verification Button
	 * @return
	 */
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("V�rifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						System.out.println("J'ai cliqu� sur Ok");
					}
				});
			}
		});
	}
	
	/**
	 * Create one JLabel hidden as an image.
	 * @param imagePath
	 * @return
	 * @throws IOException
	 */
	private static JLabel createLabelImage(Resource resource) throws IOException {
		String imageRelativPath = resource.getPath();
		
		final URL url = MainUi.class.getResource(imageRelativPath);

		BufferedImage img = ImageIO.read(url);
		Image sImage = img.getScaledInstance(1024/3,768/4, Image.SCALE_SMOOTH);
		
		final JLabel label = new JLabel(new ImageIcon(sImage));
		label.addMouseListener(new MouseListener() {
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
	
	/**
	 * Create all Label Images with an ArrayList of resources.
	 * @param resources
	 * @return
	 */
	private static ArrayList<JLabel> createAllLabelImages(ArrayList<Resource> resources, int nbImages,ArrayList<Resource>selectedForLabel) {
		ArrayList<JLabel> listOfLabelImages = new ArrayList<JLabel>();
		ArrayList<Resource> imagesClone = ResourceManager.clone(resources);
		
		for(int i = 0; i < nbImages; ++i) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, imagesClone.size() - 1);
			System.out.println(randomNum);
			
			try {
				System.out.println(imagesClone.get(randomNum));
				listOfLabelImages.add(createLabelImage(imagesClone.get(randomNum)));
			}catch(IOException e) {
				System.out.println (e.toString());
		        System.out.println("No such file at: " + imagesClone.get(randomNum));
			}
			if(!selectedForLabel.contains(imagesClone.get(randomNum))) {
				selectedForLabel.add(imagesClone.get(randomNum));
			}
			
			imagesClone.remove(randomNum);
		}
		
		return listOfLabelImages;
	}
	
	/**
	 * Push all jLabel Images into the frame to be displayed subsequently.
	 * @param frame
	 * @param labelImages
	 */
	private static void setAllFrames(JFrame frame, ArrayList<JLabel> labelImages) {
		for(JLabel lImg : labelImages) {
			frame.add(lImg);
		}
	}
}
