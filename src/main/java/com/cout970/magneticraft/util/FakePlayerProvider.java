package com.cout970.magneticraft.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

public class FakePlayerProvider {
    private static FakePlayer FAKE;
    private static GameProfile PROFILE = new GameProfile(UUID.fromString("368ae505-c0b9-4cdd-9d2c-368aef201226"), "[Magneticraft]");

    public static FakePlayer getFakePlayer(WorldServer world) {
        if (FAKE == null) {
            FAKE = FakePlayerFactory.get(world, PROFILE);
        }
        return FAKE;
    }
}
