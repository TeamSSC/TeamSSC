package com.sparta.teamssc.domain.team.weekProgress.service;

import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;

import java.util.List;


public interface WeekProgressService {
     WeekProgress getWeekProgressById(Long id);
     WeekProgress createWeekProgress(String name);
     WeekProgress updateWeekProgress(Long id, String name);
     void deleteWeekProgress(Long id);
     List<WeekProgress> getAllWeekProgress();

     }