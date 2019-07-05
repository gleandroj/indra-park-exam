import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import * as ParkPages from './pages';

const routes: Routes = [
    {
        path: 'indra-park',
        component: ParkPages.LayoutPageComponent,
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'dashboard'
            },
            {
                path: 'dashboard',
                component: ParkPages.DashboardPageComponent
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
