import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TestService {

  constructor(private http: HttpClient) {
  }

  test(): Observable<any> {
    return this.http.get('http://localhost:8080/test', {headers: this.getHeaders()});
  }

  getHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    return headers;
  }

}
