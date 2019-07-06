import { Component } from '@angular/core';
import { take, tap } from 'rxjs/operators';
import { MatDialog, MatSnackBar } from '@angular/material';
import * as moment from 'moment';
import { Pageable } from '../../../support/interfaces/pageable';
import { SupportComponent } from '../../../support/components';
import { OperationService } from '../../../core/services/operation.service';
import { Operation } from '../../../core/entities';
import { OperationEntryDialogComponent, OperationExitDialogComponent } from '../../components';

interface RefreshParams {
  pageIndex?: number;
  pageSize?: number;
  active?: string;
  direction?: string;
};

@Component({
  selector: 'app-operations-page',
  templateUrl: 'operations-page.component.html',
  styleUrls: ['operations-page.component.less']
})
export class OperationsPageComponent extends SupportComponent {

  filter = {
    from: moment().startOf('day').toDate(),
    to: moment().endOf('day').toDate(),
    plate: null,
    page: 0,
    size: 5,
    sort: null
  };

  dataSource: Pageable<Operation> = {};
  loading: boolean;

  constructor(
    private operationService: OperationService,
    public dialog: MatDialog,
    public toastr: MatSnackBar
  ) {
    super();
    this.refresh({});
  }

  openToast(msg: string) {
    return this.toastr.open(msg, null, {
      duration: 3000
    });
  }

  sort({ active, direction }) {
    if (active && direction && direction.length > 0) {
      return active + ',' + direction;
    } else if (!active && !direction) {
      return this.filter.sort;
    } else {
      return null;
    }
  }

  refresh({ pageIndex, pageSize, active, direction }: RefreshParams) {
    this.loading = true;
    this.filter = {
      ...this.filter,
      page: pageIndex !== undefined ? pageIndex : this.filter.page,
      size: pageSize !== undefined ? pageSize : this.filter.size,
      sort: this.sort({ active, direction })
    };
    this.operationService.paginate(this.filter).pipe(
      take(1),
      tap(() => this.loading = false)
    ).subscribe(data => this.dataSource = data);
  }

  entry() {
    const dialogRef = this.dialog.open(OperationEntryDialogComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe((result: Operation) => {
      if (result) {
        this.dataSource.content = [result].concat(this.dataSource.content);
        this.openToast('Operação de entrada realizada com sucesso!');
      }
    });
  }

  exit(operation: Operation) {
    const dialogRef = this.dialog.open(OperationExitDialogComponent, {
      data: operation
    });

    dialogRef.afterClosed().subscribe((result: Operation) => {
      if (result) {
        Object.assign(operation, result);
        this.openToast('Operação de saída realizada com sucesso!');
      }
    });
  }
}
