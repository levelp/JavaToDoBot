package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

/**
 * Просто отвечает на ваши слова
 */
public class HelloCommand extends BotCommand {

    private static final Logger log = Logger.getLogger(HelloCommand.class.getSimpleName());

    public HelloCommand() {
        super("hello", "Тестовая команда: скажи боту Привет :)");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String userName = chat.getUserName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName() + " " + user.getLastName();
        }

        StringBuilder msg = new StringBuilder("Привет, ").append(userName).append("! ");
        if (arguments != null && arguments.length > 0) {
            msg.append("\n");
            msg.append(String.format("Вы написали: \"%s\"", String.join(" ", arguments)));
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(msg.toString());

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.severe(e.getMessage());
        }
    }
}
