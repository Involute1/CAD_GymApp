import { Component, OnInit } from '@angular/core';
import { Workout, WorkoutService } from '../../shared/services/workout.service';
import { Observable } from 'rxjs';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-workouts',
  templateUrl: './workouts.component.html',
  styleUrls: ['./workouts.component.scss'],
})
export class WorkoutsComponent implements OnInit {
  workouts$: Observable<Workout[]>;

  constructor(
    private workoutService: WorkoutService,
    private authService: AuthService
  ) {
    this.workouts$ = this.workoutService.getWorkouts(this.authService.userData);
  }

  ngOnInit(): void {}
}
