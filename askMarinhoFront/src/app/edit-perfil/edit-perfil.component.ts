import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { AuthService } from '../service/auth.service';
import { HomeService } from '../service/home.service';

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

  constructor(
    private router: Router,
    private authService: AuthService,
    private homeService: HomeService,
    private route: ActivatedRoute,
    private alert: AlertsService
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
      this.authService.putUser(this.idUser, this.usuarioEnviado).subscribe((resp: User) => {
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

}
