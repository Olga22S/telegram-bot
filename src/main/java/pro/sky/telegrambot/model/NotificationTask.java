package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    public NotificationTask() {

    }

    public LocalDateTime getSentDate(){
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate){
        this.sentDate = sentDate;
    }

    public enum NotificationStatus{
        SCHEDULED,
        SENT,
    }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    private String notificationMessage;
    private LocalDateTime notificationDate;
    private LocalDateTime sentDate;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.SCHEDULED;

    public NotificationTask(String notificationMessage, LocalDateTime notificationDate) {
        this.notificationMessage = notificationMessage;
        this.notificationDate = notificationDate;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof NotificationTask)){
            return false;
        }
        NotificationTask that = (NotificationTask) o;
        return id.equals(that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(notificationMessage, that.notificationMessage) && Objects.equals(notificationDate, that.notificationDate) && Objects.equals(sentDate, that.sentDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationMessage, notificationDate, sentDate, status);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationDate=" + notificationDate +
                ", sentDate=" + sentDate +
                ", status=" + status +
                '}';
    }
}

