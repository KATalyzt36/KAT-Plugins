package com.example;

import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import net.kat.inventoryids.InventoryIdsPlugin;
import net.kat.katapi.KATapi;
import net.kat.spell2key.Spell2KeyPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class Runner {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(EthanApiPlugin.class, PacketUtilsPlugin.class,
            KATapi.class,
            Spell2KeyPlugin.class,
            InventoryIdsPlugin.class
        );
        RuneLite.main(args);
    }
}