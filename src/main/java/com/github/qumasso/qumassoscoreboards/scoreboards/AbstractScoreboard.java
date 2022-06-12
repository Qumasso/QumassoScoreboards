package com.github.qumasso.qumassoscoreboards.scoreboards;

import com.github.qumasso.qumassoscoreboards.teams.ScoreboardTeam;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractScoreboard {

    protected List<ScoreboardTeam> teams = new ArrayList<>();

    protected Function<Player, String> title;

    protected Function<Player, List<String>> content;

    protected Map<Player, Scoreboard> scoreboards = new HashMap<>();

    public AbstractScoreboard(Function<Player, String> title, Function<Player, List<String>> content) {
        this.title = title;
        this.content = content;
    }

    public abstract void update();

    public abstract void addPlayer(Player player);

    public abstract void removePlayer(Player player);

    public abstract List<Player> getPlayers();

    public void setTitle(Function<Player, String> title) {
        this.title = title;
    }

    public void setContent(Function<Player, List<String>> content) {
        this.content = content;
    }

    public List<ScoreboardTeam> getTeams() {
        return teams;
    }

    public Function<Player, String> getTitle() {
        return title;
    }

    public Function<Player, List<String>> getContent() {
        return content;
    }

    public Map<Player, Scoreboard> getScoreboards() {
        return scoreboards;
    }

    protected final void resetScoreboard(Scoreboard scoreboard) {
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
    }

}
