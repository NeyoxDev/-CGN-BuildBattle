package fr.neyox.bb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neyox.bb.enums.BBState;
import fr.neyox.bb.events.Events;
import fr.neyox.bb.plots.Plot;
import fr.neyox.bb.task.BBVoteTask;
import fr.neyox.bb.themes.Themes;
import fr.neyox.bb.utils.ItemBuilder;
import fr.neyox.bb.vote.VotePlayer;

public class Main extends JavaPlugin {
	
	private List<Player> players = new ArrayList<Player>();
	
	private List<Plot> availablePlots;
	
	private Map<Player, Plot> playerToPlot;
	
	private Map<Player, List<VotePlayer>> playerToVotes;
	
	public List<Player> toVote;
	
	public static Player currentPlayer;
	
	public int playerLimit;
	
	public static final int minPlayer = 1;
	
	@Override
	public void onEnable() {
		BBState.setCurrentState(BBState.WATTING);
		World w = Bukkit.getWorld("world");
		this.availablePlots = Arrays.asList(
				new Plot(new Location(w, -137, 23, 5), new Location(w, -170, 23, 38)), 
				new Plot(new Location(w, -170, 23, 49), new Location(w, -137, 23, 82)),
				new Plot(new Location(w, -137, 23, 93), new Location(w, -170, 23, 126)),
				new Plot(new Location(w, -137, 23, 137), new Location(w, -170, 23, 170)),
				new Plot(new Location(w, -93, 23, 137), new Location(w, -126, 23, 170)),
				new Plot(new Location(w, -126, 23, 126), new Location(w, -93, 23, 93)),
				new Plot(new Location(w, -126, 23, 82), new Location(w, -93, 23, 49)), 
				new Plot(new Location(w, -93, 23, 5), new Location(w, -126, 23, 38)),
				new Plot(new Location(w, -82, 23, 38), new Location(w, -49, 23, 4)),
				new Plot(new Location(w, -49, 23, 49), new Location(w, -82, 23, 49)),
				new Plot(new Location(w, -49, 23, 93), new Location(w, -82, 23, 126)),
				new Plot(new Location(w, -49, 23, 137), new Location(w, -82, 23, 170)),
				new Plot(new Location(w, -38, 23, 137), new Location(w, -5, 23, 170)),
				new Plot(new Location(w, -5, 23, 93), new Location(w, -38, 23, 126)),
				new Plot(new Location(w, -38, 23, 82), new Location(w, -5, 23, 49)),
				new Plot(new Location(w, -38, 23, 5), new Location(w, -5, 23, 38)));
		this.playerLimit = this.availablePlots.size();
		Bukkit.getLogger().info(playerLimit*2 +" locations loaded (max players: " + playerLimit+")");
		this.playerToPlot = new HashMap<Player, Plot>();
		this.playerToVotes = new HashMap<Player, List<VotePlayer>>();
		getServer().getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public void onDisable() {
		finishGame();
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public static Main getInstance() {
		return Main.getPlugin(Main.class);
	}

	public void initPlayer(Player player) {
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(30);
		player.setLevel(0);
		player.setExp(0);
		player.setTotalExperience(0);
	}
	
	public Plot getPlot(Player player) {
		return playerToPlot.get(player);
	}
	
	public void finishGame() {
		for (Plot plot : availablePlots) {
			plot.getCuboid().iterator().forEachRemaining(b -> b.setType(Material.AIR));
			plot.getSol().iterator().forEachRemaining(b -> b.setType(Material.STAINED_CLAY));
		}
	}

	public void initGame() {
		String theme = Themes.getTheme();
		Bukkit.broadcastMessage("Le theme est " + theme +" !");
		for (int i = 0; i < getPlayers().size(); i++) {
			Player player = getPlayers().get(i);
			Plot plot = availablePlots.get(i);
			playerToPlot.put(player, plot);
			player.teleport(plot.getMiddle().add(0, 0.5, 0));
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage("have fun !");
			Events.SB.get(player).setLine(6, "§fThème: §e§l"+theme);
		}
	}

	public void startVote() {
		for (Player pls : getPlayers()) {
			pls.setGameMode(GameMode.CREATIVE);
			giveClays(pls, true);
		}
		toVote = new ArrayList<Player>(players);
		votePlayer(getRandomPls());
	}
	
	@SuppressWarnings("deprecation")
	public void giveClays(Player pls, boolean clear) {
		Inventory inv = pls.getInventory();
		if (clear) {
			inv.clear();
		}
		inv.setItem(0, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.RED).setName("§4Hors-sujet").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
		inv.setItem(1, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.ORANGE).setName("§6Moyen").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
		inv.setItem(2, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.GREEN).setName("§2Correct").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
		inv.setItem(3, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.LIME).setName("§aBien").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
		inv.setItem(4, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.PURPLE).setName("§dÉpique").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
		inv.setItem(5, new ItemBuilder(Material.STAINED_CLAY).setWoolColor(DyeColor.YELLOW).setName("§eLégendaire").addFlag(ItemFlag.HIDE_ENCHANTS).toItemStack());
	}
	
	public static String calculateTime(Long timeIn) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeIn.longValue()) - TimeUnit.MILLISECONDS.toHours(timeIn.longValue()) * 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeIn.longValue()) - TimeUnit.MILLISECONDS.toMinutes(timeIn.longValue()) * 60L;
        String time = "";
        if (minutes != 0) {
        	time = minutes > 1 ? minutes +" minutes" : minutes + " minute";
        }else if (seconds != 0) {
        	time = seconds > 1 ? seconds +" secondes" : seconds + " seconde";
        }
        return time;
      }
	
	@SuppressWarnings("serial")
	public void addVote(VotePlayer vote) {
		Player player = currentPlayer;
		if (!playerToVotes.containsKey(player)) {
			if (hasVoted(vote.getAuthor())) {
				getVotes(currentPlayer).stream().filter(pls -> pls.getAuthor().equals(player)).findFirst().get().setType(vote.getType());
				return;
			}
			playerToVotes.put(player, new ArrayList<VotePlayer>() {
				{
					this.add(vote);
				}
			});
		}else {
			getVotes(player).add(vote);
		}
	}
	
	public Player getBestPlayer() {
		List<Player> pls = new ArrayList<Player>(getPlayers());
		Collections.sort(pls, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				return getPoints(o1) - getPoints(o2);
			}
		});
		return pls.get(0);
	}
	
	public int getPoints(Player player) {
		int i = 0;
		for (VotePlayer vote : getVotes(player)) {
			i += vote.getType().getA_();
		}
		return i;
	}
	
	public boolean hasVoted(Player player) {
		return currentPlayer != null && getVotes(currentPlayer).stream().filter(pls -> pls.getAuthor().equals(player)).findFirst().isPresent();
	}
	
	public List<VotePlayer> getVotes(Player player){
		return playerToVotes.getOrDefault(player, new ArrayList<VotePlayer>());
	}
	
	@SuppressWarnings("deprecation")
	public void votePlayer(Player randomPlayer) {
		currentPlayer = randomPlayer;
		for (Player pls : getPlayers()) {
			pls.sendTitle("§eBuilder: " + randomPlayer.getName(), "");
			pls.sendMessage("§cVous avez 2mn pour voter !");
		}
		new BBVoteTask(randomPlayer);
		toVote.remove(randomPlayer);
	}

	public Player getRandomPls() {
		return toVote.get(new Random().nextInt(toVote.size()));
	}
	
	
}
