import { TestBed, async, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { OperationListComponent } from './operation-list.component';
import * as moment from 'moment';
import { Operation, OperationType, VehicleType, vehicleTypeDescription } from '../../../core/entities';
import { Pageable } from '../../../support/interfaces/pageable';
import { SupportModule } from 'src/app/support/support.module';

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

describe('OperationListComponent', () => {
    let fixture: ComponentFixture<OperationListComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                SupportModule.forRoot()
            ],
            declarations: [
                OperationListComponent
            ],
        });
        fixture = TestBed.createComponent(OperationListComponent);
        fixture.componentInstance.dataSource = pageableOperations;
    }));

    it('should create the operation list', () => {
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
        expect(fixture.componentInstance.dataSource.content.length).toBe(1);
    });

    it('should render the operation list header', fakeAsync(() => {
        fixture.detectChanges();
        tick();
        let thread = fixture.nativeElement.querySelectorAll('thead > tr');
        expect(thread.length).toBe(1);
        let getHeaderHTML = (cell) => cell.getElementsByTagName('button')[0].innerHTML;
        // Header row
        let headerRow = thread[0];
        expect(getHeaderHTML(headerRow.cells[0])).toBe('Tipo');
        expect(getHeaderHTML(headerRow.cells[1])).toBe('Placa');
        expect(getHeaderHTML(headerRow.cells[2])).toBe('Modelo');
        expect(getHeaderHTML(headerRow.cells[3])).toBe('Dt. Entrada');
        expect(getHeaderHTML(headerRow.cells[4])).toBe('Dt. Saída');
        expect(headerRow.cells[5].innerHTML).toBe('Ações');
    }));

    it('should render the operation list rows and hide/show the exit button', fakeAsync(() => {
        fixture.detectChanges();
        tick();
        let tableRows = fixture.nativeElement.querySelectorAll('tbody > tr');
        expect(tableRows.length).toBe(1);
        let operation = pageableOperations.content[0];
        let row = tableRows[0];
        expect(row.cells[0].innerHTML).toBe(vehicleTypeDescription[operation.vehicle.type]);
        expect(row.cells[1].innerHTML).toBe(operation.vehicle.plate);
        expect(row.cells[2].innerHTML).toBe(operation.vehicle.model);
        expect(row.cells[3].innerHTML).toBe(moment(operation.createdAt).format('DD/MM/YY HH:mm:ss'));
        expect(row.cells[4].innerHTML).toBe('');
        expect(row.cells[5].getElementsByTagName('button')[0]).toBeDefined();

        operation.exitedAt = moment().toISOString();
        fixture.detectChanges();
        tick();

        expect(row.cells[4].innerHTML).toBe(moment(operation.exitedAt).format('DD/MM/YY HH:mm:ss'));
        expect(row.cells[5].getElementsByTagName('button')[0]).toBeUndefined();
    }));

    it('should emit event when exit button click', () => {
        pageableOperations.content[0].exitedAt = null;
        fixture.detectChanges();
        spyOn(fixture.componentInstance.exit, 'emit');
        let tableRows = fixture.nativeElement.querySelectorAll('tbody > tr');
        let row = tableRows[0];
        const button = row.cells[5].getElementsByTagName('button')[0];
        button.click();
        fixture.detectChanges();
        expect(fixture.componentInstance.exit.emit).toHaveBeenCalledWith(pageableOperations.content[0]);
    });

    it('should emit event when sortable header click', () => {
        fixture.detectChanges();
        spyOn(fixture.componentInstance.sort, 'emit');
        let thead = fixture.nativeElement.querySelectorAll('tr')[0];
        const button = thead.cells[0].getElementsByTagName('button')[0];
        button.click();
        fixture.detectChanges();
        expect(fixture.componentInstance.sort.emit).toHaveBeenCalledWith({
            active: 'vehicle.type',
            direction: 'asc'
        });
    });

    it('should emit event when page button click', () => {
        fixture.detectChanges();
        spyOn(fixture.componentInstance.page, 'emit');
        let button = fixture.nativeElement.querySelectorAll('.mat-paginator-navigation-next')[0];
        button.click();
        fixture.detectChanges();
        expect(fixture.componentInstance.page.emit).toHaveBeenCalledWith({
            previousPageIndex: 0,
            pageIndex: 1,
            pageSize: 1,
            length: 2
        });
    });
});
