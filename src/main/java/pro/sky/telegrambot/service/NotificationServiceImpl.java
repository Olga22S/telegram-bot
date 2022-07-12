package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private static final String REGEX_BOT_MESSAGE = "([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final NotificationRepository repository;


    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    public Optional<NotificationTask> parse(String notificationBotMessage) {
        Pattern pattern = Pattern.compile(REGEX_BOT_MESSAGE);
        Matcher matcher = pattern.matcher(notificationBotMessage);
        NotificationTask result = null;
        try{
            if(matcher.find()){
                LocalDateTime notificationDataTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
                String notification = matcher.group(3);
                result = new NotificationTask(notification, notificationDataTime);
            }
        }catch (DateTimeParseException ex){
            logger.error("Failed to parse localDateTime: " + notificationBotMessage + " with pattern " + DATE_TIME_FORMATTER, ex);
        }catch (RuntimeException ex){
            logger.error("Failed to parse notification: " + notificationBotMessage, ex);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void schedule(NotificationTask task, Long chatId) {
        task.setChatId(chatId);
        NotificationTask storedTask = repository.save(task);
        logger.info("Notification task has been stored successfully: " + storedTask);
    }

    @Override
    public void notifyAllScheduledTask(Consumer<NotificationTask> notifier) {
        logger.info("Trigger sending of scheduled notifications");
        Collection<NotificationTask> notifications = repository.getScheduledNotifications();
        logger.info("Found {} notifications, processing...", notifications.size());
        notifications.forEach(task -> {
            notifier.accept(task);
            task.markAsSend();
        });
        repository.saveAll(notifications);
        logger.info("Finish to processing scheduled notifications");
    }
}
