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
export class HomeService {

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

  getUserById(idUser: number): Observable<User> {
    return this.http.get<User>(`https://askmarinho.herokuapp.com/users/id/${idUser}`, this.token)
  }

  feedUser(idUser: number): Observable<Post[]> {
    return this.http.get<Post[]>(`https://askmarinho.herokuapp.com/users/posts/favorites/${idUser}`, this.token)
  }

  allPosts(): Observable<Post[]> {
    return this.http.get<Post[]>("https://askmarinho.herokuapp.com/posts/all/", this.token)
  }

  postPostagem(idUser: number, themeName: string, postagem: Post): Observable<Post> {
    return this.http.post<Post>(`https://askmarinho.herokuapp.com/users/posts/register/${idUser}/${themeName}`, postagem, this.token)
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

  

  ///https://askmarinho.herokuapp.com/users/add/theme/{idUser}/{tagName}
}
