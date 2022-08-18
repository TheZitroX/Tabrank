package listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Iterator;
import java.util.Objects;

public class Listeners implements Listener {
    Scoreboard sb;

    public Listeners() {
        this.sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        setUpTeams(this.sb);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();
        String team = player.hasPermission("leitung") ? "01leitung" :
                player.hasPermission("entwickler") ? "02entwickler" : "Player";
        player.sendMessage("dein team ist hoffentlich " + team);

        this.sb.getTeam(team).addPlayer(player);
        switch (team) {
            case "01leitung":
                player.setDisplayName(Objects.requireNonNull(this.sb.getTeam(team)).getPrefix() + player.getDisplayName());
                break;
            default: throw new IllegalArgumentException();
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(this.sb);
        }
    }

    private void setUpTeams(Scoreboard sb) {
        setSbTeam(sb, "01leitung", "test");
        setSbTeam(sb, "02entwickler", "dev");
    }
    private void setSbTeam(Scoreboard sb, String name, String prefix) {
        sb.registerNewTeam(name);
        Objects.requireNonNull(sb.getTeam(name)).setPrefix(prefix);
    }
}
