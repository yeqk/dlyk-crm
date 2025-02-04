package io.github.yeqk97.dlykserver.service.impl;

import static io.github.yeqk97.dlykserver.utils.Constants.PAGE_SIZE;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.github.yeqk97.dlykserver.model.TActivity;
import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.model.dto.ActivityDto;
import io.github.yeqk97.dlykserver.repository.TActivityRepository;
import io.github.yeqk97.dlykserver.repository.specification.UserSpecification;
import io.github.yeqk97.dlykserver.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService
{
    private final TActivityRepository activityRepository;

    public static final Function<TActivity, ActivityDto> toActivityDto = a -> {
        ActivityDto activityDto = new ActivityDto();
        BeanUtils.copyProperties(a, activityDto);
        activityDto.setOwnerName(a.getOwner() == null ? null : a.getOwner().getName());
        activityDto.setCreateBy(a.getCreateBy() == null ? null : a.getCreateBy().getName());
        activityDto.setEditBy(a.getEditBy() == null ? null : a.getEditBy().getName());
        return activityDto;
    };

    public ActivityServiceImpl(final TActivityRepository activityRepository)
    {
        this.activityRepository = activityRepository;
    }

    @Override
    public Page<ActivityDto> listActivities(final Integer current)
    {
        TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return activityRepository.findAll(
            UserSpecification.filterActivityByScope(user), Pageable.ofSize(PAGE_SIZE).withPage(current)).map(toActivityDto);
    }
}
