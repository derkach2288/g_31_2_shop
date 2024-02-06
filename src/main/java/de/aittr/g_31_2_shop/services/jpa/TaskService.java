package de.aittr.g_31_2_shop.services.jpa;

import de.aittr.g_31_2_shop.domain.jpa.Task;
import de.aittr.g_31_2_shop.repositories.jpa.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private TaskRepository repository;
    private Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public  void createTask(String description) {
        logger.info(description);
        Task task = new Task(description);
        repository.save(task);
    }

    public List<Task> getLastFiveTasks() {
        List<Task> lastFiveTasks = repository.getLastFiveTasks();
//        lastFiveTasks.sort((task1, task2) -> task1.getExecutedAt().compareTo(task2.getExecutedAt()));
        lastFiveTasks.sort(Comparator.comparing(t -> t.getExecutedAt()));
        return lastFiveTasks;
    }

    public  void createTaskLastAddedProduct(Task task) {
        repository.save(task);
    }

}
