import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SelectedPicturePage } from './selected-picture.page';

describe('SelectedPicturePage', () => {
  let component: SelectedPicturePage;
  let fixture: ComponentFixture<SelectedPicturePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectedPicturePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
