import { Component, computed, input, signal, Signal } from '@angular/core';
import { ShipmentModel } from '../../shipment.model';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-shipment-list-item',
  imports: [MatExpansionModule, MatIconModule, MatCardModule, CommonModule],
  templateUrl: './shipment-list-item.html',
  styleUrl: './shipment-list-item.scss',
})
export class ShipmentListItem {
  shipment = input.required<ShipmentModel>();

  freightIcon = computed(() => {
    if (this.shipment().freightType === 'AIR') {
      return 'flight';
    } else if (this.shipment().freightType === 'SEA') {
      return 'directions_boat';
    } else {
      return 'help_outline';
    }
  });

  progressText = computed(() => {
    if (this.shipment().status === 'IN_PROGRESS') {
      return 'In Progress';
    } else if (this.shipment().status === 'COMPLETED') {
      return 'Completed';
    } else {
      return 'Not Started';
    }
  });
}
