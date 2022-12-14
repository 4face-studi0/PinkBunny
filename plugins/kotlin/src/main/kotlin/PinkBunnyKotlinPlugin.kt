import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class PinkBunnyKotlinPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.setupKotlinPlugin()
    }
}

private fun Project.setupKotlinPlugin() {
    apply(plugin = "com.google.devtools.ksp")

    if (hasKotlinAndroidPlugin().not()) {
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
    }

    setupOptIns(
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.ui.test.ExperimentalTestApi",
        "kotlin.experimental.ExperimentalTypeInference",
        "kotlin.time.ExperimentalTime",
        "kotlinx.coroutines.ExperimentalCoroutinesApi",
        "kotlinx.coroutines.FlowPreview"
    )

    tasks.withType<KotlinCompile>().configureEach {
        setSource("build/generated/ksp/main/kotlin")
        setSource("build/generated/ksp/test/kotlin")
    }
}

private fun Project.hasKotlinAndroidPlugin() =
    plugins.hasPlugin("org.jetbrains.kotlin.android")
