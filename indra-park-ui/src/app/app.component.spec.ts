import { TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { routes } from './app-routing.module';
import { Router } from '@angular/router';
import { Location } from "@angular/common";
import { ParkModule } from './park/park.module';
import { CoreModule } from './core/core.module';

describe('AppComponent', () => {
  let location: Location;
  let router: Router;
  let fixture;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        CoreModule.forRoot(),
        ParkModule.forRoot()
      ],
      declarations: [
        AppComponent
      ],
    });

    router = TestBed.get(Router);
    location = TestBed.get(Location);

    fixture = TestBed.createComponent(AppComponent);
    router.initialNavigation();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('navigate to "" redirects you to dashboard', fakeAsync(() => {
    router.navigate(['/']);
    tick();
    expect(location.path()).toBe('/indra-park/dashboard');
  }));
});
