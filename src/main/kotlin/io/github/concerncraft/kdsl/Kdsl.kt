package io.github.concerncraft.kdsl

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.server.command.CommandManager

fun test() {
    CommandRegistrationCallback.EVENT.register {
            dispatcher, _ ->
        dispatcher.register(
            CommandManager
                .literal("kekw")
                .executes {
                    it.source.player.giveItemStack(
                        Items.MUSIC_DISC_13.makeStack {
                            name = +"oh.my.god.whatisthis" // kek
                            //damage = 696969
                            count = 11//4514

                            tags {
                                "taggs.nested.yay" - {
                                    "lmao"("lmao")
                                }
                            }

                            enchantments {
                                enchantment(Enchantments.SHARPNESS, 6969)
                                enchantment(Enchantments.PROTECTION, 420)
                            }
                        }
                    )
                    0
                }
        )
    }
}