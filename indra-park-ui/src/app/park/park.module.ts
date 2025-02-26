import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ModuleWithProviders } from '@angular/core';

import { ParkRoutingModule } from './park-routing.module';
import { SupportModule } from '../support/support.module';
import { CoreModule } from '../core/core.module';

import * as Pages from './pages';
import * as AppComponents from './components';

const PagesComponents = [
    Pages.LayoutPageComponent,
    Pages.DashboardPageComponent,
    Pages.OperationsPageComponent
];

const DialogComponents = [
    AppComponents.OperationEntryDialogComponent,
    AppComponents.OperationExitDialogComponent
];

const Components = [
    AppComponents.LayoutMenuComponent,
    AppComponents.OperationListComponent,
    AppComponents.OperationPageFilter
];

@NgModule({
    declarations: [
        ...Components,
        ...DialogComponents,
        ...PagesComponents
    ],
    imports: [
        BrowserModule,
        ParkRoutingModule,
        SupportModule,
        CoreModule
    ],
    entryComponents: [
        ...DialogComponents
    ]
})
export class ParkModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ParkModule,
            providers: []
        };
    }
}
