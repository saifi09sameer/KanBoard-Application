package com.EmployeeTask.EmployeeTask.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO
{
    private String employeeEmail;
    private JSONObject jsonObject;
}
