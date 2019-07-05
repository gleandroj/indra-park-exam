import { NgModule, Provider } from '@angular/core';
import { ModuleWithProviders } from '@angular/core';
import { SupportModule } from '../support/support.module';
import * as CoreServices from './services/operation.service'

const Services: Provider[] = [
    CoreServices.OperationService
];

const components = [];

/**
 * Contem Serviços e Entidades genéricas Reutilizáveis
 */
@NgModule({
    imports: [
        SupportModule,
    ],
    exports: [
        SupportModule,
        components
    ],
    declarations: [
        components
    ],
    entryComponents: []
})
export class CoreModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: CoreModule,
            providers: Services
        };
    }
}
