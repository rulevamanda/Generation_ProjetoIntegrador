import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { CommentService } from '../service/comment.service';
import { PostService } from '../service/post.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-edit-perfil',
  templateUrl: './edit-perfil.component.html',
  styleUrls: ['./edit-perfil.component.css']
})
export class EditPerfilComponent implements OnInit {

  usuarioEnviado: User = new User()
  user: User = new User()
  confirmarSenha: string
  genero: string
  idUser: number
  numPosts: number
  numComments: number

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private alert: AlertsService,
    private userService: UserService,
    private commentService: CommentService,
    private postService: PostService
  ) { }

  ngOnInit() {
    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      this.userService.refreshToken()
      window.scroll(0,0)
      this.idUser = this.route.snapshot.params['id']
      this.findByIdUser(this.idUser)
    }
  }

  findByIdUser(id: number) {
    this.userService.getUserById(id).subscribe((resp: User) => {
      this.user = resp
      console.log(this.user.userName)
    })
  }

  confirmSenha(event: any) {
    this.confirmarSenha = event.target.value
  }

  generoUser(event: any) {
    this.genero = event.target.value
  }  

  atualizar() {

    this.user.gender = this.genero

    if (this.user.password != this.confirmarSenha) {
      this.alert.showAlertDanger('As senhas estão diferentes!')
    } else {
      this.usuarioEnviado.gender = this.user.gender
      this.usuarioEnviado.description = this.user.description
      this.usuarioEnviado.email = this.user.email
      this.usuarioEnviado.birth = this.user.birth
      this.usuarioEnviado.idUser = this.user.idUser
      this.usuarioEnviado.name = this.user.name
      this.usuarioEnviado.password = this.user.password
      this.usuarioEnviado.urlImage = this.user.urlImage
      this.usuarioEnviado.userName = this.user.userName
      
      this.userService.refreshToken()

      this.userService.putUser(this.idUser, this.usuarioEnviado).subscribe((resp: User) => {
        this.user = resp

        this.router.navigate(['/login-page'])
        environment.token = ''
        environment.nome = ''
        environment.id = 0
        environment.foto = ''
        this.alert.showAlertSuccess('Usuario atualizado!')
      } , erro => {
        if (erro.status == 400) {

          this.alert.showAlertYellow("Dados incorretos ou usuário já cadastrado")
        } else {
          this.alert.showAlertYellow("Dados incorretos ou usuário já cadastrado")
        }
      })
    }
  }

  deleteConta() {

    this.numComments = this.user.comments.length
    this.numPosts = this.user.posts.length

    
    let i : number
    let j : number


      for (i = 0; i < this.numComments; i++) {
        console.log(this.user.comments[i].idComment)
        this.commentService.deleteComment(this.user.comments[i].idComment).subscribe((resp: Object) => {
 
        }, apagou => {

        })
      }

      for (j = 0; j < this.numPosts; j++) {
        console.log(this.user.posts[j].idPost)
        this.postService.deletePostagem(this.user.posts[j].idPost).subscribe((resp: Object) => {

        }, apagou => {
          if (apagou.status == 500) {
            this.postService.deletePostagem(this.user.posts[j].idPost).subscribe((resp: Object) => {

            })
          }
   
      })
    }
    j=0
    i=0

        this.userService.refreshToken()
        this.userService.deleteUser(this.idUser).subscribe((resp: Object) => {

        }, deletou => {
          if (deletou.status == 200) {
            this.alert.showAlertSuccess("Usuário deletado com sucesso!")
          
            environment.token = ''
            environment.nome = ''
            environment.id = 0
            environment.foto = ''

            console.clear()
            this.router.navigate(['/login-page'])        
          } else if (deletou.status == 500) {
            console.log("Clique novamente")
          } else if (deletou.status == 400) {
            this.alert.showAlertDanger("Usuário não existe")
            
          }
        })
    }
  

}
