import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Pageable } from 'src/app/support/interfaces/pageable';
import { Operation } from 'src/app/core/entities';

@Component({
  selector: 'app-operation-list',
  templateUrl: 'operation-list.component.html',
  styleUrls: ['operation-list.component.less']
})
export class OperationListComponent {
  displayedColumns = ['vehicle.plate', 'vehicle.model', 'enteredAt', 'exitedAt', 'actions'];
  pageSizeOptions = [5, 10, 25, 100];
  @Input() dataSource: Pageable<Operation> = { content: [] };
  @Output() exit = new EventEmitter();
  @Output() page = new EventEmitter();
  @Output() sort = new EventEmitter();
}
