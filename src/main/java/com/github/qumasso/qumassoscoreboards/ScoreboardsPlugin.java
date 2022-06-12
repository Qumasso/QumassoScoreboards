package com.github.qumasso.qumassoscoreboards;

import com.github.qumasso.qumassoscoreboards.scoreboards.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Function;

public class ScoreboardsPlugin extends JavaPlugin implements Listener, ScoreboardFactory {

    public enum UpdateCase {

        JOIN, QUIT, PLAYER_DEATH, ENTITY_DEATH

    }

    private List<AbstractScoreboard> scoreboards = new ArrayList<>();

    private Map<UpdateCase, Boolean> updateCases = new HashMap<>();

    {
        updateCases.put(UpdateCase.JOIN, true);
        updateCases.put(UpdateCase.QUIT, true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            Boolean flag = updateCases.get(UpdateCase.PLAYER_DEATH);
            if (flag == null || flag == false) return;
            scoreboards.forEach(scoreboard -> scoreboard.update());
        }
        else {
            Boolean flag = updateCases.get(UpdateCase.ENTITY_DEATH);
            if (flag == null || flag == false) return;
            scoreboards.forEach(scoreboard -> scoreboard.update());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Boolean flag = updateCases.get(UpdateCase.JOIN);
        if (flag == null || flag == false) return;
        scoreboards.forEach(scoreboard -> {
            scoreboard.addPlayer(event.getPlayer());
            scoreboard.update();
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        Boolean flag = updateCases.get(UpdateCase.QUIT);
        if (flag == null || flag == false) return;
        scoreboards.forEach(scoreboard -> {
            scoreboard.removePlayer(event.getPlayer());
            scoreboard.update();
        });
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public AbstractScoreboard registerScoreboard(ScoreboardType type, Function<Player, String> title, Function<Player, List<String>> content) {
        AbstractScoreboard scoreboard;
        if (type == ScoreboardType.PUBLIC) scoreboard = new PublicScoreboard(title, content);
        else scoreboard = new PersonalScoreboard(title, content);
        scoreboards.add(scoreboard);
        return scoreboard;
    }

    @Override
    public void unregisterScoreboard(AbstractScoreboard scoreboard) {
        scoreboards.remove(scoreboard);
    }

    public void setCase(UpdateCase updateCase, boolean value) {
        updateCases.put(updateCase, value);
    }

}
