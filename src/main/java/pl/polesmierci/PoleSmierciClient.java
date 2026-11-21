package pl.polesmierci;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

import java.util.Set;

public class PoleSmierciClient implements ClientModInitializer {
    private static final Set<Item> ITEMS = Set.of(
            Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.LADDER,
            Items.OAK_PLANKS, Items.ACACIA_PLANKS, Items.DARK_OAK_PLANKS,
            Items.OAK_WOOD, Items.DARK_OAK_WOOD,
            Items.DARK_OAK_BUTTON, Items.OAK_BUTTON);

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (!ITEMS.contains(stack.getItem())) return;
            String id = Registries.ITEM.getId(stack.getItem()).toString();
            // usuwa "minecraft:..." i "N component(s)" z F3+H
            lines.removeIf((Text l) -> l.getString().equals(id)
                    || (l.getContent() instanceof TranslatableTextContent t && t.getKey().equals("item.components")));
        });
    }
}
