import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

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

  getUserById(idUser: number): Observable<User> {
    return this.http.get<User>(`https://askmarinho.herokuapp.com/users/id/${idUser}`, this.token)
  }

  feedUser(idUser: number): Observable<Post[]> {
    return this.http.get<Post[]>(`https://askmarinho.herokuapp.com/users/posts/favorites/${idUser}`, this.token)
  }

  putUser(idUser: number, usuarioAtt: User): Observable<User> {
    return this.http.put<User>(`https://askmarinho.herokuapp.com/users/update/${idUser}`, usuarioAtt, this.token)
  }

  deleteUser(idUser: number): Observable<Object> {
    return this.http.delete<Object>(`https://askmarinho.herokuapp.com/users/delete/${idUser}`, this.token)
  }

  postUpvoteComment(idUser: number, idComment: number): Observable<Comment> {
    return this.http.post<Comment>(`https://askmarinho.herokuapp.com/users/upvotes/comment/${idUser}/${idComment}`, this.token)
  }

  postReportComment(idUser: number, idComment: number): Observable<Comment> {
    return this.http.post<Comment>(`https://askmarinho.herokuapp.com/users/reports/comment/${idUser}/${idComment}`, this.token)
  }

  postUpvotePost(idUser: number, idPost: number): Observable<Post> {
    return this.http.post<Post>(`https://askmarinho.herokuapp.com/users/upvotes/post/${idUser}/${idPost}`, this.token)
  }

  postReportPost(idUser: number, idPost: number): Observable<Post> {
    return this.http.post<Post>(`https://askmarinho.herokuapp.com/users/reports/post/${idUser}/${idPost}`, this.token)
  }

  addFavorite(idUser: number, nome: string): Observable<User> {
    return this.http.put<User>(`https://askmarinho.herokuapp.com/users/add/theme/${idUser}/${nome}`, this.token)
  }

  deleteTag(idUser: number, idTag: number) {
    return this.http.delete(`https://askmarinho.herokuapp.com/users/delete/theme/favorites/${idUser}/${idTag}`, this.token)
  }

}
