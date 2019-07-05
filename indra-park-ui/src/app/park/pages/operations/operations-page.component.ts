import { Component } from '@angular/core';
import { SupportPageComponent } from '../../../support/components';

@Component({
  selector: 'app-operations-page',
  templateUrl: 'operations-page.component.html',
  styleUrls: ['operations-page.component.less']
})
export class OperationsPageComponent extends SupportPageComponent {
  filter = {
    from: null,
    to: null,
    plate: null
  };

  dataSource = [
    {}, {}, {}, {}, {}
  ];

  refresh(loading = false) {

  }
}
