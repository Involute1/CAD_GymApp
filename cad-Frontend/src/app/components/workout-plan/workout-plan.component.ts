import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { SelectItem } from 'primeng/api';
import {
  Exercise,
  WorkoutPlan,
  WorkoutService,
} from '../../shared/services/workout.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-workout-plan',
  templateUrl: './workout-plan.component.html',
  styleUrls: ['./workout-plan.component.scss'],
})
export class WorkoutPlanComponent implements OnInit {
  protected data: any;
  formGroup: FormGroup = this.initFormGroup();

  weights: SelectItem[] = [];
  selectedRows = {
    monday: [],
    tuesday: [],
    wednesday: [],
    thursday: [],
    friday: [],
    saturday: [],
    sunday: [],
  };

  values = [
    {
      name: 'Benchpress',
      sets: 3,
      repetition: 10,
      weight: 50,
      tag: 'asd',
    },
    {
      name: 'Pullup',
      sets: 3,
      repetition: 5,
      weight: 80,
      tag: 'asd',
    },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private workoutService: WorkoutService,
    private snackBar: MatSnackBar,
    private authService: AuthService
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

  onAdd(day: string) {
    this.formGroup.markAllAsTouched();
    // @ts-ignore
    this[day].push(this.addControls());
  }

  onSubmit() {
    this.workoutService
      .createWorkoutPlan(this.mapToWorkoutPlan())
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
  onDelete(day: string) {
    // @ts-ignore
    for (let i = this.selectedRows[day].length - 1; i >= 0; i--) {
      // @ts-ignore
      this[day].controls.splice(this.selectedRows[day][i] - 1, 1);
    }
    // @ts-ignore
    this[day].updateValueAndValidity(this[day].value);

    // @ts-ignore
    this.selectedRows[day] = [];
  }

  private initFormGroup() {
    return this.formBuilder.group({
      monday: new FormArray([]),
      tuesday: new FormArray([]),
      wednesday: new FormArray([]),
      friday: new FormArray([]),
      thursday: new FormArray([]),
      saturday: new FormArray([]),
      sunday: new FormArray([]),
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
      tag: new FormControl(''),
    });
  }

  private populateData() {
    this.values.forEach((data, index) => {
      this.onAdd('monday');
      this.monday.controls[index].setValue(data);
      this.onAdd('tuesday');
      this.tuesday.controls[index].setValue(data);
      this.onAdd('wednesday');
      this.wednesday.controls[index].setValue(data);
      this.onAdd('thursday');
      this.thursday.controls[index].setValue(data);
      this.onAdd('friday');
      this.friday.controls[index].setValue(data);
      this.onAdd('saturday');
      this.saturday.controls[index].setValue(data);
      this.onAdd('sunday');
      this.sunday.controls[index].setValue(data);
    });
  }

  private mapToWorkoutPlan(): WorkoutPlan {
    let exercises: Exercise[] = [];

    this.monday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'monday',
      });
    });

    this.tuesday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'tuesday',
      });
    });

    this.wednesday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'wednesday',
      });
    });

    this.thursday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'thursday',
      });
    });

    this.friday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'friday',
      });
    });

    this.saturday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'saturday',
      });
    });

    this.sunday.controls.forEach((exercise) => {
      exercises.push({
        ...exercise.value,
        day: 'sunday',
      });
    });

    return {
      exercises,
      userId: this.authService.userData.userId,
    };
  }

  get monday() {
    return this.formGroup.get('monday') as FormArray;
  }

  get tuesday() {
    return this.formGroup.get('tuesday') as FormArray;
  }

  get wednesday() {
    return this.formGroup.get('wednesday') as FormArray;
  }

  get thursday() {
    return this.formGroup.get('thursday') as FormArray;
  }

  get friday() {
    return this.formGroup.get('friday') as FormArray;
  }

  get saturday() {
    return this.formGroup.get('saturday') as FormArray;
  }

  get sunday() {
    return this.formGroup.get('sunday') as FormArray;
  }
}
