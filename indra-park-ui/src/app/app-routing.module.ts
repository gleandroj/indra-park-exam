import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

export const routes: Routes = [
    {path: '', redirectTo: 'indra-park', pathMatch: 'full'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule {}
