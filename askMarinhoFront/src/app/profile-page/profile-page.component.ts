import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';
import { User } from '../model/User';
import { HomeService } from '../service/home.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

  temas: Tag[]
  usuario: User = new User()
  postagensUser: Post[]
  tema: Tag = new Tag()

  constructor(
    private homeService: HomeService,
    private router: Router
  ) { }

  ngOnInit() {
    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.homeService.refreshToken()
      this.pegarPeloId()
    }
  }

  pegarPeloId() {
    this.homeService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      console.log("Foii")
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
      console.log(this.temas)
    })
  }

  adicionarTag() {
    this.homeService.refreshToken()
    this.homeService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        
        console.log("foi")
        this.pegarPeloId()
        this.tema = new Tag()
        this.usuario = resp
      })
    alert("teste")
  }

}
