package by.steklopod.webchat.service

import by.steklopod.webchat.common.Constants
import by.steklopod.webchat.model.ChatMessage
import by.steklopod.webchat.model.MessageType
import by.steklopod.webchat.storage.UserStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommandExecutor {

    @Autowired
    private lateinit var userStorage: UserStorage

    // TODO it should be refactored with the command pattern
    public fun execute(message: ChatMessage): ChatMessage {
        return when (message.content) {
            "/users" -> ChatMessage(
                type = MessageType.COMMAND_RESULT,
                content = userStorage.getAllUsers().toString(),
                sender = Constants.SERVER_SENDER
            )
            else -> ChatMessage(
                type = MessageType.COMMAND_ERROR,
                content = "${message.content} is not a valid command.",
                sender = Constants.SERVER_SENDER
            )
        }
    }
}
