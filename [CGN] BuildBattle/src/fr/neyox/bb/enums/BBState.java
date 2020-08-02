package fr.neyox.bb.enums;

import java.util.Arrays;

public enum BBState {
	
	WATTING, STARTING, INBUILD, INVOTE;

	private static BBState currentState;
	
	public static BBState getCurrentState() {
		return currentState;
	}
	
	public static void setCurrentState(BBState currentState) {
		BBState.currentState = currentState;
	}
	
	public static boolean isState(BBState... bbStates) {
		return Arrays.asList(bbStates).contains(currentState);
	}
	
}
