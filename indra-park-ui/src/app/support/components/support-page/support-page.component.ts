import { OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

export abstract class SupportPageComponent implements OnDestroy {

    protected $onDestroy = new Subject();

    constructor() { }

    public ngOnDestroy(): void {
        //Prevent memory leaks
        this.$onDestroy.next();
        this.$onDestroy.complete();
    }

}