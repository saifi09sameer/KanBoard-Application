import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-testing',
  templateUrl: './testing.component.html',
  styleUrls: ['./testing.component.css']
})
export class TestingComponent implements OnInit{
  startDate: string = '2023-01-01'; // Assign a valid date string
  endDate: string = '2022-01-01'; // Assign a valid date string
  formattedDuration: string = '';

  constructor() {}

  ngOnInit() {}

  calculateDuration() {
    const start = new Date(this.startDate);
    const end = new Date(this.endDate);
    const durationInDays = Math.floor((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
    this.formattedDuration = `${durationInDays} days`;
  }
}
