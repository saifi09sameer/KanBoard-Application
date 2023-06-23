import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';
import { IndividualConfig, ToastrService } from 'ngx-toastr';


@Injectable({
  providedIn: 'root'
})
export class SnackbarsService {

  constructor(private snackbar: MatSnackBar,private toastr: ToastrService) {}

  showSuccess(message: string, title: string = 'Success') {
    const toastConfig: Partial<IndividualConfig> = {
      timeOut: 2000,
      closeButton: true,
      positionClass: 'toast-top-right',
      progressBar: true
    };

    this.toastr.success(message, title, toastConfig);
  }

  showError(message: string, title: string = 'Error') {
    const toastConfig: Partial<IndividualConfig> = {
      timeOut: 2000,
      closeButton: true,
      positionClass: 'toast-top-right',
      progressBar: true
    };

    this.toastr.error(message, title, toastConfig);
  }

  showWarning(message: string, title: string = 'Warning') {
    const toastConfig: Partial<IndividualConfig> = {
      timeOut: 2000,
      closeButton: true,
      positionClass: 'toast-top-right',
      progressBar: true
    };

    this.toastr.warning(message, title, toastConfig);
  }

  showInfo(message: string, title: string = 'Info') {
    const toastConfig: Partial<IndividualConfig> = {
      timeOut: 2000,
      closeButton: true,
      positionClass: 'toast-top-right',
      progressBar: true
    };

    this.toastr.info(message, title, toastConfig);
  }
}
