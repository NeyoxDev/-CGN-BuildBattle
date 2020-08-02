package fr.neyox.bb.task;

import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neyox.bb.Main;
import fr.neyox.bb.events.Events;

public class BBVoteTask extends BukkitRunnable {

	private int timer = 20;
	
	private Player player;
	
	public BBVoteTask(Player player) {
		this.player = player;
		this.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	@Override
	public void run() {
		if (timer != 0) {
			for (Player pls : Bukkit.getOnlinePlayers()) {
				Events.SB.get(pls).setLine(4, "§fTemps: §3"+format());
			}
		}
		if (timer == 0) {
			if (Main.getInstance().toVote.isEmpty()) {
				Main.currentPlayer = null;
				Bukkit.broadcastMessage(Main.getInstance().getBestPlayer().getName() +" a gagné !");
				Main.getInstance().finishGame();
				return;
			}
			Main.getInstance().votePlayer(Main.getInstance().getRandomPls());
			cancel();
		}
	}
	
	private String format() {
		return new SimpleDateFormat("mm:ss").format((timer) * 1000);
	}

}
