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
  Workout,
  WorkoutService,
} from '../../../../shared/services/workout.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-capture-workout',
  templateUrl: './capture-workout.component.html',
  styleUrls: ['./capture-workout.component.scss'],
})
export class CaptureWorkoutComponent implements OnInit {
  protected data: any;
  formGroup: FormGroup = this.initFormGroup();

  selectedExercises: any = [];

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private workoutService: WorkoutService,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.onAdd();
  }

  onAdd() {
    this.formGroup.markAllAsTouched();
    this.exercises.push(this.addControls());
  }

  onSubmit() {
    this.workoutService
      .createWorkout(this.mapToWorkout())
      .subscribe((workout) => {
        this.snackBar.open('Workout Captured!', 'Dismiss', {
          duration: 3000,
        });
        this.formGroup = this.initFormGroup();
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
      date: new FormControl(this.getDate(), Validators.required),
      exercises: new FormArray([]),
    });
  }

  private getDate() {
    if (this.formGroup?.get('date')) {
      return this.formGroup.get('date')?.value;
    }
    return new Date();
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

  private mapToWorkout(): Workout {
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
      workoutDate: this.formGroup.get('date')?.value,
      userId: this.authService.userData.uid,
    };
  }

  get exercises() {
    return this.formGroup.get('exercises') as FormArray;
  }
}
