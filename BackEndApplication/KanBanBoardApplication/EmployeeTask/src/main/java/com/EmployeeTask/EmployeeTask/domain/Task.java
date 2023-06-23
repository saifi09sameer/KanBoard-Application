package com.EmployeeTask.EmployeeTask.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Task {
    private int taskID;
    private String taskName;
    private String taskDescription;
    private String taskCreatedDate;
    private String taskEndDate;
    private String taskPriority;
    private String taskStage;
    private String taskCreatedBY;
    private Set<String> assignTaskEmployees;
}