import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authUrl = environment.authAPI;

  constructor(private http: HttpClient) {

  }

  authHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.authUrl + '/healthcheck');
  }

}
