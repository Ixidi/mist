package xyz.ixidi.mist.core.listener

import com.github.shynixn.mccoroutine.contract.CoroutineSession
import com.github.shynixn.mccoroutine.contract.MCCoroutine
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.RegisteredListener
import org.bukkit.plugin.SimplePluginManager
import java.lang.reflect.Method
import kotlin.reflect.KClass

typealias ListenerHandler<T> = suspend T.() -> Unit

interface SuspendingListener<T : Event> : Listener {

    val eventClass: Class<T>
    val priority: EventPriority
    val ignoreCancelled: Boolean

    suspend fun onEvent(event: T)

}

internal class SimpleSuspendingListener<T : Event>(
    override val eventClass: Class<T>,
    override val priority: EventPriority,
    override val ignoreCancelled: Boolean,
    private val listenerHandler: ListenerHandler<T>
) : SuspendingListener<T> {

    override suspend fun onEvent(event: T) {
        listenerHandler(event)
    }

}

fun PluginManager.registerSuspendingEvent(clazz: Class<*>, suspendingListener: SuspendingListener<*>, plugin: Plugin) {
    //TODO cache instances
    val mcCoroutineGetMethod =
        Class.forName("com.github.shynixn.mccoroutine.ExtensionKt").getDeclaredMethod("getMcCoroutine")
    mcCoroutineGetMethod.isAccessible = true
    val mcCoroutine = mcCoroutineGetMethod.invoke(null) as MCCoroutine

    val eventService = mcCoroutine.getCoroutineSession(plugin).eventService
    val eventServiceClass = eventService::class.java

    val coroutineSessionField = eventServiceClass.getDeclaredField("coroutineSession")
    coroutineSessionField.isAccessible = true
    val coroutineSession = coroutineSessionField.get(eventService)

    val onEventMethod = suspendingListener::class.java.methods.first { it.name == "onEvent" }

    val suspendingEventExecutorClass =
        eventServiceClass.declaredClasses.first { it.simpleName == "SuspendingEventExecutor" }
    val suspendingEventExecutorConstructor = suspendingEventExecutorClass.getDeclaredConstructor(Class::class.java,
        Method::class.java,
        CoroutineSession::class.java)
    suspendingEventExecutorConstructor.isAccessible = true
    val suspendingEventExecutor = suspendingEventExecutorConstructor.newInstance(clazz, onEventMethod, coroutineSession)

    val suspendingRegisteredListenerClass =
        eventServiceClass.declaredClasses.first { it.simpleName == "SuspendingRegisteredListener" }
    val suspendingRegisteredListenerConstructor =
        suspendingRegisteredListenerClass.getDeclaredConstructor(Listener::class.java,
            EventExecutor::class.java,
            EventPriority::class.java,
            Plugin::class.java,
            Boolean::class.java)
    suspendingRegisteredListenerConstructor.isAccessible = true
    val suspendingRegisteredListener = suspendingRegisteredListenerConstructor.newInstance(suspendingListener,
        suspendingEventExecutor,
        suspendingListener.priority,
        plugin,
        suspendingListener.ignoreCancelled) as RegisteredListener

    val method = SimplePluginManager::class.java
        .getDeclaredMethod("getEventListeners", Class::class.java)
    method.isAccessible = true

    val handlerList = method.invoke(plugin.server.pluginManager, clazz) as HandlerList
    handlerList.register(suspendingRegisteredListener)
}

