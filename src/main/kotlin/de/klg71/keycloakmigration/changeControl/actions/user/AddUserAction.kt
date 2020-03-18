package de.klg71.keycloakmigration.changeControl.actions.user

import de.klg71.keycloakmigration.changeControl.actions.Action
import de.klg71.keycloakmigration.model.AddUser
import de.klg71.keycloakmigration.rest.extractLocationUUID
import de.klg71.keycloakmigration.rest.userByName
import java.util.*

class AddUserAction(
        realm: String? = null,
        private val name: String,
        private val enabled: Boolean = true,
        private val emailVerified: Boolean = true,
        private val attributes: Map<String, List<String>> = mapOf()) : Action(realm) {

    private lateinit var userUuid: UUID

    private val addUser = addUser()

    private fun addUser() = AddUser(name, enabled, emailVerified, attributes)

    override fun execute() {
        client.addUser(addUser, realm()).run {
            userUuid = extractLocationUUID()
        }
    }

    override fun undo() {
        client.userByName(name, realm()).run {
            client.deleteUser(id, realm())
        }
    }

    override fun name() = "AddUser $name"

}
