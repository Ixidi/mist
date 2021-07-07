package xyz.ixidi.mist.core.plugin

import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import xyz.ixidi.mist.core.command.CommandBuilder
import xyz.ixidi.mist.core.listener.ListenerHandler

interface MistPluginBuilder {

    fun <T : Event> on(
        clazz: Class<T>,
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        handler: ListenerHandler<T>
    )

    fun <S : CommandSender> command(name: String, commandBuilder: CommandBuilder<S>.() -> Unit)
}

inline fun <reified T : Event> MistPluginBuilder.on(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = true,
    noinline handler: ListenerHandler<T>
) = on(T::class.java, priority, ignoreCancelled, handler)

internal class MistPluginBuilderImpl : MistPluginBuilder {

    private val mistPlugin = MistPlugin()

    override fun <T : Event> on(
        clazz: Class<T>,
        priority: EventPriority,
        ignoreCancelled: Boolean,
        handler: ListenerHandler<T>
    ) {
        mistPlugin.addListener(clazz, priority, ignoreCancelled, handler)
    }

    fun build(): MistPlugin = mistPlugin

}

fun mistPlugin(builder: MistPluginBuilder.() -> Unit): MistPlugin {
    val mistPluginBuilder = MistPluginBuilderImpl()
    builder(mistPluginBuilder)
    return mistPluginBuilder.build()
}