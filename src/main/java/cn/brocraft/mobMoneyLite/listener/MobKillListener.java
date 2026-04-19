package cn.brocraft.mobMoneyLite.listener;

import cn.brocraft.mobMoneyLite.MobMoneyLite;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.text.DecimalFormat;

public class MobKillListener implements Listener {

    private final MobMoneyLite plugin;
    private final Economy econ;

    public MobKillListener(MobMoneyLite plugin) {
        this.plugin = plugin;
        this.econ = MobMoneyLite.getEconomy();
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        // 只处理生物
        if (!(entity.getKiller() instanceof Player player)) {
            return;
        }

        if (!plugin.getConfig().getBoolean("enabled")) {
            return;
        }

        // 可配置：是否仅在击杀敌对生物时发放奖励（默认开启）
        boolean hostileOnly = plugin.getConfig().getBoolean("hostile-only", true);
        if (hostileOnly && !(entity instanceof Enemy)) {
            return;
        }

        EntityType type = entity.getType();

        double money = getMoney(type);

        // 随机金额
        if (plugin.getConfig().getBoolean("use-random")) {
            double min = plugin.getConfig().getDouble("random-range.min");
            double max = plugin.getConfig().getDouble("random-range.max");
            money = min + (Math.random() * (max - min));
        }

        // 发钱
        econ.depositPlayer(player, money);

        // 格式化
        String format = plugin.getConfig().getString("decimal-format");
        DecimalFormat df = new DecimalFormat(format);
        String moneyStr = df.format(money);

        // 发送消息
        if (plugin.getConfig().getBoolean("message.enabled")) {
            String msg = plugin.getConfig().getString("message.format")
                    .replace("%mob%", type.name())
                    .replace("%money%", moneyStr);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    private double getMoney(EntityType type) {
        ConfigurationSection mobs = plugin.getConfig().getConfigurationSection("mobs");

        if (mobs != null && mobs.contains(type.name())) {
            return mobs.getDouble(type.name());
        }

        return plugin.getConfig().getDouble("default-money");
    }
}