import { Routes } from '@angular/router';
import { SendPackage } from './send-package/send-package';
import { ShipmentOverview } from './shipment-overview/shipment-overview';

export const routes: Routes = [
    { path: 'send', component: SendPackage  },
    { path: 'overview', component: ShipmentOverview  },
    { path: '', redirectTo: '/overview', pathMatch: 'full' }
];
