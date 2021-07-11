import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class TemasService {

  constructor(
    private http: HttpClient
  ) { }

  token = {
    headers: new HttpHeaders().set('Authorization', environment.token)
  }

  refreshToken() {
    this.token = {
      headers: new HttpHeaders().set('Authorization', environment.token)
    }
  }
  addFavorite(idUser: number, nome: string): Observable<User> {
    return this.http.put<User>(`https://askmarinho.herokuapp.com/users/add/theme/${idUser}/${nome}`, this.token)
  }
}
