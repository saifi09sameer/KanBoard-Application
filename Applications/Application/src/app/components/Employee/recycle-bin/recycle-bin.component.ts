import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITask } from 'src/app/Model/task';
import { TasksService } from 'src/app/Services/Task/tasks.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogData } from 'src/app/Model/ConfirmationDialogData';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-recycle-bin',
  templateUrl: './recycle-bin.component.html',
  styleUrls: ['./recycle-bin.component.css']
})
export class RecycleBinComponent implements OnInit {
  employeeEmail:any;
  projectID: number = 0;
  projectCreatedBy: string = "";

  DeletedTask: ITask[] = [];

  constructor(private route: ActivatedRoute, private taskService: TasksService, private msg: SnackbarsService,
    private router:Router,private dialog: MatDialog
    ) { 
    
   
    }



  ngOnInit() {
    this.route.params.subscribe(params => {
      this.projectID = params['projectID'];
      this.projectCreatedBy = params['projectCreatedBy'];
    });
    this.loadAllDeletedTask();
    this.employeeEmail=localStorage.getItem("employeeEmail");
  }

  backToTask(){
    this.router.navigate(['/taskDashboard', this.projectID])
  }

  loadAllDeletedTask() {
    this.taskService.getAllDeletedTasks(this.projectCreatedBy, this.projectID).subscribe(
      (response) => {
        this.DeletedTask = response;
      },
      (error) => {
        this.msg.showError("Server Error.....")
      }
    )
  }

  deleteAllTasks(){
    this.taskService.deleteAllDeletedTasks(this.projectCreatedBy,this.projectID).subscribe(
      (response)=>{
        if(response==true){
          this.msg.showSuccess("All Tasks Deleted Successfully....")
          this.loadAllDeletedTask();
        }
      },
      (error)=>{
        this.msg.showError("Server Error....")
      }
    )
  }

  UndoTask(taskID: number) {
    var databaseStage = "To Do";
    this.taskService.changeStageOFTask(taskID, databaseStage, this.projectCreatedBy, this.projectID).subscribe(
      (response) => {
        if (response == true) {
          this.msg.showSuccess("Undo Successfully...");
          this.loadAllDeletedTask();
        }
      },
      (response) => {
        this.msg.showError("Server Error...")
      }
    )
  }


  deleteByTaskId(taskId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmation',
        message: 'Do you really want to delete this task?',
        confirmText: 'Delete',
        cancelText: 'Cancel'
      } as ConfirmationDialogData,
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.taskService.deleteTask(this.projectCreatedBy, this.projectID, taskId).subscribe(
          (response) => {
            if (response == true) {
              this.msg.showSuccess('Delete Task Successfully....');
              this.loadAllDeletedTask();
            } else {
              this.msg.showError('Task Not Deleted....');
            }
          },
          (error) => {
            this.msg.showError('Server Error....');
          }
        );
      }
    });
  }

}
