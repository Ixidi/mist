package xyz.ixidi.mist.core.plugin

import com.github.shynixn.mccoroutine.SuspendingJavaPlugin
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import xyz.ixidi.mist.core.listener.ListenerHandler
import xyz.ixidi.mist.core.listener.SimpleSuspendingListener
import xyz.ixidi.mist.core.listener.SuspendingListener
import xyz.ixidi.mist.core.listener.registerSuspendingEvent

class MistPlugin {

    private val listeners = mutableListOf<SuspendingListener<*>>()

    internal fun <T : Event> addListener(
        clazz: Class<T>,
        priority: EventPriority,
        ignoreCancelled: Boolean,
        handler: ListenerHandler<T>
    ) {
        listeners.add(SimpleSuspendingListener(clazz, priority, ignoreCancelled, handler))
    }

    internal infix fun applyFor(javaPlugin: SuspendingJavaPlugin) {
        listeners.forEach {
            javaPlugin.server.pluginManager.registerSuspendingEvent(
                it.eventClass, it, javaPlugin
            )
        }
    }

}