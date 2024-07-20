package net.kat.api.lists.spells;

import com.example.PacketUtils.WidgetInfoExtended;
import lombok.Getter;
import net.runelite.api.SpriteID;

@Getter
public enum SpellInfo {
    BLOOD_BARRAGE(WidgetInfoExtended.SPELL_BLOOD_BARRAGE.getPackedId(), SpellBooks.ANCIENT, 92, SpriteID.SPELL_BLOOD_BARRAGE, "TODO: Complete this", "Blood Barrage"),
    ICE_BARRAGE(WidgetInfoExtended.SPELL_ICE_BARRAGE.getPackedId(), SpellBooks.ANCIENT, 94, SpriteID.SPELL_ICE_BARRAGE, "TODO: Complete this", "Ice Barrage");

    private final int packedId;
    private final int spellBookID;
    private final int minLevelToCast;
    private final int spriteIdSpell;
    private final String infoRequiredRunes;
    private final String name;

    SpellInfo(int packedId, int spellBookID, int minLevelToCast, int spriteIdSpell, String infoRequiredRunes, String name) {
        this.packedId = packedId;
        this.spellBookID = spellBookID;
        this.minLevelToCast = minLevelToCast;
        this.spriteIdSpell = spriteIdSpell;
        this.infoRequiredRunes = infoRequiredRunes;
        this.name = name;
    }
}
