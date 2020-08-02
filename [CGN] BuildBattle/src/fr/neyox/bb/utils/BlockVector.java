package fr.neyox.bb.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class BlockVector implements Cloneable {
	protected int x;

	protected int y;

	protected int z;

	public BlockVector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}

	public BlockVector setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return this.y;
	}

	public BlockVector setY(int y) {
		this.y = y;
		return this;
	}

	public int getZ() {
		return this.z;
	}

	public BlockVector setZ(int z) {
		this.z = z;
		return this;
	}

	public BlockVector add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public BlockVector subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public BlockVector add(BlockVector blockVector) {
		this.x += blockVector.x;
		this.y += blockVector.y;
		this.z += blockVector.z;
		return this;
	}

	public BlockVector subtract(BlockVector blockVector) {
		this.x -= blockVector.x;
		this.y -= blockVector.y;
		this.z -= blockVector.z;
		return this;
	}

	public BlockVector zero() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		return this;
	}

	public BlockVector set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public BlockVector copy(Block block) {
		this.x = block.getX();
		this.y = block.getY();
		this.z = block.getZ();
		return this;
	}

	public BlockVector copy(BlockVector blockVector) {
		this.x = blockVector.x;
		this.y = blockVector.y;
		this.z = blockVector.z;
		return this;
	}

	public Vector toVector() {
		return new Vector(this.x, this.y, this.z);
	}

	public Location toLocation(World world) {
		return new Location(world, this.x, this.y, this.z);
	}

	public Block toBlock(World world) {
		return world.getBlockAt(this.x, this.y, this.z);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BlockVector that = (BlockVector) o;
		return (this.x == that.x && this.y == that.y && this.z == that.z);
	}

	public int hashCode() {
		int result = this.x;
		result = 31 * result + this.y;
		result = 31 * result + this.z;
		return result;
	}

	public String toString() {
		return "BlockVector{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
	}

	protected BlockVector clone() {
		try {
			return (BlockVector) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
