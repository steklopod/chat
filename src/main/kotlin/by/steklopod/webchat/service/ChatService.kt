package by.steklopod.webchat.service

import by.steklopod.webchat.common.Constants.PUBLIC_TOPIC_DEST
import by.steklopod.webchat.common.checkUsernameIsNotEmpty
import by.steklopod.webchat.common.prepareDeclinedMessage
import by.steklopod.webchat.common.prepareSuccessfullyLoggedMessage
import by.steklopod.webchat.common.prepareUserJoinedChatMessage
import by.steklopod.webchat.common.prepareUserLeftChatMessage
import by.steklopod.webchat.exceptions.InvalidUsernameException
import by.steklopod.webchat.exceptions.NotAuthorizedUserException
import by.steklopod.webchat.model.ChatMessage
import by.steklopod.webchat.model.MessageType
import by.steklopod.webchat.storage.UserStorage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class ChatService {
    private val logger = LoggerFactory.getLogger(ChatService::class.java)

    @Autowired
    private lateinit var messagingTemplate: SimpMessageSendingOperations

    @Autowired
    private lateinit var userStorage: UserStorage

    fun addUserToChat(sessionId: String, username: String?): ChatMessage {
        return try {
            checkUsernameIsNotEmpty(username)

            userStorage.addUser(sessionId, username!!)
            messagingTemplate.convertAndSend(
                    PUBLIC_TOPIC_DEST,
                prepareUserJoinedChatMessage(username)
            )

            logger.info("User $username joined the chat")
            prepareSuccessfullyLoggedMessage(username)
        } catch (e: InvalidUsernameException) {
            logger.info("Unsuitable name $username")
            prepareDeclinedMessage(e.message)
        }
    }

    fun removeUserFromChat(sessionId: String) {
        userStorage.removeUserBySessionId(sessionId)?.let {
            messagingTemplate.convertAndSend(
                    PUBLIC_TOPIC_DEST,
                prepareUserLeftChatMessage(it)
            )
        }
    }

    fun broadcastMessage(sessionId: String, message: ChatMessage): ChatMessage {
        val username = userStorage.getUserBySessionId(sessionId)
                ?: throw NotAuthorizedUserException("User $sessionId is not authorized")

        return ChatMessage(
            type = MessageType.CHAT,
            content = message.content,
            sender = username
        )
    }

}
