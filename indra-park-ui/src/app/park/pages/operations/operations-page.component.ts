import { Component } from '@angular/core';
import { SupportComponent } from '../../../support/components';
import { OperationService } from 'src/app/core/services/operation.service';
import { take, tap } from 'rxjs/operators';
import { MatDialog, MatSnackBar } from '@angular/material';
import { OperationEntryDialogComponent, OperationExitDialogComponent } from '../../components';
import { Operation } from '../../../core/entities';
import * as moment from 'moment';

@Component({
  selector: 'app-operations-page',
  templateUrl: 'operations-page.component.html',
  styleUrls: ['operations-page.component.less']
})
export class OperationsPageComponent extends SupportComponent {

  filter = {
    from: moment().startOf('day').toDate(),
    to: moment().endOf('day').toDate(),
    plate: null
  };

  dataSource = [];
  loading: boolean;

  constructor(
    private operationService: OperationService,
    public dialog: MatDialog,
    public toastr: MatSnackBar
  ) {
    super();
    this.refresh(true);
  }

  openToast(msg: string) {
    return this.toastr.open(msg, null, {
      duration: 3000
    });
  }

  refresh(loading = false) {
    this.loading = loading;
    this.operationService.all(this.filter)
      .pipe(
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
        this.dataSource = [result].concat(this.dataSource);
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
