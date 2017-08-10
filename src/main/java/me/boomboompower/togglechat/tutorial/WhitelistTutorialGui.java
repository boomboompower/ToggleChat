/*
 *     Copyright (C) 2016 boomboompower
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

/*
 * The most hard-coded messaging system you'll ever see!
 *
 * CREATED TO BE DELETED WITHOUT REPERCUSSIONS
 * CHECKS ARE MADE IN THE OPTIONS CLASS <INIT> METHOD
 */
public class WhitelistTutorialGui extends GuiScreen {

    private GuiScreen previousScreen;
    private int pageNumber;
    private Minecraft mc;

    public WhitelistTutorialGui(GuiScreen previous, int pageNumber) {
        this.previousScreen = previous;
        this.pageNumber = pageNumber;

        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 200, this.height - 25, 150, 20, "Previous"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 50, this.height - 25, 150, 20, "Next"));
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();

        // Setup header and footer
        setupInfo();

        writePage();

        buttonList.get(1).enabled = pageNumber < 2;

        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void keyTyped(char c, int key) throws IOException {
        if (key == 1) {
            mc.displayGuiScreen(previousScreen);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                if (pageNumber > 0) {
                    new MainTutorialGui(this, pageNumber--);
                } else {
                    mc.displayGuiScreen(previousScreen);
                }
                break;
            case 1:
                new MainTutorialGui(this, pageNumber++);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void setupInfo() {
        GuiUtils.drawCentered(new CenterStringBuilder("Whitelist Tutorial", this.width / 2, this.height / 2 - 115));
        GuiUtils.drawCentered(new CenterStringBuilder(String.format("Page %s", (pageNumber + 1)), this.width / 2, this.height / 2 - 105));

        drawHorizontalLine(width / 2 - 80, width / 2 + 80, this.height / 2 - 95, Color.WHITE.getRGB());
    }

    private void writePage() {
        switch (pageNumber) {
            case 0:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lWhat is it?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "The whitelist feature is designed so that",
                        "you can have a certain chat toggled, and still see",
                        "a message in a toggled chat if the sentance contains",
                        "a word from the whitelist. Weather it be an important",
                        "friends name, or some trigger word to be used in an emergency",
                        "the whitelist feature will always be there to assist you."
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.GOLD, "&lHow do I use it?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, ChatColor.WHITE,
                        "If you wish to add a certain word to the whitelist",
                        "simply open the main whitelist gui on the previous screen",
                        "and type in whatever you wish to input to the whitelist.",
                        "",
                        "The only limitation to the whitelist is that spaces cannot",
                        "be used. This is an intended feature as it goes against the",
                        "idea of the whitelist being a simple word-based system."
                );
                break;
            case 1:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lHow does the whitelist work?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "All the words in the whitelist are stored in a list",
                        "and whenever a chat message if received, the message is tested",
                        "to see if if contains anything from the whitelist. If it does,",
                        "all of toggle settings are ignored instantly and the message goes",
                        "through. In a technical aspect all the words are stored in an ArrayList",
                        "that is looped through in a containsIgnoreCase fashion."
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.GOLD, "&lWhy does the clearing page have a confirmation box?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, ChatColor.WHITE,
                        "Previously, there was none which made it easy for users to",
                        "missclick and have all the words stored in their whitelist be",
                        "deleated instantly. The confirmation is just a last-second check",
                        "to make sure you are actually willing to clear the whitelist."
                );
                break;
            case 2:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, ChatColor.GOLD, "&lAre there chats the whitelist doesn\'t work with?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, ChatColor.WHITE,
                        "There are a few chat toggles that are known to have issues",
                        "with the whitelist, this is especially true in multi-line chats",
                        "such as friend requests or party invites. Currently there are only",
                        "ideas for a \"smart-mode\" feature. That will check multiple lines.",
                        "If you find a bug, don\'t forget to report it!"
                );

                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, ChatColor.GOLD, "&lWhere can I find the whitelist source code?");
                GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, ChatColor.WHITE,
                        "All code for the mod is available at:",
                        "&9https://github.com/boomboompower/ToggleChat",
                        "Feel free to contribute if you wish, all help is appreciated!"
                );
                break;
            default:
                GuiUtils.writeInformation(this.width / 2, this.height / 2 - 50, 30,
                        "TODO",
                        "Shall be completed soon\u2122!"
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
        mc.displayGuiScreen(this);
    }
}
