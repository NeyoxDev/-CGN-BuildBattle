package fr.neyox.bb.enums;

public enum Vote {

	HORS_SUJET(0),
	MOYEN(1),
	CORRECT(2),
	BIEN(3),
	EPIQUE(4),
	LEGENDAIRE(5);
	
	private int a;
	
	private Vote(int a) {
		this.a = a;
	}
	
	public int getA_() {
		return a;
	}
	
}
