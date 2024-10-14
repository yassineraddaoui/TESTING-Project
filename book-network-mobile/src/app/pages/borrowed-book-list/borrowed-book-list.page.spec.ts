import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BorrowedBookListPage } from './borrowed-book-list.page';

describe('BorrowedBookListPage', () => {
  let component: BorrowedBookListPage;
  let fixture: ComponentFixture<BorrowedBookListPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(BorrowedBookListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
