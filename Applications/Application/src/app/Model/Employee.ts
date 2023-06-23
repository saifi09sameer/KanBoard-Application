export interface Employee {
  employeeID: number;
  employeeName: string;
  gender: string;
  maritalStatus: string;
  employeeDOB: string;
  employeeAadhaarNumber: number;
  bloodGroup: string;
  employeePhoneNumber: string;
  employeeEmail: string;
  employeePassword: string;
  joiningDate: string;
  employeeRole: string;
  nationality: string;
  employeeCity: string;
  imageURL: string;
  status: boolean;
}



export interface EmployeeLogin {
  employeeEmail: string;
  employeePassword: string
}


export class EmployeeRegister {
  employeeName: string;
  employeeEmail: string;
  employeePassword: string;
  employeePhoneNumber: string;
  nationality: string;

  constructor(
    employeeName: string,
    employeeEmail: string,
    employeePassword: string,
    employeePhoneNumber: string,
    nationality: string
  ) {
    this.employeeName = employeeName;
    this.employeeEmail = employeeEmail;
    this.employeePassword = employeePassword;
    this.employeePhoneNumber = employeePhoneNumber;
    this.nationality = nationality;
  }
}


