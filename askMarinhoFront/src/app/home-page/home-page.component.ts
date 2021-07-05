import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';
import { User } from '../model/User';
import { HomeService } from '../service/home.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  usuario: User = new User()
  tema: Tag = new Tag()
  temaParaPost: Tag = new Tag()
  novoPost: Post = new Post()
  temas: Tag[]
  postsFeed: Post[]
  postagensUser: Post[]
  todosPosts: Post[]
  postLike: Post = new Post()

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
      this.pegarFeed()
      this.getAllPosts()
    }
  }

  getAllPosts() {
    this.homeService.allPosts().subscribe((resp: Post[]) => {
      this.todosPosts = resp
      console.log(this.todosPosts.toString())
    })
  }

  pegarFeed() {
    this.homeService.feedUser(environment.id).subscribe((resp: Post[]) => {
      this.postsFeed = resp
      console.log(this.postsFeed.length)
    })
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

  postarPostagem() {
    this.homeService.postPostagem(environment.id, this.temaParaPost.tagName, this.novoPost).subscribe((resp: Post) => {
      alert("Postagem cadastrada com sucesso!")
      this.novoPost = resp
      this.getAllPosts()
      this.pegarPeloId()
      this.pegarFeed()
      this.novoPost = new Post()
      this.temaParaPost = new Tag()
      console.log("Funcionou")
    })
  } 

}
