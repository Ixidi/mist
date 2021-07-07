package xyz.ixidi.mist.core.command

import org.bukkit.command.CommandSender
import xyz.ixidi.mist.core.command.argument.Argument

class CommandBuilder<S : CommandSender>(
    val name: String
) {

    var description = "Default desc."
    var aliases = listOf<String>()

    internal var arguments = listOf<Argument>()
    internal var handler: suspend (S, List<*>) -> Unit = { _, _ -> }

    fun <FIRST> execution(first: Argument<FIRST>, handler: suspend (S, FIRST) -> Unit) {

    }
}