package dev.iiahmed.strikesurrender.util;

import dev.iiahmed.strikesurrender.StrikeSurrender;
import net.md_5.bungee.api.ChatColor;

public enum Message {

    ONLY_PLAYERS("only-players", "&cOnly players can execute this command"),
    USAGE("usage", "&cUSAGE: /<command>"),
    NOT_IN_FIGHT("not-in-fight", "&fYou are currently &aNOT &fin a fight"),
    SURRENDERED("surrender", "&fYou have &fsurrendered &ffrom the current fight"),
    SURRENDER_ANNOUNCE("surrender-announce", "&fPlayer &b<player> &fhas surrendered successfully")

    ;

    private final String path, defaultMessage;

    Message(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        StrikeSurrender instance = StrikeSurrender.getInstance();
        String msg = instance.getConfig().getString("message." + path);
        if(msg != null) {
            return translate(msg);
        }
        return translate(defaultMessage);
    }

    private String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
