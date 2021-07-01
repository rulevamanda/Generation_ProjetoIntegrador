import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';
import { UserLogin } from '../model/UserLogin';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  entrar(userLogin: UserLogin): Observable<UserLogin>{
    return this.http.post<UserLogin>('https://askmarinho.herokuapp.com/users/login', userLogin)
  }

  cadastrar(user: User): Observable<User>{
    return this.http.post<User>('https://askmarinho.herokuapp.com/users/register', user)
  }

}
