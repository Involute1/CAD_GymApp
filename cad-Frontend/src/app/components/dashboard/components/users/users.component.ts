import { Component } from '@angular/core';
import { UserService } from '../../../../shared/services/user.service';
import { AuthService, User } from '../../../../shared/services/auth.service';
import { map, Observable, of } from 'rxjs';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent {
  user$: Observable<User[]>;
  filteredUser$: Observable<User[]> = of([]);
  formGroup = this.formBuilder.group({ role: ['Trainer'] });

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) {
    this.user$ = this.userService.getUsers(this.authService.userData.tenantId);
    this.getUserByTyp();
  }

  getUserByTyp() {
    this.filteredUser$ = this.user$.pipe(
      map((users) =>
        users.filter((user) => user.role === this.formGroup.get('role')?.value)
      )
    );
  }
}
