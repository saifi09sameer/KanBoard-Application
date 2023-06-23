package com.EmployeeTask.EmployeeTask.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data

public class Project {
    private int projectID;
    private String projectName;
    private String projectDescription;
    private String createdBY;
    private String projectCreatedDate;
    private String projectEndDate;
    private List<Task> taskList;
    private Set<String> employeeList;
}
