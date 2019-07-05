import { Component } from '@angular/core';
import { SupportPageComponent } from '../../../support/components';
import * as moment from 'moment';
import { OperationService } from 'src/app/core/services/operation.service';
import { take, tap } from 'rxjs/operators';

@Component({
  selector: 'app-operations-page',
  templateUrl: 'operations-page.component.html',
  styleUrls: ['operations-page.component.less']
})
export class OperationsPageComponent extends SupportPageComponent {
  filter = {
    from: moment().startOf('day').toDate(),
    to: moment().endOf('day').toDate(),
    plate: null
  };

  dataSource = [];
  loading: boolean;

  constructor(
    private operationService: OperationService
  ) {
    super();
    this.refresh(true);
  }

  refresh(loading = false) {
    this.loading = loading;
    this.operationService.all(this.filter)
      .pipe(
        take(1),
        tap(() => this.loading = false)
      ).subscribe(data => this.dataSource = data);
  }
}
