package fr.neyox.bb.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockLocation extends BlockVector implements Cloneable {
	private String worldName;

	private int worldHash;

	public BlockLocation(Block block) {
		this(block.getWorld(), block.getX(), block.getY(), block.getZ());
	}

	public BlockLocation(Location loc) {
		this(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public BlockLocation(World world, int x, int y, int z) {
		this(world.getName(), world.hashCode(), x, y, z);
	}

	private BlockLocation(String worldName, int worldHash, int x, int y, int z) {
		super(x, y, z);
		this.worldName = worldName;
		this.worldHash = worldHash;
	}

	public String getWorldName() {
		return this.worldName;
	}

	public World getWorld() {
		if (this.worldName == null)
			return null;
		return Bukkit.getServer().getWorld(this.worldName);
	}

	public BlockLocation setWorld(World world) {
		if (world == null) {
			setWorld((String) null, 0);
		} else {
			setWorld(world.getName(), world.hashCode());
		}
		return this;
	}

	public BlockLocation setWorld(String worldName, int worldHash) {
		this.worldName = worldName;
		this.worldHash = worldHash;
		return this;
	}

	public boolean isWorld(World world) {
		if (this.worldName == null)
			return (world == null);
		return (world != null && world.hashCode() == this.worldHash && this.worldName.equals(world.getName()));
	}

	public Chunk getChunk() {
		return getWorld().getChunkAt(this.x >> 4, this.z >> 4);
	}

	public boolean isChunkLoaded() {
		return getWorld().isChunkLoaded(this.x >> 4, this.z >> 4);
	}

	public Block getBlock() {
		return getWorld().getBlockAt(this.x, this.y, this.z);
	}

	public Location getLocation() {
		return new Location(getWorld(), this.x, this.y, this.z);
	}

	public BlockLocation setX(int x) {
		return (BlockLocation) super.setX(x);
	}

	public BlockLocation setY(int y) {
		return (BlockLocation) super.setY(y);
	}

	public BlockLocation setZ(int z) {
		return (BlockLocation) super.setZ(z);
	}

	public BlockLocation add(int x, int y, int z) {
		return (BlockLocation) super.add(x, y, z);
	}

	public BlockLocation subtract(int x, int y, int z) {
		return (BlockLocation) super.subtract(x, y, z);
	}

	public BlockLocation add(BlockVector blockVector) {
		return (BlockLocation) super.add(blockVector);
	}

	public BlockLocation subtract(BlockVector blockVector) {
		return (BlockLocation) super.subtract(blockVector);
	}

	public BlockLocation zero() {
		return (BlockLocation) super.zero();
	}

	public BlockLocation set(int x, int y, int z) {
		return (BlockLocation) super.set(x, y, z);
	}

	public BlockLocation copy(Block block) {
		return (BlockLocation) super.copy(block);
	}

	public BlockLocation copy(BlockVector blockVector) {
		return (BlockLocation) super.copy(blockVector);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (o instanceof BlockLocation) {
			BlockLocation that = (BlockLocation) o;
			return (this.x == that.x && this.y == that.y && this.z == that.z
					&& ((this.worldName == null) ? (that.worldName == null)
							: (that.worldName != null && this.worldName.equals(that.worldName))));
		}
		if (o instanceof Block) {
			Block that = (Block) o;
			return (this.x == that.getX() && this.y == that.getY() && this.z == that.getZ() && this.worldName != null
					&& this.worldName.equals(that.getWorld().getName()));
		}
		return false;
	}

	public int hashCode() {
		return this.y << 24 ^ this.x ^ this.z ^ this.worldHash;
	}

	public String toString() {
		return "BlockLocation{worldName='" + this.worldName + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z
				+ '}';
	}

	public BlockLocation clone() {
		return (BlockLocation) super.clone();
	}

}