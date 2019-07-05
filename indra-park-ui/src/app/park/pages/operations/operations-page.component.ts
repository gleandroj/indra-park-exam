import { Component } from '@angular/core';
import { SupportPageComponent } from '../../../support/components';
import * as moment from 'moment';
import { OperationService } from 'src/app/core/services/operation.service';
import { take, tap, distinctUntilChanged, debounceTime, map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-operations-page',
  templateUrl: 'operations-page.component.html',
  styleUrls: ['operations-page.component.less']
})
export class OperationsPageComponent extends SupportPageComponent {

  searchSubject = new Subject();

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
    this.searchSubject.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      map((text) => this.filter.plate = text),
      tap(() => this.refresh(true)),
      takeUntil(this.$onDestroy)
    ).subscribe();
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
