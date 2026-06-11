import { Component, ChangeDetectionStrategy, inject, computed } from '@angular/core';
import { Shipment } from '../shipment';
import { httpResource } from '@angular/common/http';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { ShipmentModel } from '../shipment.model';
import { ShipmentListItem } from './shipment-list-item/shipment-list-item';

@Component({
  selector: 'app-shipment-overview',
  imports: [MatTableModule, CommonModule, ShipmentListItem],
  templateUrl: './shipment-overview.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './shipment-overview.scss',
})
export class ShipmentOverview {
  service = inject(Shipment);
  url = this.service.getShipmentUrl();

  shipmentResource = httpResource<ShipmentModel[]>(() => this.url);

  displayColumns = [
    'id',
    'originCityName',
    'originCityCountry',
    'destinationCityName',
    'destinationCityCountry',
    'freightType',
    'estimatedDeliveryTime',
    'status',
    'createdAt',
  ];

  dataSource = computed(() => new MatTableDataSource(this.shipmentResource.value()));
}
