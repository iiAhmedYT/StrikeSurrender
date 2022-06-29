package dev.iiahmed.strikesurrender;

import dev.iiahmed.strikesurrender.command.SurrenderCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class StrikeSurrender extends JavaPlugin {

    @Getter
    private static StrikeSurrender instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("surrender").setExecutor(new SurrenderCommand());
    }

}
