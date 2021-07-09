import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';
import { AuthService } from '../service/auth.service';
import { HomeService } from '../service/home.service';
import { ProfileService } from '../service/profile.service';

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
    private authService: AuthService,
    private homeService: HomeService,
    private route: ActivatedRoute,
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.idUser = this.route.snapshot.params['id']
      this.findByIdUser(this.idUser)
    }
  }

  findByIdUser(id: number) {
    this.homeService.getUserById(id).subscribe((resp: User) => {
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
      alert('As senhas estão diferentes!')
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
      this.authService.putUser(this.idUser, this.usuarioEnviado).subscribe((resp: User) => {
        this.user = resp

        this.router.navigate(['/login-page'])
        environment.token = ''
        environment.nome = ''
        environment.id = 0
        environment.foto = ''
        alert('Usuario atualizado!')
      } , erro => {
        if (erro.status == 400) {

          alert("Dados incorretos ou usuário já cadastrado")
        } else {
          alert("Dados incorretos ou usuário já cadastrado")
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
        this.profileService.deleteComment(this.user.comments[i].idComment).subscribe((resp: Object) => {
 
        }, apagou => {

        })
      }

      for (j = 0; j < this.numPosts; j++) {
        console.log(this.user.posts[j].idPost)
        this.profileService.deletePostagem(this.user.posts[j].idPost).subscribe((resp: Object) => {

        }, apagou => {
          if (apagou.status == 500) {
            this.profileService.deletePostagem(this.user.posts[j].idPost).subscribe((resp: Object) => {

            })
          }
   
      })
    }
    j=0
    i=0

        this.profileService.deleteUser(this.idUser).subscribe((resp: Object) => {

        }, deletou => {
          if (deletou.status == 200) {
            alert("Usuário deletado com sucesso!")
          
            environment.token = ''
            environment.nome = ''
            environment.id = 0
            environment.foto = ''

            console.clear()
            this.router.navigate(['/login-page'])        
          } else if (deletou.status == 500) {
            console.log("Clique novamente")
          } else if (deletou.status == 400) {
            alert("Usuário não existe")
            
          }
        })
    }
  

}
