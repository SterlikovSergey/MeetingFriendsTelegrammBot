package by.st.meetingwithfriendsbot.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandsHandler {
    private final Map<String,Command> commands;

    public CommandsHandler(@Autowired StartCommand startCommand,
                        @Autowired CreateMeetingCommand createMeetingCommand,
                           @Autowired ViewsAllMeetingsCommand viewsAllMeetingsCommand) {
        this.commands = Map.of(
                "/start", startCommand,
                "/createMeetingCommand", createMeetingCommand,
                "Меню встреч", viewsAllMeetingsCommand,
                "Вернуться в главное меню",startCommand
        );
    }

    public SendMessage handleCommands(Update update){
        String command = update.getMessage().getText();
        /*String command = messageText.split(" ")[0];*/
        Long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if(commandHandler != null){
            return commandHandler.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId),"Извините, я не знаю такой команды");
        }
    }
}
