package ru.pivovarov.TelegramBotJR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;

@Repository
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}
