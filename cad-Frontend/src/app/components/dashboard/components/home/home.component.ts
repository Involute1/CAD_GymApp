import { Component } from '@angular/core';
import {
  WorkoutPlan,
  WorkoutService,
} from '../../../../shared/services/workout.service';
import { AuthService } from '../../../../shared/services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  workoutPlan$: Observable<WorkoutPlan>;

  constructor(
    private workoutService: WorkoutService,
    private authService: AuthService
  ) {
    this.workoutPlan$ = this.workoutService.getWorkoutPlan(
      this.authService.userData.uid
    );
  }
}
