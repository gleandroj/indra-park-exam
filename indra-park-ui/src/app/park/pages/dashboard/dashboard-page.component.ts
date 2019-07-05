import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { SupportComponent } from '../../../support/components';
import * as Highcharts from 'highcharts'
import { OperationService } from '../../../core/services/operation.service';
import { take, tap, map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: 'dashboard-page.component.html',
  styleUrls: ['dashboard-page.component.less']
})
export class DashboardPageComponent extends SupportComponent implements AfterViewInit {

  @ViewChild('chartContainer', { static: true }) chartContainer: ElementRef<HTMLDivElement>;

  chartOptions = {
    chart: {
      type: 'line'
    },
    title: {
      text: 'Operações da Semana'
    },
    subtitle: false,
    xAxis: {
      type: 'datetime',
      dateTimeLabelFormats: {
        day: '%d/%m'
      }
    },
    yAxis: {
      min: 0
    },
    tooltip: {
      headerFormat: '<div style="line-height: 0; text-align: center;"><b >{point.x:%d/%m}</b></div><br>',
      useHTML: true,
      shared: true
    },
    credits: {
      enabled: false
    },
    series: []
  };

  constructor(
    public operationService: OperationService
  ) {
    super();
  }

  ngAfterViewInit(): void {
    Highcharts.chart(this.chartContainer.nativeElement, this.chartOptions as any);
    this.operationService.report().pipe(
      take(1),
      map(dataSet => dataSet)
    ).subscribe();
  }

}
