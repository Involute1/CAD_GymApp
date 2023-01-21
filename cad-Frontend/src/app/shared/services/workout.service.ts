import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of } from 'rxjs';

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

  getWorkoutPlan(userId: string): Observable<WorkoutPlanView> {
    return of({
      exercises: [
        {
          id: 0,
          name: 'benchpress',
          sets: [
            {
              weight: 10,
              repetition: 10,
            },
            {
              weight: 10,
              repetition: 10,
            },
            {
              weight: 10,
              repetition: 10,
            },
            {
              weight: 10,
              repetition: 10,
            },
          ],
          tags: [
            {
              name: 'monday',
            },
          ],
          day: 'monday',
        },
        {
          id: 1,
          name: 'Pullups',
          sets: [
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
          ],
          tags: [
            {
              name: 'monday',
            },
          ],
          day: 'monday',
        },
        {
          id: 2,
          name: 'Pullups',
          sets: [
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
          ],
          tags: [
            {
              name: 'monday',
            },
          ],
          day: 'monday',
        },
        {
          id: 3,
          name: 'Pullups',
          sets: [
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
            {
              weight: 80,
              repetition: 10,
            },
          ],
          tags: [
            {
              name: 'tuesday',
            },
          ],
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
  sets: Set[];
  tags: Tag[];
  day?: string;
}

export interface Set {
  weight: number;
  repetition: number;
}

export interface Tag {
  name: string;
}
