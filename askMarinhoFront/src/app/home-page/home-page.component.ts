import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { CommentService } from '../service/comment.service';
import { HomeService } from '../service/home.service';
import { TemasService } from '../service/tag.service';
import { UserService } from '../service/user.service';

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
  comentarioLike: Comment = new Comment()
  postReport: Post = new Post()
  comentarioReport: Comment = new Comment()

  comentarioNoPost: Comment = new Comment()

  idPostComentado: number

  constructor(
    private homeService: HomeService,
    private commentService: CommentService,
    private router: Router,
    private alert: AlertsService,
    private temaService: TemasService,
    private userService: UserService
  ) { }

  ngOnInit() {

    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.homeService.refreshToken()
      this.pegarPeloId()
      this.getAllPosts()
      this.pegarFeed()
    }    
  }

  getAllPosts() {
    this.homeService.allPosts().subscribe((resp: Post[]) => {
      this.todosPosts = resp
    })
  }

  pegarFeed() {
    this.homeService.feedUser(environment.id).subscribe((resp: Post[]) => {
      this.postsFeed = resp
    })
  }

  pegarPeloId() {
    this.homeService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
    })
  }

  adicionarTag() {
    this.temaService.refreshToken()
    this.temaService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        
        this.pegarPeloId()
        this.pegarFeed()
        this.getAllPosts()
        this.tema = new Tag()
        this.usuario = resp
      })
    this.alert.showAlertSuccess("Tag favorita adicionada com sucesso!")
  }

  postarPostagem() {
    this.homeService.postPostagem(environment.id, this.temaParaPost.tagName, this.novoPost).subscribe((resp: Post) => {
      this.alert.showAlertSuccess("Postagem cadastrada com sucesso!")
      this.novoPost = resp
      this.getAllPosts()
      this.pegarPeloId()
      this.pegarFeed()
      this.novoPost = new Post()
      this.temaParaPost = new Tag()
    })
  } 

  chamou(idPost: number) {
    this.idPostComentado = idPost
  }

  comentar() {
    this.commentService.postComment(environment.id, this.idPostComentado, this.comentarioNoPost).subscribe((resp: Comment) => {
      this.comentarioNoPost = resp
      this.alert.showAlertSuccess("Comentado com sucesso")
      this.getAllPosts()
      this.pegarPeloId()
      this.pegarFeed()
      this.comentarioNoPost = new Comment()
    })
  }

  upvoteComment(idComment: number) {
    this.userService.refreshToken()
    this.userService.postUpvoteComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioLike = resp
      
      this.pegarPeloId()
      this.pegarFeed()
      this.getAllPosts()
    })
  }

  reportComment(idComment: number) {
   this.userService.refreshToken()
    this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioReport = resp
      
      this.pegarPeloId()
      this.pegarFeed()
      this.getAllPosts()
    })
  }

  upvotePost(idPost: number) {
   this.userService.refreshToken()
    this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
      this.postLike = resp
      
      this.pegarPeloId()
      this.pegarFeed()
      this.getAllPosts()
    })
  }

  reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
      this.pegarFeed()
      this.getAllPosts()
    })
  }

}
