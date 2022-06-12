package com.github.qumasso.qumassoscoreboards.teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreboardTeam {

    private List<Player> teammates;

    private String name, prefix, suffix;

    private ChatColor color;

    private Map<Team.Option, Team.OptionStatus> options;

    private boolean canSeeFriendlyInvisibles = true, allowFriendlyFire = false;

    public ScoreboardTeam(String name) {
        this(name, null);
    }

    public ScoreboardTeam(String name, ChatColor color) {
        this(name, color, null);
    }

    public ScoreboardTeam(String name, ChatColor color, String prefix) {
        this(name, color, prefix, null);
    }

    public ScoreboardTeam(String name, ChatColor color, String prefix, String suffix) {
        this(name, color, prefix, suffix, null);
    }

    public ScoreboardTeam(String name, ChatColor color, String prefix, String suffix, Map<Team.Option, Team.OptionStatus> options) {
        teammates = new ArrayList<>();
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.suffix = suffix;
        this.options = options;
    }

    public void update(Scoreboard scoreboard) {
        Team team = scoreboard.getTeam(name);
        if (team == null) team = scoreboard.registerNewTeam(name);
        else {
            for (String entry : team.getEntries()) {
                team.removeEntry(entry);
            }
        }
        for (Player teammate : teammates) {
            team.addEntry(teammate.getName());
        }
        if (color != null) team.setColor(color);
        if (prefix != null) team.setPrefix(prefix);
        if (suffix != null) team.setSuffix(suffix);
        Team finalTeam = team;
        if (options != null) options.forEach((option, optionStatus) -> finalTeam.setOption(option, optionStatus));
        team.setCanSeeFriendlyInvisibles(canSeeFriendlyInvisibles);
        team.setAllowFriendlyFire(allowFriendlyFire);
    }

    public List<Player> getTeammates() {
        return teammates;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Map<Team.Option, Team.OptionStatus> getOptions() {
        return options;
    }

    public void setOptions(Map<Team.Option, Team.OptionStatus> options) {
        this.options = options;
    }

    public boolean isCanSeeFriendlyInvisibles() {
        return canSeeFriendlyInvisibles;
    }

    public void setCanSeeFriendlyInvisibles(boolean canSeeFriendlyInvisibles) {
        this.canSeeFriendlyInvisibles = canSeeFriendlyInvisibles;
    }

    public boolean isAllowFriendlyFire() {
        return allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }
}
