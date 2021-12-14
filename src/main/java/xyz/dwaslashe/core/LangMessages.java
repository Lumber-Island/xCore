package xyz.dwaslashe.core;

import xyz.dwaslashe.lang.Lang;

import java.util.Arrays;

public class LangMessages {

    private final Lang lang;

    public LangMessages(Lang lang){
        this.lang = lang;
    }

    public void register(){
        lang.register("lang.author", "WuShei");
        lang.register("lang.minecraft_locale", "en-us");
        lang.register("lang.head", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19");
        lang.register("lang.item.slot", 12);
        lang.register("lang.item.name", "&cEnglish &8( &4en-us &8)");
        lang.register("lang.item.lore", Arrays.asList(" &7To select the &cEnglish&7 language",
                " &7click on this icon.",
                " ",
                " &a&m  &2&m>&7 {PLAYERS} users",
                "  &fchose this language so far",
                " ",
                " &f~Translator: WuShei ( &aServer Team &f)"));
        lang.register("lang.chose", "&7Thanks for chose &cEnglish&7.");
        lang.register("lang.already_have", "&7You already have &fthis &7language.");

        lang.register("events.join.message", Arrays.asList("&7Welcome &a{PLAYER} &7in the our server.",
                " &7Players: &a{PLAYERS-ONLINE}&2/{PLAYERS-MAX}",
                " &7Lovely players: &2{PLAYERS-TOTAL}"));

        lang.register("events.join.message_global_first", "&cALERT! &8[&a+&8] &7Welcome &a{PLAYER} &7on the server. Everyone who said to s/he &a\"Hello\" &7receive &afree 5 dolars.");
        lang.register("events.join.message_global", "&8[&a+&8] &7Player &a{PLAYER} &7joined to the server.");
        lang.register("events.gender.alert", "&7Please upset your gender to easier contact with you. That's optional function.");
        lang.register("events.gender.correct.female", "&7Thanks you for chose yours gender &d(FEMALE)\n&7For help you received &afree 5 dolars.");
        lang.register("events.gender.correct.male", "&7Thanks you for chose yours gender &b(MALE)\n&7For help you received &afree 5 dolars.");
        lang.register("events.gender.confirm", "&7Please use this command one time more to &aconfirm &7gender change.");
        lang.register("events.gender.remove", "&7Please use this command one time more to &aconfirm &7gender &cremove&7.");
        lang.register("events.teleport.counter", "&7Teleportation: &c{TIME} to end.");
        lang.register("events.teleport.start", "&7Starting... &7Teleportation will be end in &c{TIME}");

        lang.register("time.days", "day/s");
        lang.register("time.hours", "hour/s");
        lang.register("time.minutes", "min/s");
        lang.register("time.seconds", "sec/s");
        lang.register("time.millis", "ms");

        lang.register("notifications", Arrays.asList(
                "&9[NOTIFY] &8| &7Everyone who chose him gender may attend in meetings and to get married with other people.",
                "&9[NOTIFY] &8| &7If you see cheater please report him here &f-> &a/report cheater [nick]",
                "&9[NOTIFY] &8| &7If you see any bug please report him here &f-> &a/report bug [bug description]",
                "&9[NOTIFY] &8| &7You may buy here cars, mechanism plans and more other thinks. &2[/shop]",
                "&9[NOTIFY] &8| &7Do you want hide chat? Use the command &a/manage",
                "&9[NOTIFY] &8| &7Please remember to &finvite &7your friends to our server. &2[/invite]",
                "&9[NOTIFY] &8| &7If you see anyone trying to damage or harm the server's reputation please report him here &f-> &2/discord&a and create ticket",
                "&9[NOTIFY] &8| &7Do you want hide this message? Use the command &a/notifications",
                "&9[NOTIFY] &8| &7That's first version our server. Any bug, problems, errors, ideas please report here &f-> &a/discord"));
    }
}
