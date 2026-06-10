import { Service } from '@angular/core';

@Service()
export class Shipment {

    baseUrl = 'http://localhost:8080/api/v1/shipments';


    getShipmentUrl() {
        return this.baseUrl;
    }
}
