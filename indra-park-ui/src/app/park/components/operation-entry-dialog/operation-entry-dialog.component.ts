import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Vehicle, VehicleType, vehicleTypesLabels } from 'src/app/core/entities';
import { OperationService } from 'src/app/core/services/operation.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { tap } from 'rxjs/operators';
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
        plateControl.valueChanges.subscribe( (value: string)  => plateControl.setValue(
            value ? value.toUpperCase().trim() : value, {emitEvent: false}
        ));
    }

    close(): void {
        this.dialogRef.close();
    }

    save(): void {
        this.operationService.entry(this.form.value as Vehicle).pipe(
            tap((operation) => this.dialogRef.close(operation))
        ).subscribe();
    }
}
