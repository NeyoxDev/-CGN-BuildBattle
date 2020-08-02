package fr.neyox.bb.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.neyox.bb.Main;
import fr.neyox.bb.enums.BBState;
import fr.neyox.bb.enums.Vote;
import fr.neyox.bb.scoreboard.ScoreboardSign;
import fr.neyox.bb.task.BBPreStarting;
import fr.neyox.bb.vote.VotePlayer;

public class Events implements Listener {

	//remove le protection des clay, et on peut se balander en dehors du carré de clay
	
	private Main main = Main.getPlugin(Main.class);
	
	public static Map<Player, ScoreboardSign> SB = new HashMap<Player, ScoreboardSign>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		SB.put(player, createScoreboard(player));
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		main.initPlayer(player);
		if (BBState.isState(BBState.WATTING, BBState.STARTING) && (main.getPlayers().size()+1) <= main.playerLimit) {
			main.getPlayers().add(player);
			if (main.getPlayers().size() >= Main.minPlayer) {
				if (BBPreStarting.INSTANCE == null) {
					BBPreStarting task = new BBPreStarting();
					task.runTaskTimer(main, 0, 20);
				}
			}
		}else {
			player.setGameMode(GameMode.SPECTATOR);
			/*
			 * Send to lobby
			 */
		}
	}
	
	private ScoreboardSign createScoreboard(Player player) {
		ScoreboardSign sb = new ScoreboardSign(player, "§f§l» §3§lBUILD BATTLE §f§l«");
		sb.create();
		sb.setLine(0, "§b");
		sb.setLine(1, "§a§lInfos");
		sb.setLine(2, "§fJoueurs: §e"+(Main.getInstance().getPlayers().size()+1)+"/§6" + Main.getInstance().playerLimit);
		sb.setLine(3, "§a");
		sb.setLine(4, "§fTemps: §300:00");
		sb.setLine(5, "§c");
		sb.setLine(6, "§fThème: §e§lAucun");
		sb.setLine(7, "§d");
		sb.setLine(8, "§fMode: §7Solo (1.8)");
		sb.setLine(9, "§f");
		sb.setLine(10, "§6crouton-goodnite.fr");
		return sb;
	}
	
	@EventHandler
	public void onExplose(EntityExplodeEvent e) {
		e.setCancelled(true);
		e.blockList().clear();
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onExplose(BlockExplodeEvent e) {
		e.setCancelled(true);
		e.blockList().clear();
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack it = e.getItem();
		if (it == null) return;
		if (BBState.isState(BBState.INVOTE) && it.getType() == Material.WOOL) {
			if (Main.currentPlayer != null && Main.currentPlayer.equals(player)) {
				player.sendMessage("§cTu ne peux pas voter pour toi même !");
				return;
			}
			switch (it.getItemMeta().getDisplayName()) {
			case "§4Hors-sujet":
				main.addVote(new VotePlayer(Vote.HORS_SUJET, player));
				break;
			case "§6Moyen":
				main.addVote(new VotePlayer(Vote.MOYEN, player));
				break;
			case "§2Correct":
				main.addVote(new VotePlayer(Vote.CORRECT, player));
				break;
			case "§aBien":
				main.addVote(new VotePlayer(Vote.BIEN, player));
				break;
			case "§dÉpique":
				main.addVote(new VotePlayer(Vote.EPIQUE, player));
				break;
			case "§eLégendaire":
				main.addVote(new VotePlayer(Vote.LEGENDAIRE, player));
				break;

			default:
				break;
			}
			Main.getInstance().giveClays(player, true);
			player.getInventory().getItemInHand().addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			player.sendMessage("Tu as voté " + it.getItemMeta().getDisplayName());
		}
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		e.setFoodLevel(20);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (!Main.getInstance().getPlayers().contains(player)) {
			e.setCancelled(true);
			return;
		}
		if (BBState.isState(BBState.INBUILD) && !main.getPlot(player).getCuboid().contains(e.getBlock()) || e.getBlock().getLocation().getY() <= 21) {
			e.setCancelled(true);
			player.sendMessage("Vous devez construire dans votre platform !");
		}else if (!BBState.isState(BBState.INBUILD)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (!Main.getInstance().getPlayers().contains(player)) {
			e.setCancelled(true);
			return;
		}
		if (BBState.isState(BBState.INBUILD) && !main.getPlot(player).getCuboid().contains(e.getBlock())) {
			e.setCancelled(true);
			player.sendMessage("Vous devez construire dans votre platform !");
		}else if (!BBState.isState(BBState.INBUILD)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (!Main.getInstance().getPlayers().contains(player)) {
			return;
		}
		if (BBState.isState(BBState.INBUILD) && !main.getPlot(player).getCuboid().containsY(e.getTo())) {
			e.setCancelled(true);
			player.teleport(main.getPlot(player).getMiddle());
			player.sendMessage("§cVous devez rester dans votre platform !");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (main.getPlayers().contains(player)) {
			main.getPlayers().remove(player);
		}
		for (Entry<Player, ScoreboardSign> sb : SB.entrySet()) {
			sb.getValue().setLine(2, "§fJoueurs: §e"+(Main.getInstance().getPlayers().size())+"/§6" + Main.getInstance().playerLimit);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		Player player = e.getPlayer();
		SB.get(player).destroy();
		if (main.getPlayers().contains(player)) {
			main.getPlayers().remove(player);
		}
		for (Entry<Player, ScoreboardSign> sb : SB.entrySet()) {
			sb.getValue().setLine(2, "§fJoueurs: §e"+(Main.getInstance().getPlayers().size())+"/§6" + Main.getInstance().playerLimit);
		}
	}
	
}
