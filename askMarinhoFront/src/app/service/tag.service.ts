import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Tag } from '../model/Tag';
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

  tagFindById(id: number): Observable<Tag> {
    return this.http.get<Tag>(`https://askmarinho.herokuapp.com/theme/id/${id}`, this.token)
  }

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>("https://askmarinho.herokuapp.com/theme/all", this.token)
  }
 

}
