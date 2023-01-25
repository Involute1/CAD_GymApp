import { Component } from '@angular/core';
import { GymService, Invoice } from '../../../../shared/services/gym.service';
import { AuthService } from '../../../../shared/services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-bills',
  templateUrl: './bills.component.html',
  styleUrls: ['./bills.component.scss'],
})
export class BillsComponent {
  bills$: Observable<Invoice[]>;

  constructor(
    private gymService: GymService,
    private authService: AuthService
  ) {
    this.bills$ = this.gymService.getBills(this.authService.userData.tenantId);
  }
}
