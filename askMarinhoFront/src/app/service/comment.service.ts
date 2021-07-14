import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

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

  commentFindById(id: number): Observable<Comment> {
    return this.http.get<Comment>(`https://askmarinho.herokuapp.com/comments/id/${id}`, this.token)
  }

  postComment(idUser: number, idPost: number, comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`https://askmarinho.herokuapp.com/users/comments/register/${idUser}/${idPost}`, comment, this.token)
  }

  putComment(idComment: number, commentAtt: Comment): Observable<Comment> {
    return this.http.put<Comment>(`https://askmarinho.herokuapp.com/users/comments/update/${idComment}`, commentAtt, this.token)
  }
  
  deleteComment(idComment: number) {
    return this.http.delete(`https://askmarinho.herokuapp.com/users/comments/delete/${idComment}`, this.token)
  }

}
