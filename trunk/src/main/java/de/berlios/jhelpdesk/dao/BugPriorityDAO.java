package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.BugPriority;

public interface BugPriorityDAO {

    List<BugPriority> getAllPriorities();

    BugPriority getById(Long id);

    BugPriority getById(int id);
}
