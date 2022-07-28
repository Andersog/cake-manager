import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddCakeComponent } from './add-cake/add-cake.component';
import { Cake, CakeRequest, CakeService } from './cake-list/cake-list.service';
import { AuthService } from '@auth0/auth0-angular';
import { combineLatest, forkJoin, take } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
})
export class AppComponent {
  public cakes?: Cake[];
  public addingCake: boolean = false;

  constructor(
    public dialog: MatDialog,
    public cakeService: CakeService,
    public auth: AuthService
  ) {
    // Await both of these before loading the page so that the user does not experience pop in
    forkJoin({
      // isAuthenticate does not complete after emission, so just take the first
      isAuthenticated: auth.isAuthenticated$.pipe(take(1)),
      cakes: this.cakeService.getCakes(),
    }).subscribe((result) => {
      this.cakes = result.cakes;
    });
  }

  /**
   * Adds a cake to the server and updates our current view.
   *
   * @param cakeToAdd The cake to add
   */
  public addCake(cakeToAdd: CakeRequest): void {
    this.addingCake = true;
    this.cakeService.createCake(cakeToAdd).subscribe({
      next: (value) => {
        this.cakes?.push(value);

        // Give the renderer a small amount of time before trying to scroll
        setTimeout(() => window.scrollTo(0, document.body.scrollHeight), 100);
      },
      error: (error) => alert('An error occurred adding our cake'),
      complete: () => (this.addingCake = false),
    });
  }

  /**
   * Opens the add cake dialog.
   */
  openDialog() {
    const dialogRef = this.dialog.open(AddCakeComponent);
    dialogRef.afterClosed().subscribe((value) => {
      if (value?.cake) this.addCake(value.cake);
    });
  }
}
