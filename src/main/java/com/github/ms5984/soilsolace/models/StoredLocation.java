package com.github.ms5984.soilsolace.models;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.UUID;

public class StoredLocation {
    public final int x, y, z;
    public final UUID worldUid;

    private StoredLocation() {
        this.x = this.y = this.z = 0;
        this.worldUid = null;
    }
    public StoredLocation(int x, int y, int z, UUID worldUid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldUid = worldUid;
    }
    public StoredLocation(Location location) {
        this(location.getBlock());
    }
    public StoredLocation(Block block) {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.worldUid = block.getWorld().getUID();
    }
}
