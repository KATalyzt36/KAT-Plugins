package net.kat.spell2key;

import net.runelite.client.config.*;

@ConfigGroup("Spell2Key")
public interface Spell2KeyConfig extends Config {
    @ConfigSection(
            name = "<html>Spell2Key<br>Version 1.0.0</html>",
            description = "",
            position = 0,
            closedByDefault = true
    )
    String version = "version";
    @ConfigSection(
            name = "Ancient",
            description = "Settings for Ancient spells",
            position = 1,
            closedByDefault = false
    )
    String ancientSection = "ancientSection";
    @ConfigItem(
            keyName = "Blood Barrage",
            name = "Blood Barrage Hotkey",
            description = "Set a key to throw a Blood Barrage",
            position = 2,
            section = ancientSection
    )
    default Keybind bloodKey() {
        return Keybind.NOT_SET;
    }
    @ConfigItem(
            keyName = "Ice Barrage",
            name = "Ice Barrage",
            description = "Set a key to throw an Ice Barrage",
            position = 3,
            section = ancientSection
    )
    default Keybind iceKey() {
        return Keybind.NOT_SET;
    }
}
