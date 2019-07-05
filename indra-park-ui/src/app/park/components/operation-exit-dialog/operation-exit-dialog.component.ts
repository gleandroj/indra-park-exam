import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { OperationService } from '../../../core/services/operation.service';
import { SupportComponent } from '../../../support/components';
import { Operation, OperationValueResult } from '../../../core/entities';
import { tap, take } from 'rxjs/operators';

@Component({
    selector: 'app-operation-exit-dialog',
    templateUrl: 'operation-exit-dialog.component.html',
    styleUrls: ['operation-exit-dialog.component.less']
})
export class OperationExitDialogComponent extends SupportComponent {

    result: OperationValueResult;
    loading: boolean;

    constructor(
        public dialogRef: MatDialogRef<OperationExitDialogComponent>,
        public operationService: OperationService,
        @Inject(MAT_DIALOG_DATA) public operation: Operation
    ) {
        super();
    }

    close(): void {
        this.dialogRef.close();
    }

    calculate(): void {
        this.loading = true;
        const loaded = () => this.loading = false;;
        this.operationService.calculate(this.operation.id).pipe(
            tap((result) => this.result = result),
            take(1)
        ).subscribe(loaded.bind(this), loaded.bind(this));
    }

    save(): void {
        this.loading = true;
        const loaded = () => this.loading = false;;
        this.operationService.exit(this.operation.id).pipe(
            tap((operation) => this.dialogRef.close(operation)),
            take(1)
        ).subscribe(loaded.bind(this), loaded.bind(this));
    }
}
