import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AlertController, MenuController } from '@ionic/angular';
import { IonInfiniteScrollCustomEvent } from '@ionic/core';
import { filter } from 'rxjs';
import { BookResponse } from 'src/app/models/bookModels/BookResponse';
import { MyBookPageResponse } from 'src/app/models/bookModels/myBookPageResponse';
import { BookService } from 'src/app/services/book/book.service';

@Component({
  selector: 'app-my-book-list',
  templateUrl: './my-book-list.page.html',
  styleUrls: ['./my-book-list.page.scss'],
})
export class MyBookListPage implements OnInit {

  myBookPageResponse: MyBookPageResponse = {};
  isLoading: boolean = false;
  myBooks: Array<BookResponse> = [];
  page: number = 0;
  size: number = 2;
  bookCover: string | undefined;

  constructor(
    private bookService: BookService,
    private alert: AlertController,
    private menu: MenuController,
    private router: Router
  ) {
    this.router.events
      .pipe(
        filter(
          (event) =>
            event instanceof NavigationEnd &&
            (event as NavigationEnd).urlAfterRedirects === '/my-book-list'
        )
      )
      .subscribe(() => {
        console.log('Navigated to /my-book-list, refreshing books.');
        this.refreshBookList(); 
      });
  }

  ngOnInit() {
    this.refreshBookList();
  }

  refreshBookList() {
    this.myBooks = []; 
    this.page = 0;     
    this.loadBooksUntilFull(); 
  }

  loadBooksUntilFull(event?: IonInfiniteScrollCustomEvent<void>) {
    if (this.isLoading) return;

    this.isLoading = true;
    this.bookService.findMyBooks({ page: this.page, size: this.size }).subscribe({
      next: (response) => {
        this.myBooks = [...this.myBooks, ...(response.content || [])];
        this.isLoading = false;

        if (this.page < (response.totalPages as number) - 1) {
          this.page++;
          this.loadBooksUntilFull(event); 
        } else if (event) {
          event.target.disabled = true;
        }

        if (event) {
          event.target.complete();
        }
      },
      error: async (err) => {
        const alert = await this.alert.create({
          header: 'Error',
          message: 'Something went wrong',
          buttons: ['OK'],
        });
        alert.present();
        this.isLoading = false;
      },
    });
  }

  loadMore(event: IonInfiniteScrollCustomEvent<void>) {
    this.loadBooksUntilFull(event); 
  }

  selectedPicture(book: BookResponse): string | undefined {
    return book.cover ? 'data:image/jpg;base64, ' + book.cover : '';
  }

  menuOpen() {
    this.menu.open('first');
  }

  archiveBook(book: BookResponse) {
    this.bookService.archivedBook(book.id as number).subscribe({
      next: () => {
        book.archived = !book.archived;
      },
    });
  }

  shareBook(book: BookResponse) {
    this.bookService.shareBook(book.id as number).subscribe({
      next: () => {
        book.shareable = !book.shareable;
      },
    });
  }

  addBook() {
    this.router.navigate(['manage-book']);
  }

  editBook(book: BookResponse) {
    this.router.navigate(['/manage-book', book.id]);
  }
}
