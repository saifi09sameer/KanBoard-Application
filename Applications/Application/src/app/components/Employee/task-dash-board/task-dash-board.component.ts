import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/Model/Project';
import { ITask } from 'src/app/Model/task';
import { ProjectService } from 'src/app/Services/Project/project.service';
import { CreateTaskComponent } from '../create-task/create-task.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { TasksService } from 'src/app/Services/Task/tasks.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ViewTaskComponent } from '../view-task/view-task.component';
import { AssignTaskComponent } from '../assign-task/assign-task.component';
import { ViewProjectComponent } from '../view-project/view-project.component';
import html2canvas from 'html2canvas';
import { saveAs } from 'file-saver';
import { NotificationService } from 'src/app/Services/Notification/notification.service';




@Component({
  selector: 'app-task-dash-board',
  templateUrl: './task-dash-board.component.html',
  styleUrls: ['./task-dash-board.component.css']
})

export class TaskDashBoardComponent implements OnInit {



  backgroundColor: string = 'white'; // Initial background color

  changeBackgroundColor() {
    // Generate a random color
    const randomColor = '#' + Math.floor(Math.random() * 16777215).toString(16);

    // Set the background color
    this.backgroundColor = randomColor;
  }


  
  loginEmployee: any;
  projectID: any;
  projectName: string = "";
  projectCreatedBy: string = "";
  showFooter: boolean[] = [];
  employeeList: string[] = [];
  task: ITask[] = [];
  inprogress: ITask[] = [];
  review: ITask[] = [];
  done: ITask[] = [];

  project!: Project;


  constructor(private actRoute: ActivatedRoute, private fb: FormBuilder,
    private taskService: TasksService,
    private projectService: ProjectService,
    private msg: SnackbarsService,
    private dialog: MatDialog,
    private router: Router,
    private renderer: Renderer2,
    

  ) {

  }

  ngOnInit(): void {
    this.loginEmployee = localStorage.getItem("employeeEmail");
    this.storePerameterProjectID();
    this.getProject();


  }



  captureScreenshot() {
    const element = document.documentElement;
    html2canvas(element).then((canvas) => {
      canvas.toBlob((blob) => {
        if (blob !== null) {
          saveAs(blob, 'screenshot.png');
          this.msg.showSuccess('Capture Screenshot ');
        } else {
          this.msg.showError('Failed to capture screenshot ');
        }
      });
    });
  }


  getDuration(item: any): number {
    const startDate = new Date();
    const endDate = new Date(item.taskEndDate);
    const duration = Math.floor(Math.abs(endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24)); 
    return duration;
  }
  
  
  

  


  viewProject() {
    const dialogRef = this.dialog.open(ViewProjectComponent, {
      data: { projectId: this.projectID }
    });


    dialogRef.afterClosed().subscribe(result => {

    });
  }

  navigateToWinDashboard() {
    const data = {
      projectID: this.projectID,
      projectCreatedBy: this.projectCreatedBy
    };
    this.router.navigate(['/recycleBin', data]);
  }

  storePerameterProjectID() {
    this.actRoute.paramMap.subscribe(params => {
      this.projectID = params.get('projectId');
    });

  }
  getProject() {
    this.projectService.getProjectBasedOnID(this.projectID).subscribe(
      (response) => {
        this.project = response;
        this.employeeList = this.project.employeeList;
        // this.task = this.project.taskList;
        this.projectCreatedBy = this.project.createdBY;
        this.projectName = this.project.projectName;
        console.log(JSON.stringify(this.project.taskList.length));
        this.task = [];
        this.inprogress = [];
        this.review = [];
        this.done = [];
        for (let i = 0; i < this.project.taskList.length; i++) {
          if (this.project.taskList[i].taskStage === "To Do") {
            this.task.push(this.project.taskList[i]);
            console.log(this.project.taskList[i].taskStage);
          } else if (this.project.taskList[i].taskStage === "In Progress") {
            this.inprogress.push(this.project.taskList[i]);
          } else if (this.project.taskList[i].taskStage === "Review") {
            this.review.push(this.project.taskList[i]);
          } else if (this.project.taskList[i].taskStage === "Done") {
            this.done.push(this.project.taskList[i]);
          }
        }

      },
      (error) => {
        if (error.status == 404) {
          this.msg.showError("Project Not Found....");
        } else {
          this.msg.showError("Server Error....");
        }
      }
    );
  }






