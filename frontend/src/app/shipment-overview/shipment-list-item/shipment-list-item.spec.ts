import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentListItem } from './shipment-list-item';

describe('ShipmentListItem', () => {
  let component: ShipmentListItem;
  let fixture: ComponentFixture<ShipmentListItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipmentListItem],
    }).compileComponents();

    fixture = TestBed.createComponent(ShipmentListItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
