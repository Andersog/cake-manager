import { Injectable } from '@angular/core';
import { catchError, flatMap, map, mergeMap, Observable, of } from 'rxjs';
import {} from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '@auth0/auth0-angular';

/**
 * A creation request.
 */
export type CakeRequest = {
  name: string;
  description: string;
  imageUrl: string;
};

/**
 * A cake entity as represented on the server.
 */
export type Cake = {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
};

const CAKE_URL = '/cakes';

@Injectable()
export class CakeService {
  constructor(private httpClient: HttpClient, public auth: AuthService) {}

  /**
   * Gets our cakes from the endpoint.
   * @returns An observable of all cakes on the system.
   */
  public getCakes(): Observable<Cake[]> {
    return this.httpClient.get<Cake[]>(CAKE_URL).pipe(catchError(() => []));
  }

  /**
   * Creates a cake on the system.
   * @returns The cake creation request.
   */
  public createCake(requestedCake: CakeRequest): Observable<Cake> {
    return this.auth.getAccessTokenSilently().pipe(
      mergeMap((token) => {
        let headers = new HttpHeaders();
        headers = headers.set('Authorization', 'Bearer ' + token);

        return this.httpClient.post<Cake>(CAKE_URL, requestedCake, {
          headers: headers,
        });
      })
    );
  }
}
