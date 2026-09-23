package argha.example.notification_processing_system.repository;

import argha.example.notification_processing_system.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
