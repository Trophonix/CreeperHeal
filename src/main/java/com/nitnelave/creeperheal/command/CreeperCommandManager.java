package com.nitnelave.creeperheal.command;

import com.nitnelave.creeperheal.block.BurntBlockManager;
import com.nitnelave.creeperheal.block.ExplodedBlockManager;
import com.nitnelave.creeperheal.config.CfgVal;
import com.nitnelave.creeperheal.config.CreeperConfig;
import com.nitnelave.creeperheal.config.WCfgVal;
import com.nitnelave.creeperheal.config.WorldConfig;
import com.nitnelave.creeperheal.utils.CreeperMessenger;
import com.nitnelave.creeperheal.utils.CreeperPermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The command manager for creeperheal.
 * 
 * @author nitnelave
 * 
 */
public class CreeperCommandManager implements CommandExecutor
{
    private final static String GREEN = ChatColor.GREEN.toString(),
                    PURPLE = ChatColor.DARK_PURPLE.toString();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
     * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel,
                             String[] args)
    {
        if (args.length != 0)
        {
            String cmd = args[0];
            //the last argument can be a world
            WorldConfig currentWorld = null;
            World w = Bukkit.getWorld(args[args.length - 1]);
            if (w != null)
                currentWorld = CreeperConfig.getWorld(args[args.length - 1]);

            if (currentWorld == null)
                if (sender instanceof Player)
                    currentWorld = CreeperConfig.getWorld(((Player) sender).getWorld());

            if (cmd.equalsIgnoreCase("creeper"))
                booleanCmd(currentWorld, WCfgVal.CREEPERS, args, commandLabel, "Creepers explosions", sender);

            else if (cmd.equalsIgnoreCase("TNT"))
                booleanCmd(currentWorld, WCfgVal.TNT, args, commandLabel, "TNT explosions", sender);

            else if (cmd.equalsIgnoreCase("fire"))
                booleanCmd(currentWorld, WCfgVal.FIRE, args, commandLabel, "Burnt blocks", sender);

            else if (cmd.equalsIgnoreCase("ghast"))
                booleanCmd(currentWorld, WCfgVal.GHAST, args, commandLabel, "Ghast fireballs explosions", sender);

            else if (cmd.equalsIgnoreCase("custom"))
                booleanCmd(currentWorld, WCfgVal.CUSTOM, args, commandLabel, "Magical explosions", sender);

            else if (cmd.equalsIgnoreCase("waitBeforeHeal") || cmd.equalsIgnoreCase("wbh"))
                integerCmd(CfgVal.WAIT_BEFORE_HEAL, args, commandLabel, "Sets the interval before replacing a block destroyed in an explosion", "seconds", sender);

            else if (cmd.equalsIgnoreCase("waitBeforeHealBurnt") || cmd.equalsIgnoreCase("wbhb"))
                integerCmd(CfgVal.WAIT_BEFORE_HEAL_BURNT, args, commandLabel, "Sets the interval before replacing a burnt block", "seconds", sender);

            else if (cmd.equalsIgnoreCase("blockPerBlockInterval") || cmd.equalsIgnoreCase("bpbi"))
            {
                integerCmd(CfgVal.BLOCK_PER_BLOCK_INTERVAL, args, commandLabel, "Sets the interval between block replacement", "ticks", sender);
                ExplodedBlockManager.rescheduleTask();
            }

            else if (cmd.equalsIgnoreCase("forceHeal") || cmd.equalsIgnoreCase("heal"))
                forceCmd(args, "explosions", sender, currentWorld);

            else if (cmd.equalsIgnoreCase("healBurnt"))
                forceCmd(args, "burnt blocks", sender, currentWorld);

            else if (cmd.equalsIgnoreCase("healNear"))
                healNear(sender, args, commandLabel);

            else if (cmd.equalsIgnoreCase("reload"))
                CreeperConfig.load();

            else if (cmd.equalsIgnoreCase("help"))
                sendHelp(sender, commandLabel);
            else if (cmd.equalsIgnoreCase("on"))
                enable(true, currentWorld, sender);
            else if (cmd.equalsIgnoreCase("off"))
                enable(false, currentWorld, sender);

            else
            {
                return false;
            }

            //in case of a change of setting via a command, write it to the file
            CreeperConfig.write();
        }
        else
        {
            return false;
        }

        return true;
    }

    /*
     * Displays the help according to the permissions of the player.
     */
    private void sendHelp(CommandSender sender, String label)
    {
        sender.sendMessage("creeperheal -- Repair explosions damage and make traps");
        sender.sendMessage("--------------------------------------------");

        boolean admin = true, heal = true, healNear = true, healNearOther = true;

        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            admin = checkPermissions(player, "admin");
            heal = admin || checkPermissions(player, "heal");
            healNearOther = heal || checkPermissions(player, "heal.near.all");
            healNear = healNearOther || checkPermissions(player, "heal.near.self");

        }

        if (!(admin || healNear))
            sender.sendMessage(getMessage("plugin-help-no-commands", null, sender.getName(), null, null, null, null));
        else
        {
            if (admin)
            {
                sender.sendMessage(GREEN + "/" + label + " reload :" + PURPLE
                                   + " reloads the config from the file.");
                sender.sendMessage(GREEN + "/" + label + " (creeper|TNT|ghast|custom|fire) (on/off) (world) :"
                                   + PURPLE + " toggle replacement");
                sender.sendMessage(GREEN + "/" + label + " [on|off] (world) :" + PURPLE
                                   + " toggle CH for this world.");
                sender.sendMessage(GREEN
                                   + "/" + label + " (waitBeforeHeal|wbh) [seconds] :"
                                   + PURPLE
                                   + " Sets the interval before an explosion is replaced to x seconds");
                sender.sendMessage(GREEN + "/" + label + " (waitBeforeHealBurnt|wbhb) [seconds] :" + PURPLE
                                   + " Same for a burnt block");
                sender.sendMessage(GREEN + "/" + label + " (blockPerBlockInterval|bpbi) [ticks] :" + PURPLE
                                   + " Sets the block replacement rate");
            }

            if (heal)
            {
                sender.sendMessage(GREEN + "/" + label + " heal (world) :" + PURPLE + " Heals all explosions.");
                sender.sendMessage(GREEN + "/" + label + " healBurnt (world) :" + PURPLE
                                   + " Heal all burnt blocks.");
            }

            // healNear
            sender.sendMessage(GREEN + "/" + label + " healNear" + (healNearOther ? " (player)" : "")
                               + " :" + PURPLE + " Heals all explosions around"
                               + (healNearOther ? " the given player" : ""));
        }
    }

    /**
     * Handle the commands concerning boolean settings.
     * 
     * @param world
     *            The world in which to change the setting.
     * @param key
     *            The setting to change.
     * @param args
     *            The arguments of the command.
     * @param label
     *            The command the player sent (to account for aliases)
     * @param setting
     *            The name of the setting.
     * @param sender
     *            The sender who performed the command.
     */
    private void booleanCmd(WorldConfig world, WCfgVal key, String[] args, String label, String setting,
                            CommandSender sender)
    {
        if (sender instanceof Player && !checkPermissions((Player) sender, "admin"))
            sender.sendMessage(getMessage("no-permission-command", null, sender.getName(), null, null, null, null));

        if (world == null)
        {
            for (World w : Bukkit.getServer().getWorlds())
                booleanCmd(CreeperConfig.getWorld(w), key, args, label, setting, sender);
            return;
        }

        if (args.length == 1)
            world.setBool(key, !world.getBool(key));
        else if (args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("true"))
            world.setBool(key, true);
        else if (args[1].equalsIgnoreCase("off") || args[1].equalsIgnoreCase("false"))
            world.setBool(key, false);
        else
        {
            sender.sendMessage("/" + label + " " + args[0] + " (on|off|time)");
            sender.sendMessage("Toggles " + setting + " replacement on/off");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + setting + " replacement set to : "
                           + world.getBool(key));
    }

    /**
     * Handle commands concerning integer settings.
     * 
     * @param key
     *            The setting to change.
     * @param args
     *            The command arguments.
     * @param label
     *            The command the player sent (to account for aliases)
     * @param help
     *            The help message.
     * @param unit
     *            The unit in the measurement.
     * @param sender
     *            The command's sender.
     */
    private void integerCmd(CfgVal key, String[] args, String label, String help, String unit,
                            CommandSender sender)
    {
        if (sender instanceof Player && !checkPermissions((Player) sender, "admin"))
            sender.sendMessage(getMessage("no-permission-command", null, sender.getName(), null, null, null, null));

        if (args.length == 2)
        {
            int interval;
            try
            {
                interval = Integer.parseInt(args[1]);
            } catch (NumberFormatException e)
            {
                sender.sendMessage("/" + label + " " + args[0] + " [" + unit + "]");
                sender.sendMessage(help);
                return;
            }
            sender.sendMessage(ChatColor.GREEN + "New interval set to : " + interval + " " + unit);

            CreeperConfig.setInt(key, interval);
        }
        else
        {
            sender.sendMessage("/" + label + " " + args[0] + " [" + unit + "]");
            sender.sendMessage(help);
        }
    }

    /**
     * Handle force commands (i.e. force instant replacement of blocks).
     * 
     * @param args
     *            The command arguments.
     * @param msg
     *            The name of the type of blocks.
     * @param sender
     *            The command's sender.
     * @param currentWorld
     *            The world to which the command must be applied. If null, apply
     *            to all worlds.
     */
    private void
        forceCmd(String[] args, @SuppressWarnings("unused") String msg, CommandSender sender,
                 WorldConfig currentWorld)
    {
        String cmd = args[0];

        if (sender instanceof Player && !checkPermissions((Player) sender, "heal", "admin"))
        {
            sender.sendMessage(getMessage("no-permission-command", null, sender.getName(), null, null, null, null));
            return;
        }

        boolean burnt = cmd.equalsIgnoreCase("healBurnt");
        if (currentWorld == null)
            if (burnt)
                BurntBlockManager.forceReplaceBurnt();
            else
                ExplodedBlockManager.forceReplace();
        else if (burnt)
            BurntBlockManager.forceReplaceBurnt(currentWorld);
        else
            ExplodedBlockManager.forceReplace(currentWorld);

        sender.sendMessage(ChatColor.GREEN + "Explosions healed");
    }

    /**
     * Replace all explosions near a player.
     * 
     * @param sender
     *            The sender. If it is the console, then the command is ignored.
     * @param args
     *            The command arguments.
     * @param label
     *            The command the player sent (to account for aliases)
     */
    private void healNear(CommandSender sender, String[] args, String label)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            boolean hasPermission = checkPermissions(player, "heal", "admin", "heal.near.all");
            Player target;
            if (args.length > 1)
            {
                if (!hasPermission)
                {
                    player.sendMessage(getMessage("no-permission-command", player.getWorld().getName(), sender.getName(), null, null, null, null));
                    return;
                }
                target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null)
                {
                    player.sendMessage(ChatColor.RED
                                       + "This player is not online. /" + label + " healNear <someone>");
                    return;
                }

            }
            else
            {
                hasPermission = hasPermission || checkPermissions(player, "heal.near.self");
                if (!hasPermission)
                {
                    sender.sendMessage(getMessage("no-permission-command", null, sender.getName(), null, null, null, null));
                    return;
                }
                target = player;
            }
            ExplodedBlockManager.replaceNear(target);
        }
        else if (args.length > 1)
        {
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.RED
                                   + "This player is not online. /" + label + " healNear <someone>");
                return;
            }
            ExplodedBlockManager.replaceNear(target);

        }
        else
            sender.sendMessage(ChatColor.RED + "Please give the target's name.");
    }

    /*
     * Enable of disable creeperheal in a world.
     */
    private void enable(boolean enable, WorldConfig currentWorld, CommandSender sender)
    {
        boolean hasPerm = true;
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            hasPerm = checkPermissions(player, "admin");
        }
        if (hasPerm)
        {
            if (currentWorld == null)
            {
                for (World w : Bukkit.getWorlds())
                    enable(enable, CreeperConfig.getWorld(w), sender);
                return;
            }
            currentWorld.setBool(WCfgVal.WORLD_ON, enable);
            sender.sendMessage(GREEN + (enable ? "En" : "Dis") + "abled CH in world : "
                               + currentWorld.getName());
        }
    }

    /*
     * Check if the player has at least one of the permissions.
     */
    private boolean checkPermissions(Player player, String... nodes)
    {
        return CreeperPermissionManager.checkPermissions(player, false, nodes);
    }

    /*
     * Get the formatted message to send to a player.
     */
    private String getMessage(String message, String... values)
    {
        return CreeperMessenger.processMessage(message, values);
    }

}
