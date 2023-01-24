import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GymService {
  gymUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.gymPort}` +
    '/gym';

  constructor(private http: HttpClient) {}

  gymHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.gymUrl + '/healthcheck');
  }

  getGyms(): Observable<Gym[]> {
    return of([
      {
        tenantId: 'HappyFit-6amgt',
        name: 'HappyFit',
      },
      {
        tenantId: '1',
        name: 'Fitty',
      },
    ]);
    //return this.http.get<Gym[]>(this.gymUrl + '/gym');
  }
}

export interface Gym {
  tenantId: string;
  name: string;
}
