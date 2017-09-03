/*
 *     Copyright (C) 2017 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.boomboompower.togglechat.tutorial;

import me.boomboompower.togglechat.gui.modern.ModernButton;
import me.boomboompower.togglechat.gui.utils.CenterStringBuilder;
import me.boomboompower.togglechat.gui.utils.GuiUtils;
import me.boomboompower.togglechat.utils.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

public class MainTutorialGui extends GuiScreen {

    private GuiScreen previousScreen;

    private int pageNumber;
    private Minecraft mc;

    public MainTutorialGui(GuiScreen previous, int pageNumber) {
        this.previousScreen = previous;
        this.pageNumber = pageNumber;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(0, this.width / 2 - 200, this.height - 25, 150, 20, "Previous"));
        this.buttonList.add(new ModernButton(1, this.width / 2 + 50, this.height - 25, 150, 20, "Next"));
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();

        // Setup header and footer
        setupInfo();

        writePage();

        ((ModernButton) this.buttonList.get(1)).enabled = this.pageNumber < 7;

        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) {
            this.mc.displayGuiScreen(this.previousScreen);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (((ModernButton) button).id) {
            case 0:
                if (this.pageNumber > 0) {
                    new me.boomboompower.togglechat.tutorial.MainTutorialGui(this, this.pageNumber--);
                } else {
                    this.mc.displayGuiScreen(this.previousScreen);
                }
                break;
            case 1:
                new me.boomboompower.togglechat.tutorial.MainTutorialGui(this, this.pageNumber++);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void setupInfo() {
        GuiUtils.drawCentered(new CenterStringBuilder("ToggleChat Tutorial", this.width / 2, this.height / 2 - 115));
        GuiUtils.drawCentered(new CenterStringBuilder(String.format("Page %s", (this.pageNumber + 1)), this.width / 2, this.height / 2 - 105));
        GuiUtils.drawCentered(new CenterStringBuilder("Any questions? Ask &aboomboompower&r on the forums!", this.width / 2, this.height / 2 + 80));

        drawHorizontalLine(this.width / 2 - 80, this.width / 2 + 80, this.height / 2 - 95, Color.WHITE.getRGB());
    }

    private void writePage() {
        switch (pageNumber) {
            case 0:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 80, 10,
                        "You may notice, there are MANY different chats that",
                        "can be toggled with this mod.",
                        "",
                        "This is a guide informing you of what each toggle actually does",
                        "",
                        "The toggleable chats are listed below."
                );

                GuiUtils.writeInformation(this.width / 2 - 60, this.height / 2 - 10, 12, ChatColor.GOLD,
                        "Team",
                        "Join",
                        "Leave",
                        "Guild",
                        "Party",
                        "Shout",
                        "Message"
                );

                GuiUtils.writeInformation(this.width / 2 + 40, this.height / 2 - 10, 12, ChatColor.GOLD,
                        "UHC",
                        "Party invites",
                        "Friend requests",
                        "Spectator",
                        "Colored team",
                        "Housing",
                        "Separators"
                );
                break;
            case 1:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lTeam");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&9[TEAM]&r\"",
                        "",
                        "Especially good for if your teammate is",
                        "spamming the chat or saying something else",
                        "that you believe is not appropriate"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lJoin");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "end with \"&ejoined.&r\"",
                        "",
                        "Useful for people who have a lot of",
                        "friends and constantly recieve messages",
                        "that someone has joined the game."
                );
                break;
            case 2:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lLeave");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "end with \"&eleft.&r\"",
                        "",
                        "Also useful for players with large",
                        "amounts of friends, or for guilds",
                        "with a lot of players. (&aTechnoguild&r)"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lGuild");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&2Guild >&r\" or \"&aG >&r\"",
                        "",
                        "One of the most commonly used features.",
                        "Simply allows you to toggle recieving",
                        "guild messages. Good for large guilds!"
                );
                break;
            case 3:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lParty");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&9Party >&r\"",
                        "",
                        "Ever been in a &9100&r player party?",
                        "They can get pretty hectic and extremely spammy",
                        "Toggle it with a click of a button!"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lShout");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&6[SHOUT]&r\"",
                        "",
                        "VERY useful for ignoring shouts by",
                        "random users before the game starts.",
                        "(Generally they aren't positive)"
                );
                break;
            case 4:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lMessages");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&dTo&r\" or \"&dFrom&r\"",
                        "",
                        "Good for people who are very popular and",
                        "have people messaging them every second.",
                        "Simply slap that &6Messages&r button and move on!"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lUHC");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "start with \"&6[00\u272B]&r\"",
                        "(00 can be any number.)",
                        "",
                        "For those who do not wish to see their",
                        "opponents chat in UHC. Very strategical!"
                );
                break;
            case 5:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lParty invites");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "*may* be a party invite/expiry message",
                        "",
                        "Some people get waaaaay too many party invites",
                        "Due to the fact there is no quick way of toggling",
                        "party invites, you can use this instead!"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lFriend requests");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that start with",
                        "\"&eFriend request from&r\" and a few others",
                        "",
                        "I mean it\'s better to use &9/friend toggle&r",
                        "but whatever, thanks for using the mod!"
                );
                break;
            case 6:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lSpectator");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "begin with \"&7[SPECTATOR]&r\"",
                        "",
                        "Ignoring spectator chat with this amazing",
                        "tool, created specifically to ignore spectators!",
                        "&8&o#BringBackSpecChat"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lColored team");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for chat messages that start with",
                        "\"&e[YELLOW]&r\", \"&d[PURPLE]&r\", \"&c[RED]&r\" etc",
                        "",
                        "Useful for when you\'re playing paintball.",
                        "A lot of people were confused about this,",
                        "the explaination of it can be found her..."
                );
                break;
            case 7:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lHousing");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "Checks for chat messages that ",
                        "begin with \"&e[CO-OWNER]&r\", \"&e[OWNER]&r\" or \"&6[RES]&r\"",
                        "",
                        "Good for when you are in a housing plot",
                        "with many residents/co-owners."
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, ChatColor.GOLD, "&lSeparators");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.WHITE,
                        "Checks for messages that are just",
                        "\"&9-------------------------&r\"",
                        "",
                        "This was intially built into the \"Leave\"",
                        "toggle, but we decided to separate it... Get it...",
                        "&8Im so sorry........"
                );
                break;
            default:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 50, 10,
                        "Sorry, &cno&r information is currently available!",
                        "Please try again later!",
                        "",
                        "",
                        "Sincerely",
                        "&aboomboompower&r"
                );
                break;
        }
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
