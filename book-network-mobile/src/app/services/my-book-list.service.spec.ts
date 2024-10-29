import { TestBed } from '@angular/core/testing';

import { MyBookListService } from './my-book-list.service';

describe('MyBookListService', () => {
  let service: MyBookListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyBookListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
