package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

/**
 * Помощь (справка) по всем командам
 */
public class ListCommand extends BotCommand {

    private static final Logger log = Logger.getLogger(ListCommand.class.getSimpleName());


    public ListCommand() {
        super("list", "Список всех задач");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);

        StringBuilder result = new StringBuilder("<b>Список всех задач:</b>\n");
        result.append("1. Задача 1\n");
        result.append("2. Задача 2\n");
        msg.setText(result.toString());

        try {
            absSender.execute(msg);
        } catch (TelegramApiException e) {
            log.severe(e.getMessage());
        }
    }
}
