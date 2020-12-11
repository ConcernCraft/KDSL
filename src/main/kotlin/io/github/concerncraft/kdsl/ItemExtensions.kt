package io.github.concerncraft.kdsl

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class ItemStackBuilder(item: Item) {
    val stack: ItemStack = ItemStack(item)
    var name: Text
        get() = stack.name
        set(value) { stack.setCustomName(value) }

    var damage: Int
        get() = stack.damage
        set(value) { stack.damage = value }

    var count: Int
        get() = stack.count
        set(value) { stack.count = value }

    operator fun String.unaryPlus(): Text = TranslatableText(this)
    operator fun String.unaryMinus(): Text = LiteralText(this)

    fun tags(init: CompoundTagBuilder.() -> Unit) {
        val builder = CompoundTagBuilder()
        builder.init()
        stack.tag = builder()
    }

    fun enchantments(init: EnchantmentBuilder.() -> Unit) {
        val builder = EnchantmentBuilder(stack)
        builder.init()
    }
}

fun Item.makeStack(init: ItemStackBuilder.() -> Unit): ItemStack {
    val builder = ItemStackBuilder(this)
    builder.init()
    return builder.stack
}