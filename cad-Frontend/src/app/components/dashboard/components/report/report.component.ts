import { Component } from '@angular/core';
import { AuthService } from '../../../../shared/services/auth.service';
import { ReportService } from '../../../../shared/services/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
})
export class ReportComponent {
  constructor(
    private authService: AuthService,
    private reportService: ReportService
  ) {}

  onSubmit() {
    this.reportService.createReport(
      this.authService.userData.uid,
      this.authService.userData.tenantId
    );
  }
}
