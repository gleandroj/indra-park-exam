import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SupportModule } from '../../../support/support.module';
import { DashboardPageComponent } from './dashboard-page.component';
import { OperationService } from '../../../core/services/operation.service';
import { of } from 'rxjs';

describe('DashboardPageComponent', () => {
    let fixture: ComponentFixture<DashboardPageComponent>;
    let mockService = jasmine.createSpyObj('OperationService', ['report']);
    mockService.report.and.returnValue(of([]));

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule.withRoutes([]),
                SupportModule.forRoot(),
            ],
            declarations: [
                DashboardPageComponent
            ],
            providers: [
                { provide: OperationService, useValue: mockService }
            ]
        });

        fixture = TestBed.createComponent(DashboardPageComponent);
        fixture.autoDetectChanges();
    }));

    it('should create the dashboard page component', () => {
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });
});
