import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import {
  Exercise,
  Workout,
  WorkoutService,
} from '../../shared/services/workout.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-capture-workout',
  templateUrl: './capture-workout.component.html',
  styleUrls: ['./capture-workout.component.scss'],
})
export class CaptureWorkoutComponent implements OnInit {
  protected data: any;
  formGroup: FormGroup = this.initFormGroup();

  weights: SelectItem[] = [];
  selectedExercises: any = [];

  values = [
    {
      name: 'Benchpress',
      sets: 3,
      repetition: 10,
      weight: 50,
    },
    {
      name: 'Pullup',
      sets: 3,
      repetition: 5,
      weight: 80,
    },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private workoutService: WorkoutService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.weights = [
      { label: '5', value: 5 },
      { label: '10', value: 10 },
      { label: '20', value: 20 },
      { label: '50', value: 50 },
      { label: '80', value: 80 },
    ];

    this.populateData();
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
    if (this.selectedExercises.length < 1) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Info',
        detail: 'Please select a record to delete!',
      });
      return;
    }
    for (let i = this.selectedExercises.length - 1; i >= 0; i--) {
      this.exercises.controls.splice(this.selectedExercises[i] - 1, 1);
    }
    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Selected records deleted!',
    });

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
    });
  }

  private populateData() {
    this.values.forEach((data, index) => {
      this.onAdd();
      this.exercises.controls[index].setValue(data);
    });
  }

  private mapToWorkout(): Workout {
    let exercises: Exercise[] = [];
    this.exercises.controls.forEach((exercise) => {
      let exerciseEntry: Exercise = {
        name: exercise.get('name')?.value,
        sets: [],
        tags: [],
      };
      exercises.push(exerciseEntry);
    });
    return {
      exercises,
      workoutDate: this.formGroup.get('date')?.value,
    };
  }

  get exercises() {
    return this.formGroup.get('exercises') as FormArray;
  }
}
