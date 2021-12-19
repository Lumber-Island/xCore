package xyz.dwaslashe.resources.helpers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.dwaslashe.core.Main;

import java.util.function.Consumer;

public class TeleportHelper {

    private static final Helper helper = new Helper();

    private final String name;

    private Player player;
    private int counter;

    private BukkitTask lastRequest;

    private boolean teleportRequest;

    public TeleportHelper(Player player) {
        this.name = player.getName();
        this.player = player;
    }

    public void teleport(Location to, int time, Consumer<Player> counter, Consumer<EndReason> reasonConsumer) {
        if (teleportRequest) {
            lastRequest.cancel();
            reasonConsumer.accept(EndReason.NEW);
        }
        if (time == 0) {
            player.teleport(new Location(player.getWorld(), to.getX(), to.getY(), to.getZ(), player.getLocation().getPitch(), player.getLocation().getYaw()));
            reasonConsumer.accept(EndReason.END);
            return;
        }
        player.setLastDamage(0);
        this.teleportRequest = true;
        this.counter = time;
        this.lastRequest = new BukkitRunnable() {
            final Location from = player.getLocation();

            @Override
            public void run() {
                if (player.getLastDamage() != 0) {
                    reasonConsumer.accept(EndReason.DAMAGE);
                    teleportRequest = false;
                    cancel();
                }
                if (!player.isOnline()) {
                    reasonConsumer.accept(EndReason.LOGOUT);
                    teleportRequest = false;
                    cancel();
                }
                if (!helper.locationEquals(from, player.getLocation())) {
                    reasonConsumer.accept(EndReason.MOVE);
                    teleportRequest = false;
                    cancel();
                }
                if (TeleportHelper.this.counter --> 0) {
                    counter.accept(player);
                } else {
                    reasonConsumer.accept(EndReason.END);
                    player.teleport(new Location(player.getWorld(), to.getX(), to.getY(), to.getZ(), player.getLocation().getPitch(), player.getLocation().getYaw()));
                    teleportRequest = false;
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public int getCounter() {
        return counter;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public enum EndReason {
        MOVE,
        END,
        LOGOUT,
        DAMAGE,
        NEW
    }
}
