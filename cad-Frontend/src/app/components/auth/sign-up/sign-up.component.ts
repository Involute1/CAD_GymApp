import { Component, OnInit } from '@angular/core';
import { AuthService, Roles } from '../../../shared/services/auth.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Gym, GymService } from '../../../shared/services/gym.service';
import { Observable } from 'rxjs';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit {
  roles = Roles;
  gyms$: Observable<Gym[]>;

  billingModels: SelectItem[] = [];

  formGroup: FormGroup = this.formBuilder.group({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    role: new FormControl(Roles.USER, Validators.required),
    gymName: new FormControl(''),
    billingModel: new FormControl(''),
  });

  constructor(
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private gymService: GymService
  ) {
    this.gyms$ = this.gymService.getGyms();
  }

  ngOnInit() {
    this.billingModels = [
      {
        value: 'Free',
      },
      {
        value: 'Standard',
      },
      {
        value: 'Enterprise',
      },
    ];
  }

  onSubmit() {
    this.authService.signUp(this.formGroup.value);
  }

  get role() {
    return this.formGroup.get('role') as FormControl;
  }
}
