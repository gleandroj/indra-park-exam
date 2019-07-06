import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SupportModule } from '../../../support/support.module';
import { OperationsPageComponent } from './operations-page.component';
import { Component, Input, Output, EventEmitter, forwardRef } from '@angular/core';
import { OperationService } from '../../../core/services/operation.service';
import { of } from 'rxjs';
import { Pageable } from '../../../support/interfaces/pageable';
import { Operation, OperationType, VehicleType } from '../../../core/entities';
import * as moment from 'moment';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { MatDialog } from '@angular/material';

@Component({
    selector: 'app-operation-page-filter',
    template: '',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => OperationPageFilterComponent),
            multi: true
        }
    ]
})
class OperationPageFilterComponent implements ControlValueAccessor {
    writeValue(obj: any): void {
    }
    registerOnChange(fn: any): void {
    }
    registerOnTouched(fn: any): void {
    }
    setDisabledState?(isDisabled: boolean): void {
    }
}

@Component({
    selector: 'app-operation-list',
    template: ''
})
class OperationListComponent {
    @Input() dataSource;
    @Output() exit = new EventEmitter();
    @Output() page = new EventEmitter();
}

const pageableOperations: Pageable<Operation> = {
    content: [
        {
            createdAt: moment().toISOString(),
            updatedAt: moment().toISOString(),
            enteredAt: moment().toISOString(),
            exitedAt: null,
            id: 1,
            type: OperationType.IN,
            vehicle: {
                model: 'Corsa',
                plate: 'HXE2900',
                type: VehicleType.Car
            }
        }
    ],
    size: 1,
    totalPages: 2,
    totalElements: 2,
    empty: false
};

describe('OperationsPageComponent', () => {
    let fixture: ComponentFixture<OperationsPageComponent>;
    let mockService = jasmine.createSpyObj('OperationService', ['refresh', 'exit', 'entry', 'calculate', 'paginate']);
    mockService.paginate.and.returnValue(of(pageableOperations))

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                SupportModule.forRoot(),
                RouterTestingModule.withRoutes([]),
            ],
            declarations: [
                OperationListComponent,
                OperationPageFilterComponent,
                OperationsPageComponent
            ],
            providers: [
                { provide: OperationService, useValue: mockService }
            ]
        });

        fixture = TestBed.createComponent(OperationsPageComponent);
        fixture.autoDetectChanges();
    }));

    it('should create the operations page component', () => {
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });
});
