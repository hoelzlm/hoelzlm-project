import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { CreateShipmentModel, ShipmentModel } from './shipment.model';

@Service()
export class Shipment {
  baseUrl = 'http://localhost:8080/api/v1/shipments';

  http = inject(HttpClient);

  getShipmentUrl() {
    return this.baseUrl;
  }

  createShipment(createShipment: CreateShipmentModel): Observable<ShipmentModel> {
    return this.http.post<ShipmentModel>(this.baseUrl, createShipment);
  }
}
