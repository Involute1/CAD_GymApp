import { Component, OnInit } from '@angular/core';
import { AuthService, Roles } from '../../../shared/services/auth.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { SelectItem } from 'primeng/api';
import { Tenant, UserService } from '../../../shared/services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit {
  roles = Roles;
  tenants$: Observable<Tenant[]>;

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
    private userService: UserService
  ) {
    this.tenants$ = this.userService.getTenants();
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
  }

  onSubmit() {
    if (this.formGroup.get('role')?.value === Roles.GYMOWNER) {
      this.authService.signUpNewTenant(
        this.formGroup.value,
        this.formGroup.value
      );
    } else {
      this.authService.signUp(this.formGroup.value);
    }
  }

  get role() {
    return this.formGroup.get('role') as FormControl;
  }
}
