import { Component, OnInit } from '@angular/core';
import {
  Workout,
  WorkoutService,
} from '../../../../shared/services/workout.service';
import { map, Observable, of } from 'rxjs';
import { AuthService, User } from '../../../../shared/services/auth.service';
import { UserService } from '../../../../shared/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-workouts',
  templateUrl: './workouts.component.html',
  styleUrls: ['./workouts.component.scss'],
})
export class WorkoutsComponent implements OnInit {
  formGroup = this.formBuilder.group({
    uid: [this.authService.userData.uid, Validators.required],
  });
  workouts$: Observable<Workout[]> | undefined;
  gymUsers$: Observable<User[]> = of([]);
  hasTrainerPermissions: boolean;

  constructor(
    private workoutService: WorkoutService,
    private authService: AuthService,
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.hasTrainerPermissions = this.authService.hasRole(['Trainer']);
    if (this.hasTrainerPermissions) {
      this.gymUsers$ = this.userService
        .getUsers(this.authService.userData.tenantId)
        .pipe(
          map((users) =>
            users.filter(
              (user) =>
                user.role === 'User' ||
                user.uid === this.authService.userData.uid
            )
          )
        );
    }
    this.getWorkoutsForUid();
  }

  ngOnInit(): void {}

  getWorkoutsForUid() {
    this.workouts$ = this.workoutService.getWorkouts(
      this.formGroup.get('uid')?.value
    );
  }
}
