package xyz.dwaslashe.core.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.core.data.User;

import java.text.DecimalFormat;
import java.util.List;

public class EconomyHandler implements Economy {

    private final DecimalFormat format = new DecimalFormat("#,##0.##");

    public User getUser(String name){
        return Main.getInstance().getUserCache().getUser(name);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "xcore-economy";
    }

    @Override
    public boolean hasBankSupport() {
        return true;
    }

    @Override
    public int fractionalDigits() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public String format(double v) {
        return format.format(v);
    }

    @Override
    public String currencyNamePlural() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public String currencyNameSingular() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean hasAccount(String s) {
        return getUser(s) != null;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        return getUser(s).getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        return getBalance(s) >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        EconomyResponse response;
        User user = getUser(s);
        if(user.getMoney() >= v) {
            user.setMoney(user.getMoney() - v);
            response = new EconomyResponse(v, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
        } else response = new EconomyResponse(v, user.getMoney(), EconomyResponse.ResponseType.FAILURE, "");
        return response;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        User user = getUser(s);
        user.setMoney(user.getMoney() + v);
        return new EconomyResponse(v, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    @Deprecated
    public EconomyResponse createBank(String s, String s1) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse deleteBank(String s) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse bankBalance(String s) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse bankHas(String s, double v) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse bankWithdraw(String s, double v) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse bankDeposit(String s, double v) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse isBankOwner(String s, String s1) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse isBankMember(String s, String s1) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public List<String> getBanks() {
        return Main.getInstance().getUserCache().getUserMap().keySet().stream().toList();
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(String s) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(String s, String s1) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
