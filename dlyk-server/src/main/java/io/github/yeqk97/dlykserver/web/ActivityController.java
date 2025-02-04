package io.github.yeqk97.dlykserver.web;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.yeqk97.dlykserver.model.dto.ActivityDto;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.ActivityService;

@RestController
public class ActivityController
{
    private final ActivityService activityService;

    public ActivityController(final ActivityService activityService)
    {
        this.activityService = activityService;
    }

    @GetMapping("/api/activities")
    public R listActivities(@RequestParam(required = false) final Integer current) {
        Page<ActivityDto> activities = activityService.listActivities(current);
        return R.OK(activities);
    }
}
