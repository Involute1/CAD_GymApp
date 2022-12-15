import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GymService {
  private gymUrl = environment.gymAPI;

  constructor(private http: HttpClient) {}

  gymHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.gymUrl + '/healthcheck');
  }
}
