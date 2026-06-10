import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentOverview } from './shipment-overview';

describe('ShipmentOverview', () => {
  let component: ShipmentOverview;
  let fixture: ComponentFixture<ShipmentOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipmentOverview],
    }).compileComponents();

    fixture = TestBed.createComponent(ShipmentOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
