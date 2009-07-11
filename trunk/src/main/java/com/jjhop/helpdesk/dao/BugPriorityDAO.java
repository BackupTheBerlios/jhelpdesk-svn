package com.jjhop.helpdesk.dao;

import java.util.List;

import com.jjhop.helpdesk.model.BugPriority;

public interface BugPriorityDAO {

    List<BugPriority> getAllPriorities();

    BugPriority getById(Long id);

    BugPriority getById(int id);
}
