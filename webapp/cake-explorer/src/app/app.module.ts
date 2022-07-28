import { AuthModule } from '@auth0/auth0-angular';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CakeListComponent } from './cake-list/cake-list.component';
import { CakeService } from './cake-list/cake-list.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { AddCakeComponent } from './add-cake/add-cake.component';
import { FormsModule } from '@angular/forms';
import { AuthButtonComponent } from './auth/auth-button.component';

@NgModule({
  declarations: [
    AppComponent,
    CakeListComponent,
    AddCakeComponent,
    AuthButtonComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatProgressSpinnerModule,

    AuthModule.forRoot({
      domain: 'ganderson-personal.eu.auth0.com',
      clientId: 'GU6Be2k770PldP8MIzNSZltn4KOKAjS8',
    }),
  ],
  providers: [
    CakeService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
