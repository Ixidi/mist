import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Repositories {

    const val spigot = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    const val sonatypeSnapshots = "https://oss.sonatype.org/content/repositories/snapshots"
    const val sonatypeCentral = "https://oss.sonatype.org/content/repositories/snapshots"

}

fun RepositoryHandler.spigot() {
    maven(Repositories.spigot)
    maven(Repositories.sonatypeSnapshots)
    maven(Repositories.sonatypeCentral)
}