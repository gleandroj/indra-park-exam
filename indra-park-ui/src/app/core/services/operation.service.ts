import { Injectable } from '@angular/core';
import { AbstractService } from 'src/app/support/services';
import { Operation, Vehicle } from '../entities';
import { Observable } from 'rxjs';

@Injectable()
export class OperationService extends AbstractService<Operation> {
  protected resourceURL = 'operations';

  public entry(vehicle: Vehicle): Observable<Operation> {
    return this.http.post(`${this.baseURL}/${this.resourceURL}`, vehicle);
  }
}
