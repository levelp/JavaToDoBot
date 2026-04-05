import commands.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Пример Telegram-бота
 */
public class JavaToDoBot extends TelegramLongPollingCommandBot {
    private static final Logger log = Logger.getLogger(JavaToDoBot.class.getSimpleName());
    private static String BOT_USERNAME; // Имя пользователя для бота
    private static String BOT_TOKEN; // Токен для бота

    private JavaToDoBot() throws IOException {
        super(BOT_TOKEN);
        // Регистрация всех доступных команд бота
        register(new HelloCommand());
        register(new ListCommand());
        register(new MulTableCommand());

        // Помощь со списком и описанием всех команд
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        // Команда /start тоже будет показывать помощь по командам
        register(new StartCommand(helpCommand));

        // Если пришла не команда, а простое сообщение
        registerDefaultAction((absSender, message) -> {
            // Создаём сообщение для отправки пользователю
            SendMessage msg = new SendMessage();
            // Кому отправить сообщение: тому, кто прислал исходное сообщение
            msg.setChatId(message.getChatId().toString());
            // Текст сообщения
            msg.setText("Неизвестная команда: '" +
                    message.getText() +
                    "'. Список команд бота " + Emoji.AMBULANCE);
            try {
                absSender.execute(msg); // Отправляем сообщение
            } catch (TelegramApiException e) {
                log.severe(e.getMessage());
            }
            // Выводим помощь по командам
            helpCommand.execute(absSender,
                    message.getFrom(), message.getChat(), new String[]{});
        });
    }

    public static void main(String[] args) throws IOException {
        // Считываем настройки бота из конфигурационного файла
        Properties config = new Properties();
        InputStream stream = JavaToDoBot.class.getResourceAsStream("/config.properties");
        if (stream == null) {
            System.out.println("Файл с настройками config.properties не найден");
            System.out.println("Вам нужно зарегистировать своего бота у бота @BotFather");
            System.out.println("И затем создать в каталоге resources файл \"config.properties\":");
            System.out.println("bot.username=...имя вашего бота...");
            System.out.println("bot.token=...@токен от BotFather...");
            return;
        }
        config.load(stream);
        // Для регистрации @BotFather
        BOT_USERNAME = config.getProperty("bot.username"); // Имя бота в Telegram
        System.out.println("Бот для Telegram: https://telegram.me/" + BOT_USERNAME + " или @" + BOT_USERNAME);
        BOT_TOKEN = config.getProperty("bot.token"); // Токен бота
        // Получить имя и токен можно у @BotFather

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Регистируем и запускаем бота
            telegramBotsApi.registerBot(new JavaToDoBot());
        } catch (TelegramApiException e) {
            log.severe(e.getMessage());
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        // Если пришло сообщение
        if (update.hasMessage()) {
            Message message = update.getMessage();
            // Если в сообщении есть текст (также может быть место: message.hasLocation())
            if (message.hasText()) {
                //create an object that contains the information to send back the message
                SendMessage msg = new SendMessage();
                msg.setChatId(message.getChatId().toString()); // Отправляем сообщение тому кто прислал исходное сообщение
                msg.setText("Добавляю задачу: \"" + message.getText() + "\"");
                try {
                    execute(msg); // Отправляем сообщение ;)
                } catch (TelegramApiException e) {
                    // Если ошибка при попытке отправить сообщение => печатаем её
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
