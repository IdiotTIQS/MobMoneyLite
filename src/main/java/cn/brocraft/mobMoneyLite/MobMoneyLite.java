package cn.brocraft.mobMoneyLite;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobMoneyLite extends JavaPlugin {

    private static Economy econ;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe("未找到 Vault 或经济插件，插件已关闭！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        // 注册监听器
        getServer().getPluginManager().registerEvents(
                new cn.brocraft.mobMoneyLite.listener.MobKillListener(this),
                this
        );

        getLogger().info("MobMoneyLite 已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("MobMoneyLite 已关闭！");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}