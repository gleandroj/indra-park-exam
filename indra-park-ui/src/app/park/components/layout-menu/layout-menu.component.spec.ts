import { TestBed, async, ComponentFixture, tick, fakeAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Location } from "@angular/common";
import { LayoutMenuComponent } from './layout-menu.component';
import { RouterTestingModule } from '@angular/router/testing';
import { SupportModule } from 'src/app/support/support.module';
import { By } from '@angular/platform-browser';
import { Component } from '@angular/core';

@Component({ selector: 'app-dashboard-page', template: '' })
class DashboardPageComponent { }

describe('LayoutMenuComponent', () => {
    let location: Location;
    let router: Router;
    let fixture: ComponentFixture<LayoutMenuComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                SupportModule.forRoot(),
                RouterTestingModule.withRoutes([
                    {
                        path: 'indra-park/dashboard',
                        component: DashboardPageComponent
                    }
                ]),
            ],
            declarations: [
                LayoutMenuComponent,
                DashboardPageComponent
            ],
        });

        router = TestBed.get(Router);
        location = TestBed.get(Location);

        fixture = TestBed.createComponent(LayoutMenuComponent);
        fixture.autoDetectChanges();
        fixture.componentInstance.menus = [
            {
                icon: 'dashboard',
                routeLink: '/indra-park/dashboard',
                title: 'Dashboard'
            }
        ];
        router.initialNavigation();
    }));

    it('should create the layout menu', () => {
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });

    it('should render the menu list', () => {
        fixture.detectChanges();
        const app = fixture.componentInstance;
        const test = fixture.debugElement.queryAll(By.css('ul > li'));
        expect(app.menus.length).toBe(1);
        expect(test.length).toBe(1);
        expect(test[0].name).toBe('li');
        expect(test[0].query(By.css('.list-text')).nativeElement.innerHTML).toBe('Dashboard');
    });

    it('should navigate when menu click', fakeAsync(() => {
        fixture.detectChanges();
        const app = fixture.componentInstance;
        const test = fixture.debugElement.query(By.css('ul > li > a'));
        expect(test.name).toBe('a');
        test.nativeElement.click();
        tick();
        expect(location.path()).toBe('/indra-park/dashboard');
    }));

});
