import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  reportUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.reportingPort}` +
    '/api/reporting';

  constructor(private http: HttpClient) {}

  createReport(uid: string, tenantId: string) {
    this.http.get<boolean>(`${this.reportUrl}/${uid}/${tenantId}`).subscribe();
  }
}
