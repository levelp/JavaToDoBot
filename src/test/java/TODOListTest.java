import model.TODOList;
import model.Task;
import org.junit.Assert;
import org.junit.Test;

public class TODOListTest extends Assert {

    /**
     * Добавление задач
     */
    @Test
    public void testSimple() {
        TODOList list = new TODOList();
        Task task = new Task("Вынести настройки для подключения бота в config.properties");
        list.add(task);
        assertEquals(1, list.count());
    }
}
