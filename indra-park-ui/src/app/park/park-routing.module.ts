import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import * as Pages from './pages';

const routes: Routes = [
    {
        path: 'indra-park',
        component: Pages.LayoutPageComponent,
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'dashboard'
            },
            {
                path: 'dashboard',
                component: Pages.DashboardPageComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ParkRoutingModule {
}