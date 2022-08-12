package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Appeal;
import ru.mis2022.repositories.AppealRepository;
import ru.mis2022.service.entity.AppealService;


@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    //todo заменить на save()
    @Override
    public Appeal persist(Appeal appeal) {
        return appealRepository.save(appeal);
    }

    //todo удалить
    @Override
    public Appeal merge(Appeal appeal) {
        return appealRepository.save(appeal);
    }
}
