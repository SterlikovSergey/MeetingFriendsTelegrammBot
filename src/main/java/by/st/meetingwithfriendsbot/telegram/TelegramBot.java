package by.st.meetingwithfriendsbot.telegram;

import by.st.meetingwithfriendsbot.config.BotProperties;
import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.service.UserService;
import by.st.meetingwithfriendsbot.telegram.callbacks.CallbackCommandsHandler;
import by.st.meetingwithfriendsbot.telegram.commands.CommandsHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;


import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;



import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandsHandler commandsHandler;

    private final CallbackCommandsHandler callbackCommandsHandler;

    private final BotProperties botProperties;

    private final UserService userService;


    @Autowired
    public TelegramBot(BotProperties botProperties,
                       CommandsHandler commandsHandler,
                       CallbackCommandsHandler callbackCommandsHandler,
                       UserService userService){
        this.botProperties = botProperties;
        this.commandsHandler = commandsHandler;
        this.callbackCommandsHandler = callbackCommandsHandler;
        this.userService = userService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","Start"));
        listOfCommands.add(new BotCommand("/help","Help"));
        try {
            execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));
        }catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().startsWith("/")) {
                    sendMessages(commandsHandler.handleCommands(update));
                    log.info(update.getMessage().getChat().getUserName() + " Запустил бот !");
                } else {
                    sendMessages(commandsHandler.handleCommands(update));
                }
            } else if (update.getMessage().hasLocation()) {
                sendMessages(commandsHandler.handleCommands(update));
                log.info(update.getMessage().getChat().getUserName() + " Запрос локации ⁉️");
            } else if(update.getMessage().hasContact()){
                Contact contact = update.getMessage().getContact();
                log.info(contact.getUserId() + " " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getPhoneNumber());
                sendMessages(commandsHandler.handleCommands(update));
            }
        } else if (update.hasCallbackQuery()) {
            sendMessages(callbackCommandsHandler.handleCallbackCommands(update));
        }
    }

    public void sendMessages(List<PartialBotApiMethod<?>> messages) {
        for (PartialBotApiMethod<?> message : messages) {
            try {
                if (message instanceof SendMessage) {
                    execute((SendMessage) message);
                } else if (message instanceof SendPhoto) {
                    execute((SendPhoto) message);
                } else if (message instanceof SendLocation){
                    execute((SendLocation) message);
                }
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }
}
