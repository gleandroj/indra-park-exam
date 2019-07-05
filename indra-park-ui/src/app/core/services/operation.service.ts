import { Injectable } from '@angular/core';
import { AbstractService } from 'src/app/support/services';
import { Operation } from '../entities';

@Injectable()
export class OperationService extends AbstractService<Operation> {
  protected resourceURL = '/api/operations';
}
