import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { UserLogin } from '../model/UserLogin';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  userLogin: UserLogin = new UserLogin

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    
    window.scroll(0,0)
  }

  entrar() {
    this.authService.entrar(this.userLogin).subscribe((resp: UserLogin) => {
      this.userLogin = resp

      environment.token = this.userLogin.token
      environment.id = this.userLogin.idUser
      environment.foto = this.userLogin.urlImage
      environment.nome = this.userLogin.name

      this.router.navigate(['/home'])
    }, erro => {
      if (erro.status == 500) {
        alert("Email ou senha estão incorretos")
      } else {
        alert("Email ou senha estão incorretos")
      }
    })
  }

}
