import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { Router } from '@angular/router';
import { Tenant, UserService } from '../../../shared/services/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  tenants$: Observable<Tenant[]>;

  constructor(
    public authService: AuthService,
    private router: Router,
    private userService: UserService
  ) {
    this.tenants$ = this.userService.getTenants();
  }

  ngOnInit() {
    if (this.authService.isLoggedIn) {
      this.router.navigate(['/dashboard']);
    }
  }
}
