import { Component, Input } from '@angular/core';

export interface MenuItem {
    title: string;
    icon: string;
    routeLink: string;
}

@Component({
    selector: 'app-layout-menu',
    templateUrl: 'layout-menu.component.html',
    styleUrls: ['layout-menu.component.less']
})
export class LayoutMenuComponent {
    @Input() menus: MenuItem[] = [];
}
