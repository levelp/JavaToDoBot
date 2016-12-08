package commands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Команда /start выполняется при добавлении бота.
 * Выводится помощь (справка) по всем командам
 */
public class StartCommand extends BotCommand {

    private HelpCommand helpCommand;

    public StartCommand(HelpCommand helpCommand) {
        super("start", "Список всех поддерживаемых ботом команд");
        this.helpCommand = helpCommand;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        helpCommand.execute(absSender, user, chat, strings);
    }
}