import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

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

  commentFindById(id: number): Observable<Comment> {
    return this.http.get<Comment>(`https://askmarinho.herokuapp.com/comments/id/${id}`, this.token)
  }

  putComment(idComment: number, commentAtt: Comment): Observable<Comment> {
    return this.http.put<Comment>(`https://askmarinho.herokuapp.com/users/comments/update/${idComment}`, commentAtt, this.token)
  }

  deleteComment(idComment: number) {
    return this.http.delete(`https://askmarinho.herokuapp.com/users/comments/delete/${idComment}`, this.token)
  }  

  deleteTag(idUser: number, idTag: number) {
    return this.http.delete(`https://askmarinho.herokuapp.com/users/delete/theme/favorites/${idUser}/${idTag}`, this.token)
  } 

  tagFindById(id: number): Observable<Tag> {
    return this.http.get<Tag>(`https://askmarinho.herokuapp.com/theme/id/${id}`, this.token)
  }
  
  
}
