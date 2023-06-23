export interface ITask {
    taskID: number;
    taskName: string;
    taskDescription: string;
    taskCreatedDate: string;
    taskEndDate: string;
    taskPriority: string;
    taskStage: string;
    taskCreatedBY: string;
    assignTaskEmployees: string[];
}
