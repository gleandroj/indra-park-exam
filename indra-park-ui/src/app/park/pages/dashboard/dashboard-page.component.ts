import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { SupportComponent } from '../../../support/components';
import * as Highcharts from 'highcharts'
import { OperationService } from '../../../core/services/operation.service';
import { take, tap, map } from 'rxjs/operators';
import { vehicleTypeDescription } from 'src/app/core/entities';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: 'dashboard-page.component.html',
  styleUrls: ['dashboard-page.component.less']
})
export class DashboardPageComponent extends SupportComponent implements AfterViewInit {

  @ViewChild('chartContainer', { static: true }) chartContainer: ElementRef<HTMLDivElement>;

  chartOptions = {
    chart: {
      type: 'spline'
    },
    title: {
      text: 'Operações da Semana'
    },
    subtitle: false,
    legend: {
      verticalAlign: 'top'
    },
    xAxis: {
      type: 'datetime',
      dateTimeLabelFormats: {
        day: '%d/%m'
      },
      title: false
    },
    yAxis: {
      tickInterval: 1,
      title: false,
      startOnTick: false,
      endOnTick: false
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
  chart: Highcharts.Chart;

  constructor(
    public operationService: OperationService
  ) {
    super();
  }

  ngAfterViewInit(): void {
    this.chart = Highcharts.chart(this.chartContainer.nativeElement, this.chartOptions as any);
    this.operationService.report().pipe(
      take(1),
      map(dataSet => dataSet.map(series => {
        return {
          name: vehicleTypeDescription[series.vehicleType],
          data: series.data
        };
      })),
      tap((series: any[]) => series.forEach(s => this.chart.addSeries(s)))
    ).subscribe();
  }

}
