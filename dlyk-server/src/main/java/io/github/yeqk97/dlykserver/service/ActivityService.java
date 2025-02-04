package io.github.yeqk97.dlykserver.service;

import org.springframework.data.domain.Page;

import io.github.yeqk97.dlykserver.model.dto.ActivityDto;

public interface ActivityService {
    Page<ActivityDto> listActivities(Integer current);
}
