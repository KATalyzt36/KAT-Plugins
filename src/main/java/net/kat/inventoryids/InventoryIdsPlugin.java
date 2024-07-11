package net.kat.inventoryids;

import com.example.EthanApiPlugin.EthanApiPlugin;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
	name = "<html><font color=#fa8405>[KAT] <font color=#ffffff>Inventory ID",
	tags = {"katalyzt","panel","util","herramienta"}
)
public class InventoryIdsPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InventoryIdsOverlay overlay;

	@Override
	protected void startUp() throws Exception
	{
		if (!EthanApiPlugin.loggedIn())
		{
			return;
		}
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}
}
