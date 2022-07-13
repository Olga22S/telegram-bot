package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

import java.util.Optional;
import java.util.function.Consumer;

public interface NotificationService {

    Optional<NotificationTask> parse(String notificationBotMessage);

    void schedule(NotificationTask task, Long chatId);

    void notifyAllScheduledTask(Consumer<NotificationTask> notifier);
}
