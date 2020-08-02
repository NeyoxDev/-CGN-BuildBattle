package fr.neyox.bb.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.google.common.base.Preconditions;

public class BlockCuboid implements BlockArea, Cloneable {
	private BlockLocation posMin;

	private BlockLocation posMax;

	private Collection<Block> contentCache;

	public BlockCuboid(BlockLocation pos1, BlockLocation pos2) {
		Preconditions.checkNotNull(pos1);
		Preconditions.checkNotNull(pos2);
		Preconditions.checkArgument((pos1.getWorld() == pos2.getWorld()), "pos1 and pos2 must have same world!");
		this.posMin = pos1.clone();
		this.posMax = pos2.clone();
		this.posMin.setX(Math.min(pos1.getX(), pos2.getX()));
		this.posMin.setY(Math.min(pos1.getY(), pos2.getY()));
		this.posMin.setZ(Math.min(pos1.getZ(), pos2.getZ()));
		this.posMax.setX(Math.max(pos1.getX(), pos2.getX()));
		this.posMax.setY(Math.max(pos1.getY(), pos2.getY()));
		this.posMax.setZ(Math.max(pos1.getZ(), pos2.getZ()));
	}

	public BlockLocation getPosMin() {
		return this.posMin;
	}

	public BlockLocation getPosMax() {
		return this.posMax;
	}

	public boolean contains(Block block) {
		return (block.getX() >= this.posMin.getX() && block.getX() <= this.posMax.getX()
				&& block.getY() >= this.posMin.getY() && block.getY() <= this.posMax.getY()
				&& block.getZ() >= this.posMin.getZ() && block.getZ() <= this.posMax.getZ()
				&& this.posMin.isWorld(block.getWorld()));
	}

	public boolean contains(Location loc) {
		return (loc.getBlockX() >= this.posMin.getX() && loc.getBlockX() <= this.posMax.getX()
				&& loc.getBlockY() >= this.posMin.getY() && loc.getBlockY() <= this.posMax.getY()
				&& loc.getBlockZ() >= this.posMin.getZ() && loc.getBlockZ() <= this.posMax.getZ()
				&& this.posMin.isWorld(loc.getWorld()));
	}

	public Iterator<Block> iterator() {
		if (this.contentCache == null) {
			this.contentCache = new ArrayList<>();
			for (int x = this.posMin.getX(); x <= this.posMax.getX(); x++) {
				for (int z = this.posMin.getZ(); z <= this.posMax.getZ(); z++) {
					for (int y = this.posMin.getY(); y <= this.posMax.getY(); y++)
						this.contentCache.add(this.posMin.getWorld().getBlockAt(x, y, z));
				}
			}
			this.contentCache = Collections.unmodifiableCollection(this.contentCache);
		}
		return this.contentCache.iterator();
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BlockCuboid blocks = (BlockCuboid) o;
		if (!this.posMin.equals(blocks.posMin))
			return false;
		return this.posMax.equals(blocks.posMax);
	}

	public int hashCode() {
		int result = this.posMin.hashCode();
		result = 31 * result + this.posMax.hashCode();
		return result;
	}

	public String toString() {
		return "BlockCuboid{posMin=" + this.posMin + ", posMax=" + this.posMax + '}';
	}

	public BlockCuboid clone() {
		try {
			BlockCuboid cuboid = (BlockCuboid) super.clone();
			cuboid.posMin = this.posMin.clone();
			cuboid.posMax = this.posMax.clone();
			cuboid.contentCache = null;
			return cuboid;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containsY(Location loc) {
		return posMax.getY() == loc.getY();
	}
}
