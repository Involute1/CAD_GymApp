import { Component, OnInit } from '@angular/core';
import { AuthService, Roles } from '../../../shared/services/auth.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { map, Observable } from 'rxjs';
import { SelectItem } from 'primeng/api';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Gym, GymService } from '../../../shared/services/gym.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit {
  roles = Roles;
  gyms$: Observable<Gym[]>;
  showBillingModel = true;

  billingModels: SelectItem[] = [];

  formGroup: FormGroup = this.formBuilder.group({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    displayName: new FormControl('', Validators.required),
    role: new FormControl(Roles.USER, Validators.required),
    tenantId: new FormControl('', Validators.required),
    gymName: new FormControl(''),
    billingModel: new FormControl(''),
  });

  constructor(
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private gymService: GymService,
    private snackbar: MatSnackBar
  ) {
    let splitHostname = window.location.hostname.split('.');
    let level = splitHostname.at(0);
    this.gyms$ = this.gymService.getGyms().pipe(
      map((gyms) =>
        gyms.filter((gym) => {
          if (window.location.hostname.startsWith('premium')) {
            return gym.billingModel === 'STANDARD';
          } else if (
            splitHostname.length === 2 ||
            level === 'staging' ||
            level === 'dev'
          ) {
            return gym.billingModel === 'FREE';
          } else {
            // @ts-ignore
            return gym.name.toLowerCase() === level.replaceAll('-', '');
          }
        })
      )
    );
  }

  ngOnInit() {
    this.billingModels = [
      {
        label: 'Free',
        value: 'FREE',
      },
      {
        label: 'Standard',
        value: 'STANDARD',
      },
      {
        label: 'Enterprise',
        value: 'ENTERPRISE',
      },
    ];
    let splitHostname = window.location.hostname.split('.');
    let level = splitHostname.at(0);
    if (
      splitHostname.length > 2 &&
      level !== 'premium' &&
      level !== 'staging' &&
      level !== 'dev'
    ) {
      this.showBillingModel = false;
      this.formGroup.get('billingModel')?.setValue('ENTERPRISE');
    }
  }

  onSubmit() {
    if (this.formGroup.get('role')?.value === Roles.GYMOWNER) {
      this.authService.signUpNewTenant(
        {
          email: this.formGroup.get('email')?.value,
          password: this.formGroup.get('password')?.value,
          displayName: this.formGroup.get('displayName')?.value,
          role: this.formGroup.get('role')?.value,
          tenantId: this.formGroup.get('tenantId')?.value,
        },
        {
          displayName: this.formGroup.get('gymName')?.value,
          billingModel: this.formGroup.get('billingModel')?.value,
        }
      );
    } else {
      this.authService.signUp({
        email: this.formGroup.get('email')?.value,
        password: this.formGroup.get('password')?.value,
        displayName: this.formGroup.get('displayName')?.value,
        role: this.formGroup.get('role')?.value,
        tenantId: this.formGroup.get('tenantId')?.value,
      });
    }
  }

  onBillingModelChange() {
    let splitHostname = window.location.hostname.split('.');
    let level = splitHostname.at(0);
    if (this.formGroup.get('billingModel')?.value === 'FREE') {
      if (
        splitHostname.length > 2 &&
        !(level === 'staging' || level === 'dev')
      ) {
        let url = '';
        splitHostname.forEach((hostPart, index) => {
          if (index !== 0) {
            url += hostPart + '.';
          }
        });
        url = url.slice(0, -1);
        window.location.href =
          'http://' +
          url +
          ':' +
          window.location.port +
          window.location.pathname;
      }
    } else if (this.formGroup.get('billingModel')?.value === 'STANDARD') {
      if (
        splitHostname.length === 2 ||
        level === 'staging' ||
        level === 'dev'
      ) {
        window.location.href =
          'http://premium.' +
          window.location.hostname +
          ':' +
          window.location.port +
          window.location.pathname;
      }
    } else if (this.formGroup.get('billingModel')?.value === 'ENTERPRISE') {
      this.snackbar.open(
        'Please contact us at david.wolpers@htwg-konstanz.de to set up your own personal deployment!'
      );
    }
  }

  get role() {
    return this.formGroup.get('role') as FormControl;
  }
}
