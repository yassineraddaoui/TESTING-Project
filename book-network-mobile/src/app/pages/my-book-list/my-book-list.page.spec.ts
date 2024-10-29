import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MyBookListPage } from './my-book-list.page';

describe('MyBookListPage', () => {
  let component: MyBookListPage;
  let fixture: ComponentFixture<MyBookListPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MyBookListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
