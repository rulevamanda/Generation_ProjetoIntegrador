import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Post } from '../model/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

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

  postagemFindById(id: number): Observable<Post>{
    return this.http.get<Post>(`https://askmarinho.herokuapp.com/posts/id/${id}`, this.token)
  }

  allPosts(): Observable<Post[]> {
    return this.http.get<Post[]>("https://askmarinho.herokuapp.com/posts/all/", this.token)
  }

  getByTituloPostagem(title: string): Observable<Post[]> {
    return this.http.get<Post[]>(`https://askmarinho.herokuapp.com/posts/title/${title}`, this.token)
  }

  postPostagem(idUser: number, themeName: string, postagem: Post): Observable<Post> {
    return this.http.post<Post>(`https://askmarinho.herokuapp.com/users/posts/register/${idUser}/${themeName}`, postagem, this.token)
  }

  addTagPostagem(themeName: string, idPost: number): Observable<Post> {
    return this.http.put<Post>(`https://askmarinho.herokuapp.com/users/posts/add/theme/${themeName}/${idPost}`, this.token)
  }

  putPostagem(id: number, postAtt: Post): Observable<Post> {
    return this.http.put<Post>(`https://askmarinho.herokuapp.com/users/posts/update/${id}`, postAtt, this.token)
  }

  deleteTagPostagem(idPost: number, idTheme: number): Observable<Post> {
    return this.http.delete<Post>(`https://askmarinho.herokuapp.com/users/posts/delete/theme/${idTheme}/${idPost}`, this.token)
  }

  deletePostagem(id: number) {
    return this.http.delete(`https://askmarinho.herokuapp.com/users/posts/delete/${id}`, this.token)
  }
}
