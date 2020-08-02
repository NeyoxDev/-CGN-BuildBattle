package fr.neyox.bb.task;

import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neyox.bb.Main;
import fr.neyox.bb.enums.BBState;
import fr.neyox.bb.events.Events;

public class BBStarting extends BukkitRunnable {

	private int timer = 5;
	
	public BBStarting() {
		this.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
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
		
		if (timer != 0 && timer != 5) {
			Bukkit.broadcastMessage("Lancement du jeu dans " + timer +"s");
			Bukkit.getOnlinePlayers().forEach(pls -> pls.playSound(pls.getLocation(), Sound.ORB_PICKUP, 10, 10));
			Bukkit.getOnlinePlayers().forEach(pls -> pls.setLevel(timer));
		}
		
		if (timer == 0) {
			new BBGameCycle();
			Bukkit.getOnlinePlayers().forEach(pls -> pls.playSound(pls.getLocation(), Sound.LEVEL_UP, 10, 10));
			BBState.setCurrentState(BBState.INBUILD);
			Main.getPlugin(Main.class).initGame();
			cancel();
		}
		timer--;
		
	}


	private String format() {
		return new SimpleDateFormat("mm:ss").format(timer * 1000);
	}
	
}
