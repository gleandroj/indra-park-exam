import { Component } from '@angular/core';
import { SupportComponent } from '../../../../support/components';
import { menus } from '../../../menus';
import { Router, NavigationEnd } from '@angular/router';
import { filter, distinctUntilChanged, tap, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-layout-page',
  templateUrl: 'layout-page.component.html',
  styleUrls: ['layout-page.component.less']
})
export class LayoutPageComponent extends SupportComponent {

  sideMode: String = 'over';
  isSideOpen = false;
  title = 'Indra Park';
  menus = menus;

  constructor(
    router: Router
  ) {
    super();
    router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      distinctUntilChanged(),
      tap(() => this.isSideOpen = false),
      takeUntil(this.$onDestroy)
    ).subscribe();
  }

  toggleSideNav() {
    this.isSideOpen = !this.isSideOpen;
  }
}
