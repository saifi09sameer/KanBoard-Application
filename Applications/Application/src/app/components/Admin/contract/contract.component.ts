import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/Services/Authntication/admin.service';
import { MatTableDataSource } from '@angular/material/table';
import { Contact } from 'src/app/Model/Contact';
import { HttpErrorResponse } from '@angular/common/http';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit {
  data: Contact[] = [];

  displayedColumns: string[] = ['Name', 'Email', 'Subject', 'Message', 'Date', 'Action'];
  dataSource = new MatTableDataSource<Contact>();

  constructor(private adminService: AdminService,
    private msg: SnackbarsService,
    private dialog:MatDialog) { } 

  ngOnInit(): void {
    this.getAllMessages();
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  getAllMessages() {
    this.adminService.getAllMessages().subscribe(
      (response) => {
        this.data = response;
        this.dataSource.data = this.data;
      },
      (error) => {

      }
    )
  }

  deleteMessage(contractID: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        title: 'Confirm Deletion',
        message: 'Are you sure you want to delete this message?',
        confirmText: 'Yes',
        cancelText: 'No'
      }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.adminService.deleteMessages(contractID).subscribe(
          (response) => {
            if (response) {
              this.msg.showSuccess('Deleted Successfully...');
              this.getAllMessages();
            }
          },
          (error) => {
            this.msg.showError('Server Error...');
          }
        );
      }
    });
  }
}
