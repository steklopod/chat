package by.steklopod.webchat.common

import by.steklopod.webchat.exceptions.InvalidUsernameException
import by.steklopod.webchat.model.ChatMessage
import by.steklopod.webchat.model.MessageType


fun checkUsernameIsNotEmpty(username: String?) {
    if (username == null || username.trim().isEmpty()) {
        throw InvalidUsernameException("Username must not be null")
    }
}

fun prepareUserJoinedChatMessage(username: String): ChatMessage {
    return ChatMessage(
        type = MessageType.JOIN,
        content = "$username joined the chat",
        sender = Constants.SERVER_SENDER
    )
}

fun prepareUserLeftChatMessage(username: String): ChatMessage {
    return ChatMessage(
        type = MessageType.LEAVE,
        content = "$username left the chat",
        sender = Constants.SERVER_SENDER
    )
}

fun prepareSuccessfullyLoggedMessage(username: String): ChatMessage {
    return ChatMessage(
        type = MessageType.LOGIN_ACCEPTED,
        content = "You have successfully logged as $username",
        sender = Constants.SERVER_SENDER
    )
}

fun prepareDeclinedMessage(reason: String?): ChatMessage {
    return ChatMessage(
        type = MessageType.LOGIN_DECLINED,
        content = reason,
        sender = Constants.SERVER_SENDER
    )
}
