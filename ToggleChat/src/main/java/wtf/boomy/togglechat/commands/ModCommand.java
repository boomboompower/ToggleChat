/*
 *     Copyright (C) 2021 boomboompower
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

package wtf.boomy.togglechat.commands;

import wtf.boomy.mods.modernui.threads.ThreadFactory;
import wtf.boomy.mods.modernui.uis.ChatColor;
import wtf.boomy.togglechat.ToggleChatMod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

/**
 * An enhanced CommandBase implementation which contains patches to the core CommandBase class.
 *
 * @author boomboompower
 * @since 3.0.0
 */
public abstract class ModCommand extends CommandBase {
    
    // Async thread handler.
    private final ThreadFactory threadFactory = new ThreadFactory("ModCommand");
    
    // The mod instance.
    protected ToggleChatMod mod;
    
    public ModCommand(ToggleChatMod mod) {
        this.mod = mod;
    }
    
    /**
     * Default command usage information
     *
     * @param sender the sender who executed this command
     *
     * @return the message to send this player if {@link net.minecraft.command.WrongUsageException} is
     * thrown
     */
    public String getCommandUsage(ICommandSender sender) {
        return ChatColor.RED + "Usage: /" + getCommandName();
    }
    
    /**
     * Patch for CommandAliases to allow for null aliases without causing issues with the Minecraft
     * client internally (basically just a crash).
     *
     * @return a never-null list of command aliases.
     */
    public final List<String> getCommandAliases() {
        List<String> aliases = getAliases();
        
        // Null aliases could crash the client
        // Depending on the MC Version
        // Use the super call if it is null
        if (aliases == null) {
            System.err.println(getClass().getSimpleName()
                    + " ["
                    + getCommandName()
                    + "] tried to register a command with null aliases. Avoiding a game crash");
            
            aliases = super.getCommandAliases();
        }
        
        return aliases;
    }
    
    // Leave out @Override incase running on newer MC versions where a different method will be used.
    public final void processCommand(ICommandSender sender, final String[] args) {
        try {
            if (shouldMultithreadCommand(args)) {
                this.threadFactory.runAsync(() -> {
                    String[] temp = args;
                    
                    // Threaded removal of unneeded arguments.
                    if (shouldRemoveBlankArgs()) {
                        temp = removeBlankArgs(temp);
                    }
                    
                    onCommand(sender, temp);
                });
            } else {
                String[] fixedArgs = args;
                
                // Remove unneeded arguments.
                if (shouldRemoveBlankArgs()) {
                    fixedArgs = removeBlankArgs(args);
                }
                
                // Execute our command.
                onCommand(sender, fixedArgs);
            }
        } catch (Exception ex) {
            sendMessage(ChatColor.RED + "An error occurred whilst running this command.");
            
            ex.printStackTrace();
        }
    }
    
    /**
     * A safer way to execute a command. Errors which occur here will not cause the game to crash.
     *
     * @param sender the sender of the command
     * @param args   the arguments of the command
     */
    public abstract void onCommand(ICommandSender sender, String[] args);
    
    /**
     * Returns a list of our aliases for this Command
     *
     * @return a list of command aliases
     */
    public abstract List<String> getAliases();
    
    /**
     * Make everyone able to execute this command regardless of their privileges on the client or
     * server.
     *
     * @param sender the sender to check for permissions
     *
     * @return true if the sender can use this command.
     */
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
    
    /**
     * Return true if the command should be run on its own thread.
     *
     * @param args the arguments of the command
     *
     * @return true if the command should be async
     */
    protected boolean shouldMultithreadCommand(String[] args) {
        return false;
    }
    
    /**
     * Should the CommandHandler strip any arguments in a command which are considered "blank" or
     * empty?
     *
     * @return true if blank args should be stripped.
     */
    protected boolean shouldRemoveBlankArgs() {
        return false;
    }
    
    /**
     * Code to strip blank arguments from a command if required. Adds all Strings which are valid to
     * an ArrayList, then converts this ArrayList to a String[] list.
     *
     * @param incomingArgs the incoming player arguments.
     *
     * @return the revised arguments
     */
    protected final String[] removeBlankArgs(String[] incomingArgs) {
        if (incomingArgs.length == 0) {
            return incomingArgs;
        }
        
        List<String> revised = new ArrayList<>();
        
        // Search every string.
        for (String input : incomingArgs) {
            // Add any strings which aren't null or empty to the list
            if (input != null && input.trim().isEmpty()) {
                revised.add(input);
            }
        }
        
        // If nothing has happened don't bother using it.
        if (revised.isEmpty()) {
            return incomingArgs;
        }
        
        // Set the args to our revised array
        incomingArgs = revised.toArray(new String[0]);
        
        return incomingArgs;
    }
    
    /**
     * Sends a raw message to the client.
     *
     * @param message the message to send
     */
    protected void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
    }
    
    /**
     * Sends a message to the client with SkinChanger branding out the front
     *
     * @param message the message to send
     */
    protected void sendBrandedMessage(String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
                ChatColor.AQUA + "ToggleChat" + ChatColor.GOLD + " > " + ChatColor.GRAY + message));
    }
    
    /// ----------------------------------- Compatibility --------------------------------
    
    // For 1.12.2 functionality
    public final String getName() {
        return getCommandName();
    }
    
    public final String getUsage(ICommandSender sender) {
        return getCommandUsage(sender);
    }
    
    public final void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        processCommand(sender, args);
    }
    
    public final boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public final int getRequiredPermissionLevel() {
        return 0;
    }
}

