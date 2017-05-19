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

package me.boomboompower.all.togglechat.gui;

import me.boomboompower.all.togglechat.utils.CenterStringBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class TutorialGui {

    public static class MainToggleTutorial extends GuiScreen {

        private GuiScreen previousScreen;
        private int pageNumber;

        public MainToggleTutorial(GuiScreen previous, int pageNumber) {
            this.previousScreen = previous;
            this.pageNumber = pageNumber;
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 200, this.height - 25, 150, 20, "Previous"));
            this.buttonList.add(new GuiButton(1, this.width / 2 + 50, this.height - 25, 150, 20, "Next"));
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        @Override
        public void drawScreen(int x, int y, float ticks) {
            drawDefaultBackground();

            // Setup header and footer
            setupInfo();

            writePage();

            buttonList.get(1).enabled = pageNumber < 9;

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
                        new MainToggleTutorial(this, pageNumber--);
                    } else {
                        mc.displayGuiScreen(previousScreen);
                    }
                    break;
                case 1:
                    new MainToggleTutorial(this, pageNumber++);
                    break;
            }
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        private void setupInfo() {
            drawCentered(new CenterStringBuilder("ToggleChat Tutorial", this.width / 2, this.height / 2 - 120));
            drawCentered(new CenterStringBuilder(String.format("Page %s", (pageNumber + 1)), this.width / 2, this.height / 2 - 110));
            drawCentered(new CenterStringBuilder("Any questions? Ask &aboomboompower&r on the forums!", this.width / 2, this.height / 2 + 80));
        }

        private void writePage() {
            switch (pageNumber) {
                case 0:
                    writeInformation(this.width / 2, this.height / 2 - 90, 10,
                            "You may notice, there are MANY different chats that",
                            "can be toggled with this mod.",
                            "",
                            "This is a guide informing you of what each toggle actually does",
                            "",
                            "",
                            "The toggleable chats are listed below."
                    );

                    writeInformation(this.width / 2 - 50, this.height / 2 - 10, 12, EnumChatFormatting.GOLD,
                            "Team",
                            "Join",
                            "Leave",
                            "Guild",
                            "Party",
                            "Shout",
                            "Message"
                    );

                    writeInformation(this.width / 2 + 50, this.height / 2 - 10, 12, EnumChatFormatting.GOLD,
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
                    writeInformation(this.width / 2 - 120, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "Team");
                    writeInformation(this.width / 2 - 120, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&9[TEAM]&r\"",
                            "",
                            "If the &aTeam&r button is &cdisabled&r",
                            "The message will be &cblocked&r!"
                    );

                    writeInformation(this.width / 2 - 120, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "Join");
                    writeInformation(this.width / 2 - 120, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "end with \"&ejoined.&r\"",
                            "",
                            "If the &aJoin&r button is &cdisabled&r",
                            "The message will be &cblocked&r!"
                    );

                    writeInformation(this.width / 2 + 120, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "Leave");
                    writeInformation(this.width / 2 + 120, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "end with \"&eleft.&r\"",
                            "",
                            "If the &aLeave&r button is &cdisabled&r",
                            "The message will be &cblocked&r!"
                    );

                    writeInformation(this.width / 2 + 120, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "Guild");
                    writeInformation(this.width / 2 + 120, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&2Guild >&r\" or \"&aG >&r\"",
                            "",
                            "If the &aGuild&r button is &cdisabled&r",
                            "The message will be &cblocked&r!"
                    );
                    break;
                default:
                    writeInformation(this.width / 2, this.height / 2 - 50, 10,
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
    }

    public static class WhitelistTutorial extends GuiScreen {

        private GuiScreen previousScreen;
        private int pageNumber;

        public WhitelistTutorial(GuiScreen previous, int pageNumber) {
            this.previousScreen = previous;
            this.pageNumber = pageNumber;
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 200, this.height - 25, 150, 20, "Previous"));
            this.buttonList.add(new GuiButton(1, this.width / 2 + 50, this.height - 25, 150, 20, "Next"));
        }

        public void display() {
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            Minecraft.getMinecraft().displayGuiScreen(this);
        }

        @Override
        public void drawScreen(int x, int y, float ticks) {
            drawDefaultBackground();

            // Setup header and footer
            setupInfo();

            writePage();

            buttonList.get(1).enabled = pageNumber < 9;

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
                        new MainToggleTutorial(this, pageNumber--);
                    } else {
                        mc.displayGuiScreen(previousScreen);
                    }
                    break;
                case 1:
                    new MainToggleTutorial(this, pageNumber++);
                    break;
            }
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        private void setupInfo() {
            drawCentered(new CenterStringBuilder("Whitelist Tutorial", this.width / 2, this.height / 2 - 120));
            drawCentered(new CenterStringBuilder(String.format("Page %s", (pageNumber + 1)), this.width / 2, this.height / 2 - 110));
        }

        private void writePage() {
            switch (pageNumber) {
                default:
                    writeInformation(this.width / 2, this.height / 2 - 50, 30,
                            "TODO",
                            "Shall be completed soon\u2122!"
                    );
                    break;
            }
        }
    }

    public static void drawCenteredString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color);
    }

    public static void drawCentered(CenterStringBuilder builder) {
        drawCenteredString(builder.getFontRender(), builder.translateCodes().getMessage(), builder.getX(), builder.getY(), builder.getColor().getRGB());
    }

    public static void writeInformation(int startingX, int startingY, int separation, String... lines) {
        writeInformation(startingX, startingY, separation, EnumChatFormatting.WHITE, lines);
    }

    public static void writeInformation(int startingX, int startingY, int separation, EnumChatFormatting color, String... lines) {
        for (String s : lines) {
            drawCentered(new CenterStringBuilder(color + s, startingX, startingY));
            startingY += separation;
        }
    }
}
