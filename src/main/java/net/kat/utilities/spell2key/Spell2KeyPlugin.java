package net.kat.utilities.spell2key;

import net.kat.api.KATapi;
import net.kat.api.lists.spells.SpellInfo;
import com.example.EthanApiPlugin.Collections.NPCs;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kat.utilities.spell2key.state.Spell2Cast;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseListener;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

@PluginDescriptor(
        name = "<html><font color=#fa8405>[KAT] <font color=#ffffff>Spell2Key",
        description = "Example description",
        enabledByDefault = false,
        tags = {"Ethan-Vann",
                "kat",
                "mod",
                "loader",
                "rlpl",
                "hotkey",
                "spell",
                "cast"
        }
)
@Slf4j
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(EthanApiPlugin.class)
@SuppressWarnings("unused")
public class Spell2KeyPlugin extends Plugin {
    @Inject
    private Client client;
    @Getter(AccessLevel.PACKAGE)
    @Inject
    private ClientThread clientThread;
    @Inject
    private ScheduledExecutorService executor;
    @Inject
    private ItemManager itemManager;
    @Inject
    private KeyManager keyManager;
    @Inject
    private Notifier notifier;
    @Inject
    private ChatMessageManager chatMessageManager;
    @Inject
    private ConfigManager configManager;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private Spell2KeyConfig config;
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private MouseManager mouseManager;
    
    private Spell2Cast spell2Cast = Spell2Cast.NONE;

    @Provides
    Spell2KeyConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(Spell2KeyConfig.class);
    }

    public boolean bloodTrigger = false;
    public boolean iceTrigger = false;

    @Override
    protected void startUp()
    {
        spell2Cast = Spell2Cast.NONE;

        keyManager.registerKeyListener(bloodHotkeyListener);
        keyManager.registerKeyListener(iceHotkeyListener);

        mouseManager.registerMouseListener(mouseListener);
    }

    @Override
    protected void shutDown()
    {
        spell2Cast = Spell2Cast.NONE;

        keyManager.unregisterKeyListener(bloodHotkeyListener);
        keyManager.unregisterKeyListener(iceHotkeyListener);
        mouseManager.unregisterMouseListener(mouseListener);
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded menuEntryAdded)
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }
        if (menuEntryAdded.getOption().equals("Attack")){
            if (spell2Cast == Spell2Cast.NONE)
            {
                return;
            }
            Optional<NPC> npc = NPCs.search().alive().withId(
                    Objects.requireNonNull(menuEntryAdded.getMenuEntry().getNpc()).getId()
            ).nearestToPoint(
                    Objects.requireNonNull(menuEntryAdded.getMenuEntry().getNpc()).getWorldLocation()
            );
            if (npc.isEmpty())
            {
                KATapi.sendGameMessage("NPC EMPTY");
                return;
            }
            if (spell2Cast == Spell2Cast.BLOOD_BARRAGE){
                KATapi.castSpell(SpellInfo.BLOOD_BARRAGE,npc);
                spell2Cast = Spell2Cast.NONE;
                return;
            } else if (spell2Cast == Spell2Cast.ICE_BARRAGE){
                KATapi.castSpell(SpellInfo.ICE_BARRAGE,npc);
                spell2Cast = Spell2Cast.NONE;
                return;
            }
            return;
        }
    }

    private final HotkeyListener bloodHotkeyListener = new HotkeyListener(() -> config.bloodKey())
    {
        @Override
        public void hotkeyPressed() {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (config.bloodKey().matches(e)) {
                bloodTrigger = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (config.bloodKey().matches(e)) {
                bloodTrigger = false;
            }
        }
    };

    private final HotkeyListener iceHotkeyListener = new HotkeyListener(() -> config.iceKey())
    {
        @Override
        public void hotkeyPressed() {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (config.iceKey().matches(e)) {
                iceTrigger = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (config.iceKey().matches(e)) {
                iceTrigger = false;
            }
        }
    };

    private final MouseListener mouseListener = new MouseListener() {

        @Override
        public MouseEvent mouseClicked(MouseEvent mouseEvent) {
            return mouseEvent;
        }

        @Override
        public MouseEvent mousePressed(MouseEvent mouseEvent) {
            if (bloodTrigger && SwingUtilities.isLeftMouseButton(mouseEvent)) {
                spell2Cast = Spell2Cast.BLOOD_BARRAGE;
                mouseEvent.consume();
            } else if (iceTrigger && SwingUtilities.isLeftMouseButton(mouseEvent)) {
                spell2Cast = Spell2Cast.ICE_BARRAGE;
                mouseEvent.consume();
            }
            return mouseEvent;
        }

        @Override
        public MouseEvent mouseReleased(MouseEvent mouseEvent) {
            spell2Cast = Spell2Cast.NONE;
            return mouseEvent;
        }

        @Override
        public MouseEvent mouseEntered(MouseEvent mouseEvent) {
            return mouseEvent;
        }

        @Override
        public MouseEvent mouseExited(MouseEvent mouseEvent) {
            return mouseEvent;
        }

        @Override
        public MouseEvent mouseDragged(MouseEvent mouseEvent) {
            return mouseEvent;
        }

        @Override
        public MouseEvent mouseMoved(MouseEvent mouseEvent) {
            return mouseEvent;
        }
    };
}