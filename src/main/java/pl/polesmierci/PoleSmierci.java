package pl.polesmierci;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class PoleSmierci implements ModInitializer {
    private static final Style PLAIN = Style.EMPTY.withItalic(false);

    private static MutableText t(String s, Formatting c) {
        return Text.literal(s).setStyle(PLAIN.withColor(c));
    }

    private static MutableText rgb(String s, int rgb) {
        return Text.literal(s).setStyle(PLAIN.withColor(TextColor.fromRgb(rgb)));
    }

    private static MutableText bold(String s, Formatting c) {
        return Text.literal(s).setStyle(PLAIN.withColor(c).withBold(true));
    }

    /** linijka z punktorem na poczatku */
    private static MutableText line(MutableText bullet, MutableText... parts) {
        MutableText out = bullet;
        for (MutableText p : parts) out.append(p);
        return out;
    }

    private static MutableText x() { return t("× ", Formatting.DARK_GRAY); }
    private static MutableText dotOrange() { return t("• ", Formatting.GOLD); }
    private static MutableText dotPurple() { return rgb("• ", 0xA83DE0); }

    private static Text glyph(String ch) {
        return Text.literal(ch).setStyle(PLAIN.withColor(Formatting.WHITE));
    }

    /** "Kliknij [myszka] aby uzyc" */
    private static MutableText click() {
        return t("Kliknij ", Formatting.GREEN)
                .append(Text.literal("").setStyle(PLAIN.withColor(Formatting.WHITE)))
                .append(t(" aby użyć", Formatting.GREEN));
    }

    private static void set(DefaultItemComponentEvents.ModifyContext ctx, Item item,
                            Text name, List<Text> lore, String style) {
        ctx.modify(item, b -> {
            b.add(DataComponentTypes.ITEM_NAME, name);
            b.add(DataComponentTypes.LORE, new LoreComponent(lore));
            if (style != null) b.add(DataComponentTypes.TOOLTIP_STYLE, Identifier.of("minecraft", style));
        });
    }

    @Override
    public void onInitialize() {
        // ---------- Pole Smierci (zlote jablko) ----------
        List<Text> pole = List.of(
                line(t("✖ ", Formatting.DARK_GRAY), t("W obrębie ", Formatting.WHITE), t("5 kratek", Formatting.GREEN), t(" dookoła zabieramy", Formatting.WHITE)),
                line(t("✖ ", Formatting.DARK_GRAY), t("każdemu możliwość używania ", Formatting.WHITE), t("elytry", Formatting.GRAY), t(" na kilka sekund", Formatting.WHITE)),
                line(t("✖ ", Formatting.DARK_GRAY), t("oraz tworzymy ", Formatting.WHITE), t("strefy z laserami", Formatting.GOLD), t(", które", Formatting.WHITE)),
                line(t("✖ ", Formatting.DARK_GRAY), t("zadają ", Formatting.WHITE), t("obrażenia", Formatting.RED), t(" każdemu kto stanie im na drodze", Formatting.WHITE)),
                Text.empty(),
                click());

        // ---------- Lod na patyku (drabinka) ----------
        List<Text> lod = List.of(
                line(x(), t("Po uderzeniu gracza nadajemy mu oraz", Formatting.WHITE)),
                line(x(), t("sobie ", Formatting.WHITE), t("efekt lewitacji", Formatting.AQUA), t(" na określony czas", Formatting.WHITE)));

        // ---------- Dzwonek szkolny (oak planks) ----------
        List<Text> dzwonek = List.of(
                line(x(), t("Po użyciu w powietrzu tworzymy ", Formatting.WHITE), t("fale hałasu", Formatting.AQUA), t(" tam", Formatting.WHITE)),
                line(x(), t("gdzie się patrzymy a gracze w obrębie nie mogą", Formatting.WHITE)),
                line(x(), t("używać ", Formatting.WHITE), t("elytry", Formatting.GRAY), t(" i zostają ", Formatting.WHITE), t("przyciągnięci", Formatting.RED), t(" w dół", Formatting.WHITE)),
                Text.empty(),
                line(x(), bold("Uwaga!", Formatting.DARK_RED)),
                line(x(), t("Przedmiot działa tylko powyżej określonej kratki", Formatting.RED)),
                line(x(), t("oraz obejmuje graczy, którzy mają pod sobą blok", Formatting.RED)),
                Text.empty(),
                click());

        // ---------- Lampion (acacia planks) ----------
        List<Text> lampion = List.of(
                line(dotOrange(), t("Po użyciu otrzymujemy ", Formatting.WHITE), t("regenerację", Formatting.RED), t(" na kilka sekund", Formatting.WHITE)),
                Text.empty(),
                click());

        // ---------- Zamek z piasku (dark oak planks) ----------
        List<Text> zamek = List.of(
                line(dotOrange(), t("Po użyciu ", Formatting.WHITE), t("tworzymy", Formatting.GOLD), t(" dookoła", Formatting.WHITE)),
                line(dotOrange(), t("nas ", Formatting.WHITE), t("zamek z piasku", Formatting.YELLOW), t(" i zamykamy w nim", Formatting.WHITE)),
                line(dotOrange(), t("wszystkich graczy w obrębie na określony czas", Formatting.WHITE)));

        // ---------- Kamien sily (oak wood) ----------
        List<Text> sila = List.of(
                line(dotPurple(), t("Ten kamień użyjesz u ", Formatting.WHITE), rgb("SZAMANA", 0xB57CFF), t(",", Formatting.WHITE)),
                line(dotPurple(), t("dzięki niemu możesz ulepszyć", Formatting.WHITE)),
                line(dotPurple(), t("1 z 3 perków kategorii ", Formatting.WHITE), t("SIŁA", Formatting.RED)));

        // ---------- Kamien zrecznosci (dark oak wood) ----------
        List<Text> zrecznosc = List.of(
                line(dotPurple(), t("Ten kamień użyjesz u ", Formatting.WHITE), rgb("SZAMANA", 0xB57CFF), t(",", Formatting.WHITE)),
                line(dotPurple(), t("dzięki niemu możesz ulepszyć", Formatting.WHITE)),
                line(dotPurple(), t("1 z 3 perków kategorii ", Formatting.WHITE), t("ZRĘCZNOŚĆ", Formatting.YELLOW)));

        // ---------- Smoczy odlamek (dark oak button) ----------
        List<Text> smok = List.of(
                glyph(""),
                Text.empty(),
                glyph(""));

        // ---------- Miecz Hydry (oak button) ----------
        List<Text> hydra = List.of(
                Text.empty(),
                glyph(""),
                glyph(""));

        DefaultItemComponentEvents.MODIFY.register(ctx -> {
            set(ctx, Items.GOLDEN_APPLE, glyph(""), pole, "pole_smierci");
            set(ctx, Items.ENCHANTED_GOLDEN_APPLE, glyph(""), pole, "pole_smierci");
            set(ctx, Items.LADDER, glyph(""), lod, null);
            set(ctx, Items.OAK_PLANKS, glyph(""), dzwonek, "pole_smierci");
            set(ctx, Items.ACACIA_PLANKS, glyph(""), lampion, "pole_smierci");
            set(ctx, Items.DARK_OAK_PLANKS, glyph(""), zamek, "pole_smierci");
            set(ctx, Items.OAK_WOOD, glyph(""), sila, "szaman");
            set(ctx, Items.DARK_OAK_WOOD, glyph(""), zrecznosc, "szaman");
            set(ctx, Items.DARK_OAK_BUTTON, glyph(""), smok, "smok");
            set(ctx, Items.OAK_BUTTON, glyph(""), hydra, null);
        });
    }
}
