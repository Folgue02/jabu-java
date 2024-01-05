package me.folgue.jabu.tasks;

import java.util.*;

/**
 *
 * @author folgue
 */
class TaskRepository {
    private static TaskRepository instance;

    public Map<String, Task> tasks = new HashMap<>();

    private TaskRepository() {
    }

    private static TaskRepository getInstance() {
        if (instance == null)
            instance = new TaskRepository();

        return instance;
    }

    /**
     * Registers a task in the repository. <b>This can only happen if the name of 
     * the task didn't exist already for another task, and if the type of the new
     * task is not the same as any already registered ones</b>.
     * @param name Name for the new task
     * @param newTask The new task
     * @return {@code true} if it was added, {@code false} if it couldn't be added 
     * due to the restrictions listed above.
     */
    public static boolean registerTask(String name, Task newTask) {
        var repository = getInstance();
        for (var entrySet : repository.tasks.entrySet()) {
            if (entrySet.getValue().getClass().equals(newTask.getClass())
                    || name.equals(entrySet.getKey())) {
                return false;
            }
        }

        repository.tasks.put(name, newTask);
        return true;
    }

    /**
     * Returns a task identified by the provided name if it exists.
     * @param taskName Name of the task to be returned.
     * @return {@code Optional.of(task)} if there is a task related to the provided 
     * name, or {@code Optional.empty()} if there is no task related to the provided
     * name.
     */
    public static Optional<Task> getTask(String taskName) {
        return getInstance().tasks.entrySet().stream()
                .filter(es -> es.getKey().equals(taskName))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}