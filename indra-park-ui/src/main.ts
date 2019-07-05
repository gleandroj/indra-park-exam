import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import * as Highcharts from 'highcharts';
import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
declare var require: any;

if (environment.production) {
  enableProdMode();
}

let Boost = require('highcharts/modules/boost');
let noData = require('highcharts/modules/no-data-to-display');
let More = require('highcharts/highcharts-more');

Boost(Highcharts);
noData(Highcharts);
More(Highcharts);
noData(Highcharts);

Highcharts.setOptions({
  lang: {
    noData: 'Nenhuma visualização disponível.'
  }
});

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
