import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {} from '@angular/common';
import { HttpClient } from '@angular/common/http';

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
  constructor(private httpClient: HttpClient) {}

  /**
   * Gets our cakes from the endpoint.
   * @returns An observable of all cakes on the system.
   */
  public getCakes(): Observable<Cake[]> {
    return this.httpClient.get<Cake[]>(CAKE_URL);
  }

  /**
   * Creates a cake on the system.
   * @returns The cake creation request.
   */
  public createCake(requestedCake: CakeRequest): Observable<Cake> {
    console.log(requestedCake);
    return this.httpClient.post<Cake>(CAKE_URL, requestedCake);
  }
}
