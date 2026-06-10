import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendPackage } from './send-package';

describe('SendPackage', () => {
  let component: SendPackage;
  let fixture: ComponentFixture<SendPackage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendPackage],
    }).compileComponents();

    fixture = TestBed.createComponent(SendPackage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
