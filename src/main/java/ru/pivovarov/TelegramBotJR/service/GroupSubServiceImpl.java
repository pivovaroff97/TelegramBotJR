package ru.pivovarov.TelegramBotJR.service;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushGroupClient;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupDiscussionInfo;
import ru.pivovarov.TelegramBotJR.repository.GroupSubRepository;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;
import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {
    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;
    private final JavaRushGroupClient javaRushGroupClient;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository,
                               TelegramUserService telegramUserService,
                               JavaRushGroupClient javaRushGroupClient) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
        this.javaRushGroupClient = javaRushGroupClient;
    }

    @Override
    public GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());
        if(groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setLastArticleId(javaRushGroupClient.findLastArticleId(groupDiscussionInfo.getId()));
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public GroupSub save(GroupSub groupSub) {
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(Integer groupId) {
        return groupSubRepository.findById(groupId);
    }

    @Override
    public List<GroupSub> findAll() {
        return groupSubRepository.findAll();
    }
}
