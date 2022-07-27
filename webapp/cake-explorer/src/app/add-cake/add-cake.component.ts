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
  public imageValid = false;

  constructor(private dialogRef: MatDialogRef<AddCakeComponent>) {}

  /**
   * Event handler for when the image loads.
   *
   * <p>
   * The event handler is only called if the image loaded successfully.
   * </p>
   */
  public onImageLoad(event: any) {
    this.imageValid = true;
  }

  /**
   * Closes the dialog and passes back a success.
   */
  public saveCake() {
    this.dialogRef.close({
      cake: {
        name: this.nameInput,
        description: this.descriptionInput,
        imageUrl: this.urlInput,
      },
    });
  }

  /**
   * Attempts to load the provided image.
   *
   * @param event The call event.
   */
  public tryLoad(event: any): void {
    if (this.image === this.urlInput) return;

    this.image = this.urlInput;
    this.imageValid = false;
  }
}
