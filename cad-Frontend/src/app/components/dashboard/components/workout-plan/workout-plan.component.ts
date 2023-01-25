import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MessageService } from 'primeng/api';
import {
  Exercise,
  WorkoutPlan,
  WorkoutService,
} from '../../../../shared/services/workout.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService, User } from '../../../../shared/services/auth.service';
import { Observable, of, take } from 'rxjs';
import { UserService } from '../../../../shared/services/user.service';

@Component({
  selector: 'app-workout-plan',
  templateUrl: './workout-plan.component.html',
  styleUrls: ['./workout-plan.component.scss'],
})
export class WorkoutPlanComponent implements OnInit {
  protected data: any;
  formGroup: FormGroup = this.initFormGroup();

  selectedExercises: any = [];
  hasTrainerPermissions: boolean;
  gymUsers$: Observable<User[]> = of([]);

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private workoutService: WorkoutService,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private userService: UserService
  ) {
    this.hasTrainerPermissions = this.authService.hasRole(['Trainer']);
    if (this.hasTrainerPermissions) {
      this.gymUsers$ = this.userService.getUsers(
        this.authService.userData.tenantId
      );
    }
  }

  ngOnInit() {
    this.workoutService
      .getWorkoutPlan(this.authService.userData.uid)
      .pipe(take(1))
      .subscribe((workoutPlan) => {
        if (workoutPlan) {
          this.populateData(workoutPlan.exercises);
        } else {
          this.onAdd();
        }
      });
  }

  onAdd() {
    this.formGroup.markAllAsTouched();
    this.exercises.push(this.addControls());
  }

  onSubmit() {
    this.workoutService
      .createWorkoutPlan(this.mapToWorkoutPlan())
      .subscribe((workout) => {
        this.snackBar.open('Workout Plan Updated!', 'Dismiss', {
          duration: 3000,
        });
      });
  }

  /**
   * delete an existing row
   */
  onDelete() {
    for (let i = this.selectedExercises.length - 1; i >= 0; i--) {
      this.exercises.controls.splice(this.selectedExercises[i] - 1, 1);
    }
    this.exercises.updateValueAndValidity(this.exercises.value);
    this.selectedExercises = [];
  }

  private initFormGroup() {
    return this.formBuilder.group({
      exercises: new FormArray([]),
      uid: new FormControl(this.authService.userData.uid, Validators.required),
    });
  }

  private addControls() {
    return this.formBuilder.group({
      weight: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
      ]),
      sets: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
      ]),
      name: new FormControl('', Validators.required),
      repetition: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
      ]),
      tag: new FormControl('', Validators.required),
    });
  }

  private populateData(exercises: Exercise[]) {
    exercises.forEach((data, index) => {
      this.onAdd();
      console.log(index);
      if (this.exercises.controls[index]) {
        this.exercises.controls[index].setValue({
          name: data.name,
          sets: data.sets,
          repetition: data.repetition,
          weight: data.weight,
          tag: data.tag,
        });
      }
    });
  }

  private mapToWorkoutPlan(): WorkoutPlan {
    let exercises: Exercise[] = [];
    this.exercises.controls.forEach((exercise) => {
      let exerciseEntry: Exercise = {
        name: exercise.get('name')?.value,
        sets: exercise.get('sets')?.value,
        repetition: exercise.get('repetition')?.value,
        weight: exercise.get('weight')?.value,
        tag: exercise.get('tag')?.value,
      };
      exercises.push(exerciseEntry);
    });

    return {
      exercises,
      userId: this.formGroup.get('uid')?.value,
      creatorId: this.authService.userData.uid,
    };
  }

  get exercises() {
    return this.formGroup.get('exercises') as FormArray;
  }
}
