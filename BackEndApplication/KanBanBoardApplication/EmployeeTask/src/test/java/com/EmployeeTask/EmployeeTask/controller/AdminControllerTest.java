//package com.EmployeeTask.EmployeeTask.controller;
//
//
//import com.EmployeeTask.EmployeeTask.domain.Project;
//import com.EmployeeTask.EmployeeTask.services.IAdminService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminControllerTest {
//    @Mock
//    IAdminService adminService;
//
//    @InjectMocks
//    AdminController adminController;
//
//    private MockMvc mockMvc;
//
//    @Test
//    public void countNumberOFProjectAndTaskTestSuccess() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//
//        Map<String, Integer> counts = new HashMap<>();
//        counts.put("projects", 5);
//        counts.put("tasks", 10);
//
//        when(adminService.countNumberOFProjectAndTask()).thenReturn(counts);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/Admin/countNumberOFProjectAndTask")
//                        .requestAttr("employeeRole", "Admin"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(adminService, times(1)).countNumberOFProjectAndTask();
//    }
//
//
//    @Test
//    public void countNumberOFProjectAndTaskTestFailure() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//
//        Map<String, Integer> counts = new HashMap<>();
//        counts.put("projects", 3);  // Set an expected count different from the actual count
//        counts.put("tasks", 8);     // Set an expected count different from the actual count
//
//        when(adminService.countNumberOFProjectAndTask()).thenReturn(counts);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/Admin/countNumberOFProjectAndTask")
//                        .requestAttr("employeeRole", "Admin"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(adminService, times(1)).countNumberOFProjectAndTask();
//    }
//
//
//    @Test
//    public void getTop5ProjectsTestSuccess() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/Admin/getTop5Projects")
//                        .requestAttr("employeeRole", "Admin"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(adminService, times(1)).getTop5Projects();
//    }
//
//
//    @Test
//    public void getTop5ProjectsTestFailure() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//
//        List<Project> expectedProjects = new ArrayList<>();
//        // Add some projects to the expected list
//
//        when(adminService.getTop5Projects()).thenReturn(expectedProjects);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/Admin/getTop5Projects")
//                        .requestAttr("employeeRole", "Admin"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(adminService, times(1)).getTop5Projects();
//    }
//
//
//    @Test
//    public void removeEmployeeTestSuccess() throws Exception {
//        String employeeEmail = "test@admin.com";
//
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/Admin/removeEmployee/{employeeEmail}", employeeEmail))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(adminService, times(1)).removeEmployee(employeeEmail);
//    }
//}
