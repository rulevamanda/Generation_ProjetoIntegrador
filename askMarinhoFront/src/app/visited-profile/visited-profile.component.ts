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
  postReport: Post = new Post()

  comentarioLike: Comment = new Comment()
  comentarioReport: Comment = new Comment()
  comentarioNoPost: Comment = new Comment()

  key = 'date'
  reverse = true
  reverso = false
  
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
    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
     })
  }

  pegarPeloId() {
    this.userService.getUserById(this.idUser).subscribe((resp: User) => {
      this.user = resp
      this.postagensUser = this.user.posts
    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
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
     }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
     })
   }
 
   reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
     }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
     })
   }

   upvoteComment(idComment: number) {
    this.userService.refreshToken()
    this.userService.postUpvoteComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioLike = resp
      
       this.pegarPeloId()

    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
     })
  }

  reportComment(idComment: number) {
    this.userService.refreshToken()
     this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
       this.comentarioReport = resp
       
        this.pegarPeloId()
  
     }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
     })
   }

   comentar() {

    if (this.comentarioNoPost.text == undefined) {
      this.alert.showAlertDanger("Comentário não pode ser nulo!")
    } else if (this.comentarioNoPost.text.length < 1 || this.comentarioNoPost.text.length > 155) {
      this.alert.showAlertDanger("Comentário deve ter entre 1 e 155 caracteres!")
    } else if (this.comentarioNoPost.text.includes("viado")) {
      this.alert.showAlertYellow("\"viado\" é uma palavra imprópria.")
    } else if (this.comentarioNoPost.text.includes("cuzão")) {
        this.alert.showAlertYellow("\"cuzão\" é uma palavra imprópria.")
    } else if (this.comentarioNoPost.text.includes("putinha")) {
        this.alert.showAlertYellow("\"putinha\" é uma palavra imprópria.")
    } else if (this.comentarioNoPost.text.includes("buceta")) {
        this.alert.showAlertYellow("\"buceta\" é uma palavra imprópria.")
    } else {
      this.commentService.postComment(environment.id, this.idPostComentado, this.comentarioNoPost).subscribe((resp: Comment) => {
        this.comentarioNoPost = resp
        this.alert.showAlertSuccess("Comentário adicionado com sucesso!")
        
        this.pegarPeloId()
      
        this.comentarioNoPost = new Comment()
      }, err => {
        if (err.status == 500) {
          this.alert.showAlertDanger("Por favor atualize a página")
        } else if (err.status == 403) {
          this.alert.showAlertDanger("O texto não pode ser vazio")
        } else if (err.status == 400) {
          this.alert.showAlertDanger("Postagem não existe, por favor atualize a página")
        } else if (err.status == 404) {
          this.alert.showAlertDanger("Usuário não existe, por favor atualize a página")
        }
      })
    }
  }

}