  viewTask(task: ITask) {
    const dialogRef = this.dialog.open(ViewTaskComponent, {
      data: {
        task: task,
        projectId: this.projectID,
        projectName: this.projectName,
        createdBy: this.projectCreatedBy
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getProject();
    });
  }




  deleteByTaskId(taskID: number) {
    var databaseStage = "Deleted";
    this.taskService.changeStageOFTask(taskID, databaseStage, this.projectCreatedBy, this.projectID).subscribe(
      (response) => {
        if (response == true) {
          this.msg.showSuccess("Deleted Successfully...");
          this.getProject();
        }
      },
      (response) => {
        this.msg.showError("Server Error...");
      }
    )
  }

  AssignTask(taskID: number) {
    const dialogRef = this.dialog.open(AssignTaskComponent, {
      data: {
        employeeList: this.employeeList,
        projectCreatedBy: this.projectCreatedBy,
        projectID: this.projectID,
        taskID: taskID
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getProject();
    });
  }



  drop(event: CdkDragDrop<ITask[]>) {
    const allowedTransitions: { [key: number]: number[] } = {
      0: [1],
      1: [0, 2],
      2: [0, 1, 3],
      3: [0, 1, 2]
    };

    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      const previousColumnId = event.previousContainer.id;
      const currentColumnId = event.container.id;

      const previousColumn = Number(previousColumnId.split('-')[3]);
      const currentColumn = Number(currentColumnId.split('-')[3]);

      if (!allowedTransitions[previousColumn] || !allowedTransitions[previousColumn].includes(currentColumn)) {
        const stageMapping: { [key: number]: string } = {
          0: 'To Do',
          1: 'In Progress',
          2: 'Review',
          3: 'Done'
        };
        const sourceStage = stageMapping[previousColumn];
        const destinationStage = stageMapping[currentColumn];
        let errorMessage = '';

        if (previousColumn === 1 && currentColumn === 3) {
          errorMessage = `You cannot move a card directly from ${sourceStage} to ${destinationStage}. Move it to Review first.`;
        } else if (previousColumn === 0 && currentColumn === 3) {
          errorMessage = `You cannot move a card directly from ${sourceStage} to ${destinationStage}. Move it to Inprogress & Review first.`;
        } else if (previousColumn === 0 && currentColumn === 2) {
          errorMessage = `You cannot move a card directly from ${sourceStage} to ${destinationStage}. Move it to Inprogress first.`;
        }

        this.msg.showWarning(errorMessage);
        return;
      }

      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );

      const taskID = event.container.data[event.currentIndex].taskID;
      const containerId = event.container.id;
      const frontendStage = Number(containerId.split('-')[3]);
      const stageMapping: { [key: number]: string } = {
        0: 'To Do',
        1: 'In Progress',
        2: 'Review',
        3: 'Done'
      };
      const databaseStage = stageMapping[frontendStage];

      this.taskService.changeStageOFTask(taskID, databaseStage, this.projectCreatedBy, this.projectID).subscribe(
        (response: boolean) => {
          if (response) {
            this.msg.showSuccess('Successfully...');
            this.getProject();
          }
        },
        (error) => {
          if (error.status == 404) {
            this.msg.showError('Failed....');
          } else {
            this.msg.showError('Server Error....');
          }
        }
      );
    }
  }























  createTask() {
    const dialogRef = this.dialog.open(CreateTaskComponent, {
      data: {
        createdBy: this.projectCreatedBy,
        projectID: this.projectID,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getProject();
    });
  }

}
