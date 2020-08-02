package fr.neyox.bb.task;

import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neyox.bb.Main;
import fr.neyox.bb.enums.BBState;
import fr.neyox.bb.events.Events;

public class BBPreStarting extends BukkitRunnable {

	public static BBPreStarting INSTANCE;
	
	private int timer = 30;
	
	public BBPreStarting() {
		INSTANCE = this;
	}
	
	@Override
	public void run() {
		if (timer != 0) {
			for (Player pls : Bukkit.getOnlinePlayers()) {
				Events.SB.get(pls).setLine(4, "§fTemps: §3"+format());
			}
		}
		if (Main.getInstance().getPlayers().size() < Main.minPlayer) {
			Bukkit.broadcastMessage("Pas assez de joueur pour lancer la partie !");
			cancel();
		}
		
		if (timer % 10 == 0 || timer < 5 && timer != 0) {
			Bukkit.broadcastMessage("Lancement du jeu dans " + (timer+5) +"s");
		}
		
		if (timer == 0) {
			new BBStarting();
			BBState.setCurrentState(BBState.STARTING);
			cancel();
		}
		
		timer--;
	}

	private String format() {
		return new SimpleDateFormat("mm:ss").format((timer+5) * 1000);
	}

}
