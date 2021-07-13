import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { CommentService } from '../service/comment.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-visited-profile',
  templateUrl: './visited-profile.component.html',
  styleUrls: ['./visited-profile.component.css']
})
export class VisitedProfileComponent implements OnInit {

  idUser: number
  user: User = new User()

  postagensUser: Post[]

  idPostComentado: number

  postLike: Post = new Post()
  comentarioLike: Comment = new Comment()

  postReport: Post = new Post()
  comentarioReport: Comment = new Comment()
  comentarioNoPost: Comment = new Comment()
  
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private commentService: CommentService,
    private alert: AlertsService
  ) { }

  ngOnInit(){
    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.idUser = this.route.snapshot.params['id']
      this.findByIdUser(this.idUser)
      
    }
  }

  redirecionar() {
    window.scroll(0,0)
      this.idUser = this.route.snapshot.params['id']
      this.findByIdUser(this.idUser)
      this.pegarPeloId()
  }


  findByIdUser(id: number) {
    this.userService.getUserById(id).subscribe((resp: User) => {
      this.user = resp
      console.log(this.user.userName)
    })
  }

  pegarPeloId() {
    this.userService.getUserById(this.idUser).subscribe((resp: User) => {
      this.user = resp
      this.postagensUser = this.user.posts
    })
  }

  chamou(idPost: number) {
    this.idPostComentado = idPost
  }

  upvotePost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
      this.postLike = resp
      
      this.pegarPeloId()
     })
   }
 
   reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
     })
   }

   upvoteComment(idComment: number) {
    this.userService.refreshToken()
    this.userService.postUpvoteComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioLike = resp
      
       this.pegarPeloId()
      // this.pegarFeed()
      // this.getAllPosts()
    })
  }

  reportComment(idComment: number) {
    this.userService.refreshToken()
     this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
       this.comentarioReport = resp
       
        this.pegarPeloId()
      //  this.pegarFeed()
      //  this.getAllPosts()
     })
   }

   comentar() {
    this.commentService.postComment(environment.id, this.idPostComentado, this.comentarioNoPost).subscribe((resp: Comment) => {
      this.comentarioNoPost = resp
      this.alert.showAlertSuccess("Comentário adicionado com sucesso!")
      
      this.pegarPeloId()
     
      this.comentarioNoPost = new Comment()
    })
  }

}
