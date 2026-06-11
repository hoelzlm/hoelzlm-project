import { httpResource } from '@angular/common/http';
import { Component, ChangeDetectionStrategy, signal, inject } from '@angular/core';
import { CityModel } from '../city.model';
import { form, FormField, required, validate } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { Shipment } from '../shipment';
import { CreateShipmentModel } from '../shipment.model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError } from 'rxjs/internal/operators/catchError';
import { throwError } from 'rxjs/internal/observable/throwError';
import { EstimatedDaysToDeliverModel } from '../estimated.model';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';

export interface SendPackageModel {
  originCityId: number | null;
  destinationCityId: number | null;
  freightType: string;
}

@Component({
  selector: 'app-send-package',
  imports: [
    FormField,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
  ],
  templateUrl: './send-package.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './send-package.scss',
})
export class SendPackage {
  private _snackBar = inject(MatSnackBar);
  service = inject(Shipment);
  router = inject(Router);

  sendPackageModel = signal<SendPackageModel>({
    originCityId: 1,
    destinationCityId: 2,
    freightType: 'AIR',
  });

  sendPackageForm = form(this.sendPackageModel, (schemaPath) => {
    required(schemaPath.originCityId, { message: 'Origin city is required' });
    required(schemaPath.destinationCityId, { message: 'Destination city is required' });
    validate(schemaPath.originCityId, ({ valueOf }) => {
      let originCityId = valueOf(schemaPath.originCityId);
      let destinationCityId = valueOf(schemaPath.destinationCityId);

      if (originCityId && destinationCityId && originCityId === destinationCityId) {
        return {
          kind: 'sameCity',
          message: 'Origin and destination cities cannot be the same',
        };
      }

      return null;
    });
  });

  citiesResource = httpResource<CityModel[]>(() => 'http://localhost:8080/api/v1/cities');
  estimatedDaysToDeliver = httpResource<EstimatedDaysToDeliverModel>(
    () =>
      `http://localhost:8080/api/v1/shipments/calculated?originCityId=${this.sendPackageModel().originCityId}&destinationCityId=${this.sendPackageModel().destinationCityId}&freightType=${this.sendPackageModel().freightType}`,
  );

  openSnackBar(message: string, deliveryDays: number | null) {
    this._snackBar.open(
      `${message} ${deliveryDays ? `delivery in ${deliveryDays} days` : ''}`,
      'Close',
      {
        duration: 5000,
      },
    );
  }

  clearForm() {
    this.sendPackageModel.set({
      originCityId: null,
      destinationCityId: null,
      freightType: 'AIR',
    });
  }

  onSubmit(event: Event) {
    event.preventDefault();
    this.service
      .createShipment({ ...(this.sendPackageModel() as CreateShipmentModel) })
      .pipe(
        catchError((error) => {
          console.error('Error creating shipment:', error);
          this.openSnackBar('Failed to create shipment', null);
          return throwError(() => new Error('Error creating shipment'));
        }),
      )
      .subscribe((shipment) => {
        this.openSnackBar('Shipment created successfully:', shipment.estimatedDays);
        this.clearForm();
      });
  }
}
