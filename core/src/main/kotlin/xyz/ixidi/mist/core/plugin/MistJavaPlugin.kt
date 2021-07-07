package xyz.ixidi.mist.core.plugin

import com.github.shynixn.mccoroutine.SuspendingJavaPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.generator.ChunkGenerator
import java.io.File
import java.io.InputStream
import java.util.logging.Logger

abstract class MistJavaPlugin(private val mistPlugin: MistPlugin) : SuspendingJavaPlugin() {

    final override suspend fun onEnableAsync() {
        mistPlugin applyFor this
    }

    final override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }

    final override suspend fun onLoadAsync() {
        super.onLoadAsync()
    }

    final override fun getCommand(name: String): PluginCommand? {
        return super.getCommand(name)
    }

    final override fun getConfig(): FileConfiguration {
        return super.getConfig()
    }

    final override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? {
        return super.getDefaultWorldGenerator(worldName, id)
    }

    final override fun getFile(): File {
        return super.getFile()
    }

    final override fun getLogger(): Logger {
        return super.getLogger()
    }

    final override fun getResource(filename: String): InputStream? {
        return super.getResource(filename)
    }

    final override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return super.onCommand(sender, command, label, args)
    }

    final override fun onDisable() {
        super.onDisable()
    }

    final override fun onEnable() {
        super.onEnable()
    }

    final override fun onLoad() {
        super.onLoad()
    }

    final override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return super.onTabComplete(sender, command, alias, args)
    }

    final override fun reloadConfig() {
        super.reloadConfig()
    }

    final override fun saveConfig() {
        super.saveConfig()
    }

    final override fun saveDefaultConfig() {
        super.saveDefaultConfig()
    }

    final override fun saveResource(resourcePath: String, replace: Boolean) {
        super.saveResource(resourcePath, replace)
    }

    final override fun toString(): String {
        return super.toString()
    }

}