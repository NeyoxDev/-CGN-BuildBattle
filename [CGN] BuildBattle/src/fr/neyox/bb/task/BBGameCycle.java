package fr.neyox.bb.task;

import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neyox.bb.Main;
import fr.neyox.bb.enums.BBState;
import fr.neyox.bb.events.Events;

public class BBGameCycle extends BukkitRunnable {

	private int timer = 60*5;
	
	public BBGameCycle() {
		this.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
	}
	
	@Override
	public void run() {
		if (timer != 0) {
			for (Player pls : Bukkit.getOnlinePlayers()) {
				Events.SB.get(pls).setLine(4, "§fTemps: §3"+format());
			}
		}
		if (Main.getInstance().getPlayers().size() == 1) {
			Bukkit.broadcastMessage(Main.getInstance().getPlayers().get(0).getName() +" a gagné !");
			Main.getInstance().finishGame();
			cancel();
		}
		
		if (timer == (60*5 /2) || (timer < 30 && timer != 0)){
			Bukkit.broadcastMessage("Il reste " + format(timer) +" avant de voter !");
		}
		
		if (timer == 0) {
			BBState.setCurrentState(BBState.INVOTE);
			Main.getPlugin(Main.class).startVote();
			cancel();
		}
		
		timer--;
	}

	private String format(int timer2) {
		return Main.calculateTime(new Long(timer* 1000));
	} 
	
	
	
	private String format() {
		return new SimpleDateFormat("mm:ss").format(timer * 1000);
	}

}
