import {BrowserModule} from '@angular/platform-browser';
import {NgModule, ModuleWithProviders} from '@angular/core';

import {ApplicationRoutingModule} from './park-routing.module';
import {SupportModule} from '../support/support.module';
import {CoreModule} from '../core/core.module';

// import * as Pages from './pages';
// import * as Dialogs from './dialogs';
// import * as AppComponents from './components';

const PagesComponents = [];

const DialogComponents = [];

const Components = [];

@NgModule({
    declarations: [
        Components
    ],
    imports: [
        BrowserModule,
        ApplicationRoutingModule,
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
