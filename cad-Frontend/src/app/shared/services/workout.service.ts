import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WorkoutService {
  workoutUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.workoutPort}` +
    '/api/workout';

  constructor(private http: HttpClient) {}

  workoutHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.workoutUrl + '/healthcheck');
  }

  getWorkoutPlan(userId: string): Observable<WorkoutPlan> {
    return this.http.get<WorkoutPlan>(`${this.workoutUrl}/plan/${userId}`);
  }

  createWorkout(workout: Workout): Observable<Workout> {
    return this.http.post<Workout>(`${this.workoutUrl}/`, workout);
  }

  createWorkoutPlan(workoutPlan: WorkoutPlan): Observable<WorkoutPlan> {
    return this.http.post<WorkoutPlan>(`${this.workoutUrl}/plan/`, workoutPlan);
  }

  getWorkouts(userId: string): Observable<Workout[]> {
    return this.http.get<Workout[]>(`${this.workoutUrl}/${userId}`);
  }
}

export interface Workout {
  workoutDate: Date;
  exercises: Exercise[];
  userId: string;
}

export interface WorkoutPlan {
  exercises: Exercise[];
  userId: string;
  creatorId: string;
}

export interface Exercise {
  id?: number;
  name: string;
  sets: number;
  weight: number;
  repetition: number;
  tag: string;
}
