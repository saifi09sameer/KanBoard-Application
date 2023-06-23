import { ITask } from "./task";

export interface Project {
    projectID: number;
    projectName: string;
    projectDescription: string;
    createdBY: string;
    projectCreatedDate: string;
    projectEndDate: string;
    taskList: ITask[];
    employeeList: string[];
  }