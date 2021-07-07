package xyz.ixidi.mist.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import xyz.ixidi.mist.core.command.argument.Argument
import xyz.ixidi.mist.core.plugin.MistJavaPlugin
import xyz.ixidi.mist.core.plugin.mistPlugin
import xyz.ixidi.mist.core.plugin.on

val plugin = mistPlugin {

    on<PlayerJoinEvent> {
        player.sendMessage("Test1")
        withContext(Dispatchers.IO) {
            launch {
                repeat(10) {
                    println(Thread.currentThread().name + " a) $it")
                    delay(1500)
                }
            }
            launch {
                repeat(20) {
                    println(Thread.currentThread().name + "b) $it")
                    delay(1000)
                }
            }
        }
        player.sendMessage("Done")
    }

    command<Player>("test") {
        description = "Desc."

        execution(
            Argument<String>()
        ) { player, first ->
            player.sendMessage(first)
        }
    }

}

//TODO annotation processor
class CorePlugin : MistJavaPlugin(plugin)