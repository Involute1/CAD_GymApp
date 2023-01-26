import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignUpTenant, SignUpUser, User } from './auth.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userUrl =
    `${window.location.protocol}//${window.location.hostname}${environment.userPort}` +
    '/user';

  constructor(private http: HttpClient) {}

  userHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.userUrl + '/healthcheck');
  }

  createUser(user: SignUpUser): Observable<User> {
    return this.http.post<User>(`${this.userUrl}/user`, user);
  }

  createTenant(tenant: SignUpTenant): Observable<Tenant> {
    return this.http.post<Tenant>(`${this.userUrl}/tenant/`, tenant);
  }

  getUser(userId: string, tenantId: string): Observable<User> {
    return this.http.get<User>(`${this.userUrl}/${userId}/${tenantId}`);
  }

  getUsers(tenantId: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.userUrl}/all/${tenantId}`);
  }

  getTenants(): Observable<Tenant[]> {
    return this.http.get<Tenant[]>(`${this.userUrl}/tenant/all`);
  }
}

export interface Tenant {
  displayName: string;
  tenantId: string;
}
