package com.cout970.magneticraft;

import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.items.ItemMeta;
import com.cout970.magneticraft.util.NamedBlock;
import com.cout970.magneticraft.util.NamedItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LangHelper {

    public static List<String> unloc = new ArrayList<>();
    public static List<String> name = new ArrayList<>();

    public static void addName(Object obj, String name) {
        if (obj == null) return;
        if (name == null) return;
        if (obj instanceof ItemStack) {
            LangHelper.put(((ItemStack) obj).getUnlocalizedName(), name);
        } else if (obj instanceof Block) {
            LangHelper.put(((Block) obj).getUnlocalizedName(), name);
        } else if (obj instanceof Item) {
            LangHelper.put(((Item) obj).getUnlocalizedName(), name);
        } else if (obj instanceof Fluid) {
            LangHelper.put(((Fluid) obj).getUnlocalizedName(), name);
        } else if (obj instanceof String) {
            LangHelper.put((String) obj, name);
        }
    }

    public static void put(String a, String b) {
        unloc.add(a);
        name.add(b);
    }

    public static void setupLangFile() {
        File f = new File(Magneticraft.DEV_HOME + "/src/main/resources/assets/magneticraft/lang/en_US.lang");
        Writer w;
        try {
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            for (String s : unloc) {
                if (s.contains("fluid.")) {
                    w.write(s + "=" + name.get(unloc.indexOf(s)) + "\n");
                } else {
                    w.write(s + ".name=" + name.get(unloc.indexOf(s)) + "\n");
                }
            }
            if (!ManagerIntegration.COFH_ENERGY) {
                w.write("kinetic_generator.name=Kinetic Generator\n");
                w.write("rf_alternator.name=RF Alternator\n");
            }
            if (!ManagerIntegration.RAILCRAFT) {
                w.write("rc_alternator.name=RailCraft Charge Alternator\n");
            }
            if (!ManagerIntegration.IC2) {
                w.write("eu_alternator.name=EU Alternator\n");
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerNames() {

        for (NamedItem i : ManagerItems.named) {
            if (i.item instanceof ItemMeta) {
                ItemMeta im = (ItemMeta) i.item;
                for (int it = 0; it < im.getMetaAmount(); it++) {
                    addName(new ItemStack(im, 1, it), im.getName(it, Locale.US));
                }
            } else {
                addName(i.item, i.name);
            }
        }

        for (NamedBlock i : ManagerBlocks.named)
            addName(i.block, i.name);

        //creative tab
        addName("itemGroup", "Magneticraft");

        //fluids
        addName("fluid." + ManagerFluids.STEAM_NAME, "Steam");
        addName("fluid." + ManagerFluids.OIL_NAME, "Crude Oil");
        addName("fluid." + ManagerFluids.HEAVY_OIL, "Heavy Oil");
        addName("fluid." + ManagerFluids.LIGHT_OIL, "Light Oil");
        addName("fluid." + ManagerFluids.NATURAL_GAS, "Natural Gas");
        addName("fluid." + ManagerFluids.HOT_CRUDE, "Hot Crude");
        //fluidblocks
        addName("tile.mg_steam_block", "Steam");
        addName("tile.mg_oil_block", "Crude Oil");
        addName("tile.mg_heavy_oil_block", "Heavy Oil");
        addName("tile.mg_light_oil_block", "Light Oil");
        addName("tile.mg_natural_gas_block", "Natural Gas");
        addName("tile.mg_hot_crude_block", "Hot Crude");

    }
}
