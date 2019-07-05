import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Vehicle, vehicleTypesLabels } from '../../../core/entities';
import { OperationService } from '../../../core/services/operation.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { tap, takeUntil } from 'rxjs/operators';
import { SupportComponent } from '../../../support/components';

@Component({
    selector: 'app-operation-entry-dialog',
    templateUrl: 'operation-entry-dialog.component.html',
    styleUrls: ['operation-entry-dialog.component.less']
})
export class OperationEntryDialogComponent extends SupportComponent {

    vehicleTypes = vehicleTypesLabels;
    form: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<OperationEntryDialogComponent>,
        public operationService: OperationService,
        private fb: FormBuilder
    ) {
        super();
        this.form = this.fb.group({
            model: [null, Validators.required],
            plate: [null, Validators.required],
            type: [null, Validators.required]
        });
        const plateControl = this.form.get('plate');
        plateControl.valueChanges.pipe(
            takeUntil(this.$onDestroy),
            tap((value: string) => plateControl.setValue(
                value ? value.toUpperCase().trim() : value, { emitEvent: false }
            ))
        ).subscribe();
    }

    close(): void {
        this.dialogRef.close();
    }

    save(): void {
        const vehicle = this.form.value as Vehicle;
        const enableForm = () => this.form.enable();
        this.form.disable();
        this.operationService.entry(vehicle).pipe(
            tap((operation) => this.dialogRef.close(operation))
        ).subscribe(enableForm.bind(this), enableForm.bind(this));
    }
}
