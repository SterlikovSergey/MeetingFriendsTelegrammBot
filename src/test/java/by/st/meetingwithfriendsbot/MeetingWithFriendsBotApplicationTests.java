package by.st.meetingwithfriendsbot;

import by.st.meetingwithfriendsbot.config.BotProperties;
import by.st.meetingwithfriendsbot.service.UserService;
import by.st.meetingwithfriendsbot.telegram.TelegramBot;
import by.st.meetingwithfriendsbot.telegram.callbacks.CallbackCommandsHandler;
import by.st.meetingwithfriendsbot.telegram.commands.CommandsHandler;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.mockito.Mockito.*;
@RequiredArgsConstructor
@SpringBootTest
class MeetingWithFriendsBotApplicationTests {
    private final BotProperties properties;

    @Test
    void contextLoads() {
    }
    @Test
    public void testHandleTextCommandsSuccessfully() {
        BotProperties botProperties = new BotProperties();
        botProperties.setName(properties.getName());
        botProperties.setToken(properties.getToken());

        CommandsHandler commandsHandler = mock(CommandsHandler.class);
        CallbackCommandsHandler callbackCommandsHandler = mock(CallbackCommandsHandler.class);
        UserService userService = mock(UserService.class);

        TelegramBot telegramBot = new TelegramBot(botProperties, commandsHandler, callbackCommandsHandler, userService);

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("/start");
        when(message.getChat()).thenReturn(chat);
        when(chat.getUserName()).thenReturn("testuser");

        List<PartialBotApiMethod<?>> responses = List.of(new SendMessage("123", "Welcome!"));
        when(commandsHandler.handleCommands(update)).thenReturn(responses);

        telegramBot.onUpdateReceived(update);

        verify(commandsHandler).handleCommands(update);
    }
}
