package com.github.qumasso.qumassoscoreboards.scoreboards;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public interface ScoreboardFactory {

    AbstractScoreboard registerScoreboard(ScoreboardType type, Function<Player, String> title, Function<Player, List<String>> content);

    void unregisterScoreboard(AbstractScoreboard scoreboard);

}
