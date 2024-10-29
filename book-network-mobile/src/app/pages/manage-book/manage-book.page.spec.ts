import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ManageBookPage } from './manage-book.page';

describe('ManageBookPage', () => {
  let component: ManageBookPage;
  let fixture: ComponentFixture<ManageBookPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageBookPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
