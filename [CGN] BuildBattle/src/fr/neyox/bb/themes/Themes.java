package fr.neyox.bb.themes;

import java.util.Arrays;
import java.util.Random;

public class Themes {

	private static String[] THEMES = Arrays.asList("Maison", "Chaise", "Bateau", "Fleur", "Fantome", "Hibou", "Burger", "Crouton", "Lampe", "Voiture", "Aquarium", "Plage", "Avion", "Clavier", "Chimie", "Ordinateur", "Chameau", "Jardin", "Sorcier", "Espace", "Mouton", "Pizza", "Cochon", "Ferme", "Poulet", "Panda", "Borne d'arcade", "Chambre", "Super-h�ros", "Fossile", "Dinosaure", "Banane", "Pomme", "Pieuvre", "Hopital", "Lave vaisselle", "�cole", "Garage", "Niche", "Tortue", "Camion", "Tracteur", "Serpent", "Arbre", "Bonhomme de neige", "Flocon", "Igloo", "Alien", "F�e", "Sushi", "Balancoire", "Baleine", "Monde", "Plan�te", "Golem", "Ange", "Labyrinth", "Carte", "Zoo", "Grotte", "Poisson", "Pique-nique", "Boite au lettre", "Guitare", "Camping", "Crabe", "Drapeau", "Tambour", "Ninja", "Chat", "Usine", "Chute d'eau", "Bunker", "Rollercoaster", "Baignoire", "Fus�e", "Oasis", "Tour d'horloge", "�p�e", "Cadeau", "Bouteille", "Citrouille").toArray(new String[] {});
	
	
	public static String getTheme() {
		return THEMES[new Random().nextInt(THEMES.length)];
	}
	
}
