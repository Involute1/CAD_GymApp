import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GymService {
  gymUrl =
    `${window.location.protocol}//${window.location.hostname}:80` + '/gym';

  constructor(private http: HttpClient) {}

  gymHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.gymUrl + '/healthcheck');
  }
}
