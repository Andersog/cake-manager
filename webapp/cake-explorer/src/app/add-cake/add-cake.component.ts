import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'AddCake',
  templateUrl: './add-cake.component.html',
  styleUrls: ['./add-cake.component.less'],
})
export class AddCakeComponent {
  public nameInput?: string;
  public descriptionInput?: string;
  public urlInput?: string;
  public image?: string;

  constructor(private dialogRef: MatDialogRef<AddCakeComponent>) {
  }

  public saveCake(){
    this.dialogRef.close({
      cake: {
        name: this.nameInput,
        description: this.descriptionInput,
        imageUrl: this.urlInput,
      },
    });
  }

  public tryLoad(event: any) {
    this.image = this.urlInput;
  }
}
