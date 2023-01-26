import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GymService {
  gymUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.gymPort}` +
    '/gym';

  constructor(private http: HttpClient) {}

  gymHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.gymUrl + '/healthcheck');
  }

  getGyms(): Observable<Gym[]> {
    return this.http.get<Gym[]>(this.gymUrl + '/gym');
  }

  getGym(tenantId: string): Observable<Gym> {
    return this.http.get<Gym>(this.gymUrl + '/' + tenantId);
  }

  getBills(tenantId: string): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.gymUrl}/${tenantId}/invoice`);
  }

  uploadGymLogo(imageFormData: FormData, tenantId: string) {
    return this.http
      .post(this.gymUrl + `/${tenantId}/logo`, imageFormData, {
        observe: 'response',
      })
      .subscribe();
  }

  getImage(tenantId: string) {
    return this.http.get(this.gymUrl + `/${tenantId}/logo`);
  }
}

export interface Gym {
  tenantId: string;
  name: string;
  billingModel: string;
  displayName: string;
}

export interface Invoice {
  firestoreId: string;
  billingDate: Date;
  amount: number;
  dueDate: Date;
  gymId: string;
}
