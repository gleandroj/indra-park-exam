import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-operation-list',
  templateUrl: 'operation-list.component.html',
  styleUrls: ['operation-list.component.less']
})
export class OperationListComponent {
  displayedColumns = ['plate', 'model', 'entered_at', 'exited_at', 'actions'];
  pageSize = 5;
  pageSizeOptions = [5, 10, 25, 100];
  @Input() dataSource = [];
  @Output() exit = new EventEmitter();
}
