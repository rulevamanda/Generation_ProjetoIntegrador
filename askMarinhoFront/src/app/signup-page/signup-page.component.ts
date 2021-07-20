import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-signup-page',
  templateUrl: './signup-page.component.html',
  styleUrls: ['./signup-page.component.css']
})
export class SignupPageComponent implements OnInit {

  user: User = new User()
  confirmarSenha: string
  genero: string

  constructor(
    private authService: AuthService,
    private router: Router,
    private alert: AlertsService
  ) { }

  ngOnInit(){
    window.scroll(0,0)
  }

  confirmSenha(event: any) {
    this.confirmarSenha = event.target.value
  }

  generoUser(event: any) {
    this.genero = event.target.value
  }

  cadastrar() {
    this.user.gender = this.genero

   

    if (this.user.password != this.confirmarSenha) {
      this.alert.showAlertDanger('As senhas estão diferentes!')
    } else if (this.user.password == undefined) {
      this.alert.showAlertDanger('A senha não pode ser nula!')
    } else if (this.user.password.length < 3 || this.user.password.length > 16) {
      this.alert.showAlertDanger("A senha deve ter entre 3 e 16 caracteres!")
    } else if (this.user.name == undefined) {
      this.alert.showAlertDanger("O nome não pode ser nulo!")
    } else if (this.user.name.length < 3 || this.user.name.length > 50) {
      this.alert.showAlertDanger("O nome deve ter entre 3 e 50 caracteres!")
    } else if (this.user.email == undefined) {
      this.alert.showAlertDanger('O email não pode ser nulo!')
    } else if (this.user.email.length < 10 || this.user.email.length > 100) {
      this.alert.showAlertDanger("O email deve ter entre 10 e 100 caracteres!")
    } else if (!this.user.email.includes("@")) {
      this.alert.showAlertDanger("O email está inválido!")
    } else if (this.user.userName == undefined) {
      this.alert.showAlertDanger("O nome de usuário não pode ser nulo!")
    } else if (this.user.userName.length < 3 || this.user.userName.length > 15) {
      this.alert.showAlertDanger("O nome de usuário deve ter entre 3 e 15 caracteres!")
    } else if (this.user.gender == undefined) {
      this.alert.showAlertDanger("Marque um valor para o gênero")
    } else if (this.user.birth == undefined) {
      this.alert.showAlertDanger("A data de nascimento não pode ser nula")
    } else if (this.user.birth.length > 10) {
      this.alert.showAlertDanger("Data de nascimento inválida!")
    } else {
      this.authService.cadastrar(this.user).subscribe((resp: User) => {
        this.user = resp

        this.router.navigate(['/login-page'])

        this.alert.showAlertSuccess('Usuário cadastrado com sucesso!')
      }, erro => {

        if (erro.status == 303) {
          this.alert.showAlertDanger("O nome não pode conter caracteres especiais")
        } else if (erro.status == 403) {
          this.alert.showAlertDanger("O email possui caracteres inválidos")
        } else if (erro.status == 405) {
          this.alert.showAlertDanger("O nome de usuário não pode conter caracteres especiais")
          console.clear()
        } else {
          this.alert.showAlertYellow("Dados incorretos ou nome de usuário e/ou email já estão sendo utilizados")
        }

      })
    }
  }

}
