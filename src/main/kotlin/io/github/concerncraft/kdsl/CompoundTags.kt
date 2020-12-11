package io.github.concerncraft.kdsl

import net.minecraft.nbt.*
import java.util.*

@DslMarker
annotation class CompoundTagDslMarker

@CompoundTagDslMarker
open class CompoundTagBuilder {
    private val tag: CompoundTag = CompoundTag()

    // make a new compound tag, and expand the path in the process
    operator fun String.minus(init: CompoundTagBuilder.() -> Unit) {
        val parent = CompoundTag()
        var deepest = parent
        this.split('.').forEach {
            deepest = deepest._put(it, CompoundTag())
        }

        val subBuilder = CompoundTagBuilder()
        subBuilder.init()
        deepest.copyFrom(subBuilder()) // this is concern i know
        tag.copyFrom(parent)
    }

    operator fun String.invoke(init: CompoundTagBuilder.() -> Unit): CompoundTag {
        val subBuilder = CompoundTagBuilder()
        subBuilder.init()
        return tag._put(this, subBuilder())
    }

    operator fun String.invoke(value: String)  : StringTag = tag._put(this, StringTag.of(value))
    operator fun String.invoke(value: Int)     : IntTag    = tag._put(this, IntTag.of(value))
    operator fun String.invoke(value: Float)   : FloatTag  = tag._put(this, FloatTag.of(value))
    operator fun String.invoke(value: Double)  : DoubleTag = tag._put(this, DoubleTag.of(value))
    operator fun String.invoke(value: Byte)    : ByteTag   = tag._put(this, ByteTag.of(value))
    operator fun String.invoke(value: Short)   : ShortTag  = tag._put(this, ShortTag.of(value))
    operator fun String.invoke(value: Long)    : LongTag   = tag._put(this, LongTag.of(value))
    operator fun String.invoke(value: Boolean) : ByteTag   = tag._put(this, if (value) ByteTag.ONE else ByteTag.ZERO)
    operator fun String.invoke(value: UUID)    : IntArrayTag
            = tag._put(this, NbtHelper.fromUuid(value))


    operator fun invoke(): CompoundTag = tag
}

fun CompoundTag.getOrPut(key: String, def: Tag): Tag = this.get(key) ?: _put(key, def)
fun CompoundTag.getOrPutList(key: String, type: Int, def: ListTag = ListTag()): ListTag
    = if (this.getType(key).toInt() == 9)
        this.getList(key, type)
    else _put(key, def)

internal fun <T: Tag> CompoundTag._put(key: String, tag: T): T {
    this.put(key, tag)
    return tag
}