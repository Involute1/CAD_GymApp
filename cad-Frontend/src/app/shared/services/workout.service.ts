import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WorkoutService {
  workoutUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.workoutPort}` +
    '/workout';

  constructor(private http: HttpClient) {}

  workoutHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.workoutUrl + '/healthcheck');
  }

  getWorkoutPlan(userId: string): Observable<WorkoutPlanView> {
    return of({
      exercises: [
        {
          id: 0,
          name: 'benchpress',
          sets: 4,
          repetition: 10,
          weight: 10,
          tag: 'chest',
          day: 'monday',
        },
        {
          id: 1,
          name: 'benchpress',
          sets: 4,
          repetition: 10,
          weight: 10,
          tag: 'chest',
          day: 'monday',
        },
        {
          id: 2,
          name: 'benchpress',
          sets: 4,
          repetition: 10,
          weight: 10,
          tag: 'chest',
          day: 'monday',
        },
        {
          id: 3,
          name: 'benchpress',
          sets: 4,
          repetition: 10,
          weight: 10,
          tag: 'chest',
          day: 'tueday',
        },
        {
          id: 4,
          name: 'benchpress',
          sets: 4,
          repetition: 10,
          weight: 10,
          tag: 'chest',
          day: 'tuesday',
        },
      ],
      userId,
    }).pipe(map((workoutPlan) => this.mapToWorkoutPlanView(workoutPlan)));
    /*return this.http.get<WorkoutPlan>(
      `${this.workoutUrl}/workoutplan/${userId}`
    );*/
  }

  createWorkout(workout: Workout): Observable<Workout> {
    return of({} as Workout);
    //return this.http.post<Workout>(`${this.workoutUrl}/workout`, workout);
  }

  createWorkoutPlan(workoutPlan: WorkoutPlan): Observable<WorkoutPlan> {
    return of(workoutPlan);
    //return this.http.post<WorkoutPlan>(`${this.workoutUrl}/workout-plan`, workoutPlan);
  }

  getWorkouts(userId: string): Observable<Workout[]> {
    return of([
      {
        workoutDate: new Date(),
        exercises: [
          {
            id: 0,
            name: 'benchpress',
            sets: 4,
            repetition: 10,
            weight: 10,
            tag: 'chest',
            day: 'monday',
          },
        ],
      },
    ]);
    //return this.http.post<Workout>(`${this.workoutUrl}/workout`, workout);
  }

  private mapToWorkoutPlanView(workoutPlan: WorkoutPlan): WorkoutPlanView {
    let dailyExcercises = [];
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'monday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'tuesday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'wednesday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'thursday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'friday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'saturday'));
    dailyExcercises.push(this.getExercisesForDay(workoutPlan, 'sunday'));
    return {
      dailyExcercises,
    };
  }

  private getExercisesForDay(
    workoutPlan: WorkoutPlan,
    day: string
  ): DailyExercies {
    return {
      exercises: workoutPlan.exercises.filter(
        (exercise) => exercise.day === day
      ),
      day,
    };
  }
}

export interface WorkoutPlanView {
  dailyExcercises: DailyExercies[];
}

export interface Workout {
  workoutDate: Date;
  exercises: Exercise[];
}

export interface DailyExercies {
  exercises: Exercise[];
  day: string;
}

export interface WorkoutPlan {
  exercises: Exercise[];
  userId: string;
}

export interface Exercise {
  id?: number;
  name: string;
  sets: number;
  weight: number;
  repetition: number;
  tag: string;
  day?: string;
}
