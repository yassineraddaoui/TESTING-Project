import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReturnedBookListPage } from './returned-book-list.page';

describe('ReturnedBookListPage', () => {
  let component: ReturnedBookListPage;
  let fixture: ComponentFixture<ReturnedBookListPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnedBookListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
