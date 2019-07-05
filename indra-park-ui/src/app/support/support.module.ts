import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ModuleWithProviders} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {NgxMaskModule} from 'ngx-mask';

import {
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatSortModule,
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTooltipModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    MatStepperModule,
    MatListModule, MatDatepickerModule, MatNativeDateModule
} from '@angular/material';

const materialModules = [
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatSortModule,
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatProgressBarModule,
    MatAutocompleteModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatStepperModule
];

const exportShared = [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserAnimationsModule,
    NgxMaskModule,
    ...materialModules
];

const importShared = [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserAnimationsModule,
    ...materialModules
];

const declarations = [];

const entryComponents = [];

/**
 * Contém a base para todos os módulos
 */
@NgModule({
    imports: [
        ...importShared
    ],
    exports: [
        ...exportShared,
        ...declarations
    ],
    declarations: [
        ...declarations
    ],
    entryComponents: [
        ...entryComponents
    ]
})
export class SupportModule {

    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SupportModule,
            providers: []
        };
    }

}
