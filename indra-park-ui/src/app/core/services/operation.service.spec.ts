import { OperationService } from "./operation.service";
import { Operation, OperationType, VehicleType, Vehicle, OperationValueResult } from '../entities';
import { Pageable } from 'src/app/support/interfaces/pageable';
import { of } from 'rxjs';
import * as moment from 'moment';

let httpClientSpy: { get: jasmine.Spy, post: jasmine.Spy };
let operationService: OperationService;
const vehicle: Vehicle = {
    model: 'Corsa',
    plate: 'HXE2200',
    type: VehicleType.Car
};
const expectedOperation: Operation = {
    createdAt: moment().toISOString(),
    updatedAt: moment().toISOString(),
    enteredAt: moment().toISOString(),
    exitedAt: null,
    id: 1,
    type: OperationType.IN,
    vehicle: vehicle
};
const expectedOperations: Operation[] = [expectedOperation];
const pageableOperations: Pageable<Operation> = {
    content: expectedOperations,
    size: 10,
    totalPages: 1,
    empty: false
};
const expectedOPVR: OperationValueResult = {
    enteredAt: expectedOperation.enteredAt,
    exitedAt: moment().toISOString(),
    hours: 0,
    minutes: 0,
    seconds: 0,
    totalHours: 1,
    value: 15,
    operationId: expectedOperation.id
};

describe('OperationService', () => {

    beforeEach(() => {
        httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post']);
        operationService = new OperationService(<any>httpClientSpy);
    });

    it('should return expected Operations (HttpClient called once)', () => {
        httpClientSpy.get.and.returnValue(of(pageableOperations));
        operationService.paginate().subscribe(
            pageable => expect(pageable).toEqual(pageableOperations, 'expected pageable Operations'),
            fail
        );
        expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
    });


    it('should return a operation when make a entry (HttpClient called once)', () => {
        httpClientSpy.post.and.returnValue(of(expectedOperation));
        operationService.entry(vehicle).subscribe(
            operation => expect(operation).toEqual(operation, 'expected operation'),
            fail
        );
        expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
    });

    it('should return a operation when make a exit (HttpClient called once)', () => {
        expectedOperation.exitedAt = moment().toISOString();
        httpClientSpy.get.and.returnValue(of(expectedOperation));
        operationService.exit(expectedOperation.id).subscribe(
            operation => expect(operation).toEqual(operation, 'expected operation'),
            fail
        );
        expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
    });

    it('should return a operation value result when make a calculate (HttpClient called once)', () => {
        expectedOperation.exitedAt = moment().toISOString();
        httpClientSpy.get.and.returnValue(of(expectedOPVR));
        operationService.calculate(expectedOperation.id).subscribe(
            opvr => expect(opvr).toEqual(expectedOPVR, 'expected operation'),
            fail
        );
        expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
    });
});