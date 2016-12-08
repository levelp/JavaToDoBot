package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Список дел
 */
public class TODOList {

    private List<Task> tasks = new ArrayList<>();

    /**
     * @param task Добавить задачу в список
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * @return количество задач в списке
     */
    public int count() {
        return tasks.size();
    }

}