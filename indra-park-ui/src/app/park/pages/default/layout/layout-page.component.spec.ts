import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SupportModule } from '../../../../support/support.module';
import { LayoutPageComponent } from './layout-page.component';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-layout-menu',
    template: ''
})
class LayoutMenuComponent {
    @Input() menus;
}

describe('LayoutPageComponent', () => {
    let fixture: ComponentFixture<LayoutPageComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule.withRoutes([]),
                SupportModule.forRoot(),
            ],
            declarations: [
                LayoutMenuComponent,
                LayoutPageComponent
            ]
        });

        fixture = TestBed.createComponent(LayoutPageComponent);
        fixture.autoDetectChanges();
    }));

    it('should create the layout page component', () => {
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });
});
