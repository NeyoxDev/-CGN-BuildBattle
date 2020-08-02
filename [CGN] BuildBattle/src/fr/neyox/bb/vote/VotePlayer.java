package fr.neyox.bb.vote;

import org.bukkit.entity.Player;

import fr.neyox.bb.enums.Vote;

public class VotePlayer {

	private Vote type;
	
	private Player author;

	public VotePlayer(Vote type, Player author) {
		this.type = type;
		this.author = author;
	}
	
	public Player getAuthor() {
		return author;
	}
	
	public Vote getType() {
		return type;
	}
	
	public void setType(Vote type) {
		this.type = type;
	}
}
