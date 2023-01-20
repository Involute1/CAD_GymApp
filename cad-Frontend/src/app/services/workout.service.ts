import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WorkoutService {
  workoutUrl =
    `${window.location.protocol}//${window.location.hostname}:80` + '/workout';

  constructor(private http: HttpClient) {}

  workoutHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.workoutUrl + '/healthcheck');
  }
}
