import { Component, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Subject } from 'rxjs';
import { tap, distinctUntilChanged, debounceTime, map, takeUntil } from 'rxjs/operators';
import { SupportComponent } from '../../../support/components';

@Component({
  selector: 'app-operation-page-filter',
  templateUrl: 'operation-page-filter.component.html',
  styleUrls: ['operation-page-filter.component.less'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => OperationPageFilter),
      multi: true
    }
  ]
})
export class OperationPageFilter extends SupportComponent implements ControlValueAccessor {
  disabled: boolean;
  onChangeFn: any;
  onTouchedFn: any;

  searchSubject = new Subject();

  filter: any = {};

  constructor() {
    super();
    this.searchSubject.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      map((text: string) => this.filter.plate = text),
      tap(() => this.changed()),
      takeUntil(this.$onDestroy)
    ).subscribe();
  }

  changed() {
    if (this.onChangeFn) {
      this.onChangeFn(this.filter);
    }
  }

  writeValue(obj: any): void {
    this.filter = obj ? obj : {};
  }

  registerOnChange(fn: any): void {
    this.onChangeFn = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedFn = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

}
