package fr.neyox.bb.plots;

import org.bukkit.Location;

import fr.neyox.bb.utils.BlockCuboid;
import fr.neyox.bb.utils.BlockLocation;

public class Plot {
	
	private Location loc1, loc2, middle;
	
	private BlockCuboid cuboid, sol;

	public Plot(Location loc1, Location loc2) {
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.sol = new BlockCuboid(new BlockLocation(loc1.subtract(0,21, 0)), new BlockLocation(loc2).subtract(0, 2, 0));
		this.loc2.setY(59);
		this.middle = generateMiddle();
		this.cuboid = new BlockCuboid(new BlockLocation(loc1.subtract(0, 1, 0)), new BlockLocation(loc2.add(0, 1, 0)));
	}
	
	private Location generateMiddle() {
		return new Location(loc1.getWorld(), (loc1.getX() + loc2.getX()) / 2, 26, (loc1.getZ() + loc2.getZ()) / 2);
	}
	
	public BlockCuboid getSol() {
		return sol;
	}

	public Location getLoc1() {
		return loc1;
	}
	
	public Location getLoc2() {
		return loc2;
	}
	
	public Location getMiddle() {
		return middle;
	}
	
	public BlockCuboid getCuboid() {
		return cuboid;
	}
	
}
