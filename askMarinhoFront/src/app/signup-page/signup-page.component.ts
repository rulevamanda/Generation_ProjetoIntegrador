import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/User';
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
    private router: Router
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
      alert('As senhas estão diferentes!')
    } else {
      this.authService.cadastrar(this.user).subscribe((resp: User) => {
        this.user = resp

        this.router.navigate(['/login-page'])

        alert('Usuario cadastrado!')
      } , erro => {
        if (erro.status == 400) {
          alert("Dados incorretos ou usuário já cadastrado")
        } else {
          alert("Dados incorretos ou usuário já cadastrado")
        }
      })
    }
  }

}
