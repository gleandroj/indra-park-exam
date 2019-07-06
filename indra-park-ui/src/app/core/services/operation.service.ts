import { Injectable } from '@angular/core';
import { AbstractService } from '../../support/services';
import { Operation, Vehicle, OperationValueResult, VehicleType } from '../entities';
import { Observable } from 'rxjs';

@Injectable()
export class OperationService extends AbstractService<Operation> {

  protected resourceURL = 'operations';

  public entry(vehicle: Vehicle): Observable<Operation> {
    return this.http.post(`${this.baseURL}/${this.resourceURL}`, vehicle);
  }

  public exit(id: number): Observable<Operation> {
    return this.http.get<Operation>(`${this.baseURL}/${this.resourceURL}/${id}/exit`);
  }

  public calculate(id: number): Observable<OperationValueResult> {
    return this.http.get<OperationValueResult>(`${this.baseURL}/${this.resourceURL}/${id}/calculate`);
  }

  public report() {
    return this.http.get<{ vehicleType: VehicleType, data: any[] }[]>(`${this.baseURL}/${this.resourceURL}/report`);
  }

}
