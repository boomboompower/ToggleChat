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

package me.boomboompower.all.togglechat.tutorial;

import me.boomboompower.all.togglechat.gui.utils.CenterStringBuilder;
import me.boomboompower.all.togglechat.gui.utils.GuiUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
public class TutorialGui {

    public static class MainToggleTutorialGui extends GuiScreen {

        private GuiScreen previousScreen;
        private int pageNumber;
        private Minecraft mc;

        public MainToggleTutorialGui(GuiScreen previous, int pageNumber) {
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

            buttonList.get(1).enabled = pageNumber < 7;

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
                        new MainToggleTutorialGui(this, pageNumber--);
                    } else {
                        mc.displayGuiScreen(previousScreen);
                    }
                    break;
                case 1:
                    new MainToggleTutorialGui(this, pageNumber++);
                    break;
            }
        }

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        private void setupInfo() {
            GuiUtils.drawCentered(new CenterStringBuilder("ToggleChat Tutorial", this.width / 2, this.height / 2 - 115));
            GuiUtils.drawCentered(new CenterStringBuilder(String.format("Page %s", (pageNumber + 1)), this.width / 2, this.height / 2 - 105));
            GuiUtils.drawCentered(new CenterStringBuilder("Any questions? Ask &aboomboompower&r on the forums!", this.width / 2, this.height / 2 + 80));

            drawHorizontalLine(width / 2 - 80, width / 2 + 80, this.height / 2 - 95, Color.WHITE.getRGB());
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

                    GuiUtils.writeInformation(this.width / 2 - 60, this.height / 2 - 10, 12, EnumChatFormatting.GOLD,
                            "Team",
                            "Join",
                            "Leave",
                            "Guild",
                            "Party",
                            "Shout",
                            "Message"
                    );

                    GuiUtils.writeInformation(this.width / 2 + 40, this.height / 2 - 10, 12, EnumChatFormatting.GOLD,
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
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lTeam");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&9[TEAM]&r\"",
                            "",
                            "Especially good for if your teammate is",
                            "spamming the chat or saying something else",
                            "that you believe is not appropriate"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lJoin");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "end with \"&ejoined.&r\"",
                            "",
                            "Useful for people who have a lot of",
                            "friends and constantly recieve messages",
                            "that someone has joined the game."
                    );
                    break;
                case 2:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lLeave");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "end with \"&eleft.&r\"",
                            "",
                            "Also useful for players with large",
                            "amounts of friends, or for guilds",
                            "with a lot of players. (&aTechnoguild&r)"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lGuild");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&2Guild >&r\" or \"&aG >&r\"",
                            "",
                            "One of the most commonly used features.",
                            "Simply allows you to toggle recieving",
                            "guild messages. Good for large guilds!"
                    );
                    break;
                case 3:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lParty");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&9Party >&r\"",
                            "",
                            "Ever been in a &9100&r player party?",
                            "They can get pretty hectic and extremely spammy",
                            "Toggle it with a click of a button!"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lShout");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&6[SHOUT]&r\"",
                            "",
                            "VERY useful for ignoring shouts by",
                            "random users before the game starts.",
                            "(Generally they aren't positive)"
                    );
                    break;
                case 4:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lMessages");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&dTo&r\" or \"&dFrom&r\"",
                            "",
                            "Good for people who are very popular and",
                            "have people messaging them every second.",
                            "Simply slap that &6Messages&r button and move on!"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lUHC");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "start with \"&6[00\u272B]&r\"",
                            "(00 can be any number.)",
                            "",
                            "For those who do not wish to see their",
                            "opponents chat in UHC. Very strategical!"
                    );
                    break;
                case 5:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lParty invites");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "*may* be a party invite/expiry message",
                            "",
                            "Some people get waaaaay too many party invites",
                            "Due to the fact there is no quick way of toggling",
                            "party invites, you can use this instead!"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lFriend requests");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that start with",
                            "\"&eFriend request from&r\" and a few others",
                            "",
                            "I mean it\'s better to use &9/friend toggle&r",
                            "but whatever, thanks for using the mod!"
                    );
                    break;
                case 6:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lSpectator");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "begin with \"&7[SPECTATOR]&r\"",
                            "",
                            "Ignoring spectator chat with this amazing",
                            "tool, created specifically to ignore spectators!",
                            "&8&o#BringBackSpecChat"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lColored team");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that start with",
                            "\"&e[YELLOW]&r\", \"&d[PURPLE]&r\", \"&c[RED]&r\" etc",
                            "",
                            "Useful for when you\'re playing paintball.",
                            "A lot of people were confused about this,",
                            "the explaination of it can be found her..."
                    );
                    break;
                case 7:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lHousing");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "Checks for chat messages that ",
                            "begin with \"&e[CO-OWNER]&r\", \"&e[OWNER]&r\" or \"&6[RES]&r\"",
                            "",
                            "Good for when you are in a housing plot",
                            "with many residents/co-owners."
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 5, 10, EnumChatFormatting.GOLD, "&lSeparators");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.WHITE,
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
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            mc.displayGuiScreen(this);
        }
    }

    public static class WhitelistTutorialGui extends GuiScreen {
        
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
                        new MainToggleTutorialGui(this, pageNumber--);
                    } else {
                        mc.displayGuiScreen(previousScreen);
                    }
                    break;
                case 1:
                    new MainToggleTutorialGui(this, pageNumber++);
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
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lWhat is it?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "The whitelist feature is designed so that",
                            "you can have a certain chat toggled, and still see",
                            "a message in a toggled chat if the sentance contains",
                            "a word from the whitelist. Weather it be an important",
                            "friends name, or some trigger word to be used in an emergency",
                            "the whitelist feature will always be there to assist you."
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.GOLD, "&lHow do I use it?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, EnumChatFormatting.WHITE,
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
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lHow does the whitelist work?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "All the words in the whitelist are stored in a list",
                            "and whenever a chat message if received, the message is tested",
                            "to see if if contains anything from the whitelist. If it does,",
                            "all of toggle settings are ignored instantly and the message goes",
                            "through. In a technical aspect all the words are stored in an ArrayList",
                            "that is looped through in a containsIgnoreCase fashion."
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.GOLD, "&lWhy does the clearing page have a confirmation box?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, EnumChatFormatting.WHITE,
                            "Previously, there was none which made it easy for users to",
                            "missclick and have all the words stored in their whitelist be",
                            "deleated instantly. The confirmation is just a last-second check",
                            "to make sure you are actually willing to clear the whitelist."
                    );
                    break;
                case 2:
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 85, 10, EnumChatFormatting.GOLD, "&lAre there chats the whitelist doesn\'t work with?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 - 70, 10, EnumChatFormatting.WHITE,
                            "There are a few chat toggles that are known to have issues",
                            "with the whitelist, this is especially true in multi-line chats",
                            "such as friend requests or party invites. Currently there are only",
                            "ideas for a \"smart-mode\" feature. That will check multiple lines.",
                            "If you find a bug, don\'t forget to report it!"
                    );

                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 10, 10, EnumChatFormatting.GOLD, "&lWhere can I find the whitelist source code?");
                    GuiUtils.writeInformation(this.width / 2, this.height / 2 + 25, 10, EnumChatFormatting.WHITE,
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
            FMLCommonHandler.instance().bus().register(this);
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            FMLCommonHandler.instance().bus().unregister(this);
            mc.displayGuiScreen(this);
        }
    }

