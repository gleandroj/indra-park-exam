import { Component } from '@angular/core';
import { SupportPageComponent } from '../../../../support/components';

@Component({
  selector: 'app-layout-page',
  templateUrl: 'layout-page.component.html',
  styleUrls: ['layout-page.component.less']
})
export class LayoutPageComponent extends SupportPageComponent {

    sideMode: String = 'over';
    isSideOpen = false;
    title = 'Indra Park';
    menus = [
      {
        routeLink: '/indra-park/dashboard',
        icon: 'dashboard',
        title: 'Dashboard'
      },
      {
        routeLink: '/test',
        icon: 'receipt',
        title: 'Operações'
      }
    ];

    constructor(
    ) {
        super();
    }

    toggleSideNav() {
        this.isSideOpen = !this.isSideOpen;
    }

    isAuthorized(item) {
        return true;
    }
}
