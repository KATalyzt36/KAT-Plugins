//package com.example.NightmareHelper;
//
//import com.example.EthanApiPlugin.EthanApiPlugin;
//import com.example.PacketUtilsPlugin;
//import com.example.Packets.MousePackets;
//import com.example.Packets.WidgetPackets;
//import com.example.gauntletFlicker.QuickPrayer;
//import com.google.inject.Inject;
//import net.runelite.api.ChatMessageType;
//import net.runelite.api.Client;
//import net.runelite.api.events.AnimationChanged;
//import net.runelite.api.events.ChatMessage;
//import net.runelite.api.events.GameTick;
//import net.runelite.api.widgets.WidgetInfo;
//import net.runelite.client.eventbus.Subscribe;
//import net.runelite.client.plugins.Plugin;
//import net.runelite.client.plugins.PluginDependency;
//import net.runelite.client.plugins.PluginDescriptor;
//
//import java.util.Arrays;
//
//import static com.example.gauntletFlicker.QuickPrayer.PROTECT_FROM_MAGIC;
//import static com.example.gauntletFlicker.QuickPrayer.PROTECT_FROM_MELEE;
//import static com.example.gauntletFlicker.QuickPrayer.PROTECT_FROM_MISSILES;
//
//@PluginDependency(PacketUtilsPlugin.class)
//@PluginDependency(EthanApiPlugin.class)
//@PluginDescriptor(
//		name = "NightmareHelper",
//		description = "",
//		enabledByDefault = false,
//		tags = {"ethan"}
//)
//public class NightmareHelperPlugin extends Plugin
//{
//	@Inject
//	Client client;
//	@Inject
//	MousePackets mousePackets;
//	@Inject
//	WidgetPackets widgetPackets;
//	@Inject
//	EthanApiPlugin api;
//	boolean forceTab = false;
//	QuickPrayer shouldPray;
//	boolean cursed = false;
//	int i =10;
//	@Subscribe
//	public void onGameTick(GameTick event)
//	{
//		if(i>0){
//			i--;
//			if(i==0){
//				System.out.println(Arrays.toString(client.getMapRegions()));
//				i=10;
//			}
//		}
//		if (forceTab)
//		{
//			System.out.println("forcing tab");
//			client.runScript(915, 3);
//			forceTab = false;
//		}
//		if (client.getWidget(5046276) == null)
//		{
//			mousePackets.queueClickPacket();
//			widgetPackets.queueWidgetAction(client.getWidget(WidgetInfo.MINIMAP_QUICK_PRAYER_ORB), "Setup");
//			forceTab = true;
//		}
//		handlePrayer();
//		if (api.isQuickPrayerEnabled())
//		{
//			api.togglePrayer();
//		}
//		api.togglePrayer();
//	}
//
//	@Override
//	protected void startUp()
//	{
//		forceTab = false;
//	}
//
//	@Subscribe
//	public void onAnimationChanged(AnimationChanged e)
//	{
//		if (e.getActor() != null && e.getActor().getName() != null && (e.getActor().getName().equalsIgnoreCase("the " +
//				"nightmare") || e.getActor().getName().equalsIgnoreCase("phosani's nightmare")))
//		{
//			if (e.getActor().getAnimation() == 8595)
//			{
//				shouldPray = PROTECT_FROM_MAGIC;
//			}
//			else if (e.getActor().getAnimation() == 8596)
//			{
//				shouldPray = QuickPrayer.PROTECT_FROM_MISSILES;
//			}
//			else if (e.getActor().getAnimation() == 8594)
//			{
//				shouldPray = QuickPrayer.PROTECT_FROM_MELEE;
//			}
//		}
//	}
//	public void handlePrayer(){
//		if (shouldPray == PROTECT_FROM_MAGIC) {
//			if (!cursed) {
//				if(api.isQuickPrayerActive(PROTECT_FROM_MAGIC)){
//					mousePackets.queueClickPacket();
//					widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 12); //quickPrayer magic
//				}
//				return;
//			}
//			if(api.isQuickPrayerActive(PROTECT_FROM_MELEE))
//			{
//				mousePackets.queueClickPacket();
//				widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 14); //quickPrayer melee
//			}
//			return;
//		}
//		if (shouldPray == PROTECT_FROM_MELEE) {
//			if (!cursed) {
//				if(api.isQuickPrayerActive(PROTECT_FROM_MELEE))
//				{
//					mousePackets.queueClickPacket();
//					widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 14); //quickPrayer melee
//				}
//				return;
//			}
//			if(api.isQuickPrayerActive(PROTECT_FROM_MISSILES))
//			{
//				mousePackets.queueClickPacket();
//				widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 13); //quickPrayer range
//			}
//			return;
//		}
//		if (shouldPray == PROTECT_FROM_MISSILES) {
//			if (!cursed) {
//				if(api.isQuickPrayerActive(PROTECT_FROM_MISSILES))
//				{
//					mousePackets.queueClickPacket();
//					widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 13); //quickPrayer range
//				}
//				return;
//			}
//			if(api.isQuickPrayerActive(PROTECT_FROM_MAGIC)){
//				mousePackets.queueClickPacket();
//				widgetPackets.queueWidgetActionPacket(1, 5046276, -1, 12); //quickPrayer magic
//			}
//		}
//	}
//	@Subscribe
//	public void onChatMessage(ChatMessage e)
//	{
//		if (e.getType() != ChatMessageType.GAMEMESSAGE)
//		{
//			return;
//		}
//		if (e.getMessage().contains("the nightmare has cursed you, shuffling your prayers!"))
//		{
//			cursed = true;
//		}
//		if (e.getMessage().contains("you feel the effects of the nightmare's curse wear off."))
//		{
//			cursed = false;
//		}
//	}
//}