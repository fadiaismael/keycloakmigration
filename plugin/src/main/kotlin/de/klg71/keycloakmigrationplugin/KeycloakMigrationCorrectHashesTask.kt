package de.klg71.keycloakmigrationplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Paths

open class KeycloakMigrationCorrectHashesTask : DefaultTask() {
    @Input
    var adminUser = "admin"

    @Input
    var adminPassword = "admin"

    @Input
    var adminTotp = ""

    @Input
    var migrationFile = "keycloak-changelog.yml"

    @Input
    var baseUrl = "http://localhost:8080"

    @Input
    var realm = "master"

    @Input
    var clientId = "admin-cli"

    @Input
    var parameters = emptyMap<String, String>()

    @Input
    var waitForKeycloak = false

    @Input
    var waitForKeycloakTimeout = 0L

    @Input
    var failOnUndefinedVariables = false

    @Input
    var warnOnUndefinedVariables = true

    @Suppress("unused")
    @TaskAction
    fun migrate() {
        GradleMigrationArgs(adminUser, adminPassword,adminTotp,
                Paths.get(project.projectDir.toString(), migrationFile).toString(),
                baseUrl, realm, clientId, true,
                parameters, waitForKeycloak, waitForKeycloakTimeout, failOnUndefinedVariables, warnOnUndefinedVariables)
                .let {
                    de.klg71.keycloakmigration.migrate(it)
                }
    }

}
