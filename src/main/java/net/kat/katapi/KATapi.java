package net.kat.katapi;

import net.kat.katapi.lists.spells.SpellInfo;
import com.example.EthanApiPlugin.Collections.Widgets;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.Packets.NPCPackets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.plugins.*;

import java.awt.*;
import java.util.Optional;

@Slf4j
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(EthanApiPlugin.class)
@PluginDescriptor(
        name = "<html><font color=#fa8405>[KAT] <font color=#ffffff>API",
        description = "Keep this always enabled to run my plugins!",
        tags = {"katalyzt","example","mod","loader","rlpl"},
        hidden = true,
        enabledByDefault = true
)
public class KATapi extends Plugin {
    static Client client = RuneLite.getInjector().getInstance(Client.class);
    static ClientThread clientThread = (ClientThread) RuneLite.getInjector().getInstance(ClientThread.class);
    //static private PluginManager pluginManager;
    static PluginManager pluginManager = RuneLite.getInjector().getInstance(PluginManager.class);
    static ChatMessageManager chatMessageManager = RuneLite.getInjector().getInstance(ChatMessageManager.class);

    public static boolean checkSpell(SpellInfo spellInfo)
    {
        Widget spell = client.getWidget(spellInfo.getPackedId());

        if (spell == null)
        {
            return false;
        }
        if (client.getVarbitValue(4070) != spellInfo.getSpellBookID())
        {
            sendGameMessage("WRONG SPEELBOOK!", Color.RED);
            return false;
        }
        if (client.getBoostedSkillLevel(Skill.MAGIC) < spellInfo.getMinLevelToCast())
        {
            sendGameMessage("YOU NEED LEVEL " + spellInfo.getMinLevelToCast() + " OR GREATER TO CAST IT!",Color.RED);
            return false;
        }
        if (spell.getSpriteId() == spellInfo.getSpriteIdSpell())
        {
            return true;
        } else {
            sendGameMessage("YOU NEED " + spellInfo.getInfoRequiredRunes() + " TO EACH CAST!",Color.RED);
            return false;
        }
    }

    public static void castSpell(SpellInfo spellToCast, Optional<NPC> npc)
    {
        clientThread.invokeLater(() -> {
            if (KATapi.checkSpell(spellToCast))
            {
                Optional<Widget> spell = Widgets.search().nameContains(spellToCast.getName()).first();
                if (npc.isEmpty())
                {
                    KATapi.sendGameMessage("NPC is empty",Color.RED);
                    return;
                }
                if (spell.isEmpty())
                {
                    KATapi.sendGameMessage("SPELL is empty",Color.RED);
                    return;
                }
                NPCPackets.queueWidgetOnNPC(npc.get(), spell.get());
                return;
            }
        });
    }

    //** *                              UTILS

    // MESSAGES
    public static void sendGameMessage(String message)
    {
        String chatMessage = new ChatMessageBuilder()
                .append(message)
                .build();

        chatMessageManager
                .queue(QueuedMessage.builder()
                        .type(ChatMessageType.CONSOLE)
                        .runeLiteFormattedMessage(chatMessage)
                        .build());
    }

    public static void sendGameMessage(String message, Color color)
    {
        String chatMessage = new ChatMessageBuilder()
                .append(color, message)
                .build();

        chatMessageManager
                .queue(QueuedMessage.builder()
                        .type(ChatMessageType.CONSOLE)
                        .runeLiteFormattedMessage(chatMessage)
                        .build());
    }
}