//    public static class TutorialGuiDeletionConfirmationGui extends GuiScreen {
//
//        private GuiScreen previousScreen;
//        private Minecraft mc;
//
//        public TutorialGuiDeletionConfirmationGui(GuiScreen previous) {
//            this.previousScreen = previous;
//
//            this.mc = Minecraft.getMinecraft();
//        }
//
//        @Override
//        public void initGui() {
//            this.buttonList.add(new GuiButton(0, this.width / 2 - 200, this.height / 2 + 30, 150, 20, "Cancel"));
//            this.buttonList.add(new GuiButton(1, this.width / 2 + 50, this.height / 2 + 30, 150, 20, "Confirm"));
//        }
//
//        @Override
//        public void drawScreen(int x, int y, float ticks) {
//            drawDefaultBackground();
//
//            GuiUtils.drawCentered(new CenterStringBuilder("Are you sure you wish to remove tutorials", this.width / 2, this.height / 2 - 60));
//            GuiUtils.drawCentered(new CenterStringBuilder("&4&lDOING SO IS PERMANENT AND MAY CAUSE INSTABILITES!", this.width / 2, this.height / 2 + 20).translateCodes());
//
//            super.drawScreen(x, y, ticks);
//        }
//
//        @Override
//        protected void keyTyped(char c, int key) throws IOException {
//            if (key == 1) {
//                mc.displayGuiScreen(previousScreen);
//            }
//        }
//
//        @Override
//        protected void actionPerformed(GuiButton button) {
//            switch (button.id) {
//                case 0:
//                    mc.displayGuiScreen(previousScreen);
//                    break;
//                case 1:
//                    try {
//                        // TODO Add method to remove
//                        sendChatMessage("Successfully removed tutorials!");
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        sendChatMessage("An error occured whilst removing tutorials");
//                        sendChatMessage("Check console for more information!");
//                    }
//                    mc.displayGuiScreen(null);
//                    break;
//            }
//        }
//
//        @Override
//        public boolean doesGuiPauseGame() {
//            return false;
//        }
//
//        @Override
//        public void sendChatMessage(String message) {
//            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "T" + EnumChatFormatting.BLUE + "C" + EnumChatFormatting.DARK_GRAY + " > " + EnumChatFormatting.GRAY + message));
//        }
//
//        public void display() {
//            FMLCommonHandler.instance().bus().register(this);
//        }
//
//        @SubscribeEvent
//        public void onClientTick(TickEvent.ClientTickEvent event) {
//            FMLCommonHandler.instance().bus().unregister(this);
//            mc.displayGuiScreen(this);
//        }
//    }
}
