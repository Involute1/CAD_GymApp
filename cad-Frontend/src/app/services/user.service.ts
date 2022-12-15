import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userUrl = environment.userAPI;

  constructor(private http: HttpClient) {}

  userHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.userUrl + '/healthcheck');
  }
}
