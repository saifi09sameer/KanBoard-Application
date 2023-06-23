import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ITask } from 'src/app/Model/task';
import { TasksService } from 'src/app/Services/Task/tasks.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.css']
})
export class ViewTaskComponent implements OnInit{
  task: ITask;
  projectId: number=0;
  projectName: string="";
  createdBy: string="";
  isVisible:boolean=false;

  constructor(private taskService:TasksService,
    private msg:SnackbarsService,
    private dilog:MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any){

    this.task = data.task;
    this.projectId = data.projectId;
    this.projectName = data.projectName;
    this.createdBy = data.createdBy;
  }
  

  ngOnInit() {

  }

  
  newTaskDescription: string = '';

  closeDialog() {
    this.dilog.closeAll();
  }

  updateTask() {
    const updatedTaskDescription = this.task.taskDescription + '\n' + this.newTaskDescription;
    this.taskService.updateTask(updatedTaskDescription,this.createdBy,this.projectId,this.task.taskID).subscribe(
      (response)=>{
        if(response==true){
          this.msg.showSuccess("Task Updated Successfully...")
          this.closeDialog();
        }
      },
      (error)=>{

      }
    )
  }
  
  
  


}
