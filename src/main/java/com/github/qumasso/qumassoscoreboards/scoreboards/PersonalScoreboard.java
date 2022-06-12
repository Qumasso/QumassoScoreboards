package com.github.qumasso.qumassoscoreboards.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PersonalScoreboard extends AbstractScoreboard {

    private Map<Player, Scoreboard> players = new HashMap<>();

    public PersonalScoreboard(Function<Player, String> title, Function<Player, List<String>> content) {
        super(title, content);
    }

    @Override
    public void update() {
        players.forEach((player, scoreboard) -> {
            resetScoreboard(scoreboard);
            String title = this.title.apply(player);
            List<String> content = this.content.apply(player);
            resetScoreboard(scoreboard);
            for (Objective objective : scoreboard.getObjectives()) objective.unregister();
            Objective objective = scoreboard.getObjective(title);
            if (objective == null) objective = scoreboard.registerNewObjective(title, "dummy", title);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            for (int i = 0; i < content.size(); i++) {
                objective.getScore(content.get(i)).setScore(i);
            }
            teams.forEach(team -> team.update(scoreboard));
        });
    }

    @Override
    public void addPlayer(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        players.put(player, scoreboard);
        player.setScoreboard(scoreboard);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public List<Player> getPlayers() {
        return players.keySet().stream().toList();
    }

}
