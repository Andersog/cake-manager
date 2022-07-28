import { DOCUMENT } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'AuthButton',
  template: ` <span *ngIf="auth.user$ | async as user">
      {{ user.name }}
    </span>
    <ng-container *ngIf="auth.isAuthenticated$ | async; else loggedOut">
      <button
        (click)="auth.logout({ returnTo: document.location.origin })"
        mat-icon-button
        aria-label="logout"
      >
        <mat-icon>logout</mat-icon>
      </button>
    </ng-container>

    <ng-template #loggedOut>
      <button
        (click)="auth.loginWithRedirect()"
        mat-icon-button
        aria-label="login"
      >
        <mat-icon>login</mat-icon>
      </button>
    </ng-template>`,
})
export class AuthButtonComponent {

  constructor(
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService
  ) {

  }
}
