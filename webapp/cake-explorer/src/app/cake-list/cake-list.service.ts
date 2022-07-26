import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {} from '@angular/common';
import { HttpClient } from '@angular/common/http';

export type CakeRequest = {
  name: string;
  description: string;
  imageUrl: string;
};

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

  public getCakes(): Observable<Cake[]> {
    return this.httpClient.get<Cake[]>(CAKE_URL)
  }

  public createCake(requestedCake: CakeRequest): Observable<Cake> {
    console.log(requestedCake)
    return this.httpClient.post<Cake>(CAKE_URL, requestedCake);
  }
}
