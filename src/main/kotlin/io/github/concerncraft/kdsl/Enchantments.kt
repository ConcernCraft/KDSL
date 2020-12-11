package io.github.concerncraft.kdsl

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.util.registry.Registry

class EnchantmentBuilder(private val stack: ItemStack) {

    fun enchantment(enchantment: Enchantment, level: Int) {
        //no double casts in my household
        val listTag: ListTag = stack.orCreateTag.getOrPutList("Enchantments", 10)
        val tag = CompoundTag()
        tag.putString("id", Registry.ENCHANTMENT.getId(enchantment).toString())
        tag.putShort("lvl", level.toShort())
        listTag.add(tag)
    }

}