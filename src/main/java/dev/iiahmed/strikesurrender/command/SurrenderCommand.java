package dev.iiahmed.strikesurrender.command;

import dev.iiahmed.strikesurrender.util.Message;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.fights.Fight;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SurrenderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Message.ONLY_PLAYERS.toString());
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("StrikePractice.surrender")) {
            player.sendMessage(Message.NO_PERMISSIONS.toString());
            return true;
        }
        StrikePracticeAPI api = StrikePractice.getAPI();
        Fight fight = api.getFight(player);
        if(!api.isInFight(player) || fight == null) {
            player.sendMessage(Message.NOT_IN_FIGHT.toString());
            return true;
        }
        long started = fight.getStarted();
        if(started == 0L) {
            player.sendMessage(Message.NOT_IN_FIGHT.toString());
            return true;
        }
        if(args.length != 0) {
            player.sendMessage(Message.USAGE.toString().replace("<command>", command.getName()));
            return true;
        }

        /* If this is a bestof kit (Expremntial) */
        List<String> teammates = fight.getTeammates(player);
        if(fight.getKit().getBestOf() > 1) {
            Player firstOpponent = Bukkit.getPlayer(fight.getOpponents(player).get(0));
            if(teammates.size() > 1) {
                fight.handleDeath(player);
            } else {
                api.forceWin(firstOpponent);
            }
            player.sendMessage(Message.SURRENDERED.toString());
            for(Player fightPlayer : fight.getPlayersInFight()) {
                if(player == fightPlayer) {
                    continue;
                }
                fightPlayer.sendMessage(Message.SURRENDER_ANNOUNCE.toString().replace("<player>", player.getName()));
            }
            return true;
        }
        /* ---- */

        player.setHealth(0);
        player.sendMessage(Message.SURRENDERED.toString());
        for(Player fightPlayer : fight.getPlayersInFight()) {
            if(player == fightPlayer) {
                continue;
            }
            fightPlayer.sendMessage(Message.SURRENDER_ANNOUNCE.toString().replace("<player>", player.getName()));
        }
        return true;
    }

}
