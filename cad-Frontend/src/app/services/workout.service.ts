import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WorkoutService {
  private workoutUrl = environment.workoutAPI;

  constructor(private http: HttpClient) {}

  workoutHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.workoutUrl + '/healthcheck');
  }
}
