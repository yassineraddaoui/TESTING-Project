import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivationAccountPage } from './activation-account.page';

describe('ActivationAccountPage', () => {
  let component: ActivationAccountPage;
  let fixture: ComponentFixture<ActivationAccountPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivationAccountPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
