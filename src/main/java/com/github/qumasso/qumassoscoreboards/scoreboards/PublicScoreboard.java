package com.github.qumasso.qumassoscoreboards.scoreboards;

import com.github.qumasso.qumassoscoreboards.scoreboards.AbstractScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class PublicScoreboard extends AbstractScoreboard {

    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    private List<Player> players = new LinkedList<>();

    public PublicScoreboard(Function<Player, String> title, Function<Player, List<String>> content) {
        super(title, content);
    }

    @Override
    public void update() {
        resetScoreboard(scoreboard);
        String title = this.title.apply(null);
        List<String> content = this.content.apply(null);
        resetScoreboard(scoreboard);
        for (Objective objective : scoreboard.getObjectives()) objective.unregister();
        Objective objective = scoreboard.getObjective(title);
        if (objective == null) objective = scoreboard.registerNewObjective(title, "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i < content.size(); i++) {
            objective.getScore(content.get(i)).setScore(i);
        }
        teams.forEach(team -> team.update(scoreboard));
    }

    @Override
    public void addPlayer(Player player) {
        player.setScoreboard(scoreboard);
        players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

}
