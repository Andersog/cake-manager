import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddCakeComponent } from './add-cake/add-cake.component';
import { Cake, CakeRequest, CakeService } from './cake-list/cake-list.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
})
export class AppComponent {
  public cakes?: Cake[];

  constructor(public dialog: MatDialog, public cakeService: CakeService) {
    this.cakeService.getCakes().subscribe((value) => (this.cakes = value));
  }

  public addCake(cakeToAdd: CakeRequest): void {
    this.cakeService
      .createCake(cakeToAdd)
      .subscribe((value) => {
        this.cakes?.unshift(value)
        document.body.scrollTop = document.documentElement.scrollTop = 0;
      });
  }
  openDialog() {
    const dialogRef = this.dialog.open(AddCakeComponent);

    dialogRef.afterClosed().subscribe((value) => this.addCake(value.cake));
  }
}
