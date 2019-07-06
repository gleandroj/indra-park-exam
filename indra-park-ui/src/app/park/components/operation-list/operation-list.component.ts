import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Pageable } from '../../../support/interfaces/pageable';
import { Operation, VehicleType, vehicleTypeDescription } from '../../../core/entities';

@Component({
  selector: 'app-operation-list',
  templateUrl: 'operation-list.component.html',
  styleUrls: ['operation-list.component.less']
})
export class OperationListComponent {
  displayedColumns = ['vehicle.type', 'vehicle.plate', 'vehicle.model', 'enteredAt', 'exitedAt', 'actions'];
  pageSizeOptions = [5, 10, 25, 100];
  @Input() dataSource: Pageable<Operation> = { content: [] };
  @Output() exit = new EventEmitter();
  @Output() page = new EventEmitter();
  @Output() sort = new EventEmitter();

  vehicleTypeDescription(type: VehicleType) {
    return vehicleTypeDescription[type] || '';
  }
}
