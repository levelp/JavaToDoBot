package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

/**
 * Помощь (справка) по всем командам
 */
public class HelpCommand extends BotCommand {

    private static final Logger log = Logger.getLogger(HelpCommand.class.getSimpleName());

    // Реестр всех команд
    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "Список всех поддерживаемых команд");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        // Создаём сообщение
        SendMessage msg = new SendMessage();
        // Отправляем его тому кто вызвал команду
        msg.setChatId(chat.getId().toString());
        // В сообщении можно использовать HTML-форматирование
        msg.enableHtml(true);
        // Создаём текст сообщения
        StringBuilder result = new StringBuilder("<b>Команды бота:</b>\n");
        for (IBotCommand cmd : commandRegistry.getRegisteredCommands()) {
            result.append(cmd.toString()).append("\n");
        }
        msg.setText(result.toString());

        try {
            absSender.execute(msg);
        } catch (TelegramApiException e) {
            log.severe(e.getMessage());
        }
    }
}
