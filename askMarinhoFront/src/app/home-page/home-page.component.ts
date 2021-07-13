import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';
import { User } from '../model/User';
import { AlertsService } from '../service/alerts.service';
import { CommentService } from '../service/comment.service';
import { PostService } from '../service/post.service';
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

  tituloPost: string

  key = 'data'
  reverse = true

  constructor(
    private commentService: CommentService,
    private router: Router,
    private alert: AlertsService,
    private tagService: TemasService,
    private userService: UserService,
    private postService: PostService
  ) { }

  ngOnInit() {

    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.postService.refreshToken()
      this.commentService.refreshToken()
      this.pegarPeloId()
      this.getAllPosts()
      this.pegarFeed()
    }    
  }

  getAllPosts() {
    this.postService.allPosts().subscribe((resp: Post[]) => {
      this.todosPosts = resp
    })
  }

  ordemData(a: Post, b: Post) {
    return a.date < b.date
  }

  

  pegarFeed() {

    this.userService.feedUser(environment.id).subscribe((resp: Post[]) => {
      this.postsFeed = resp

      this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)

    })
  }

  pegarPeloId() {
    this.userService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
    })
  }

  adicionarTag() {
    this.tagService.refreshToken()
    this.userService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        
        this.pegarPeloId()
        this.pegarFeed()
        if (this.tituloPost == '') {

          this.getAllPosts()
          
        } else {
    
          this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
            this.todosPosts = resp
            this.comentarioNoPost = new Comment()
          })
    
        }
        
        this.tema = new Tag()
        this.usuario = resp
      })
    this.alert.showAlertSuccess("Tag favorita adicionada com sucesso!")
  }

  postarPostagem() {
    this.postService.postPostagem(environment.id, this.temaParaPost.tagName, this.novoPost).subscribe((resp: Post) => {
      this.alert.showAlertSuccess("Postagem cadastrada com sucesso!")
      this.novoPost = resp
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }

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
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
      
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
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
    })
  }

  reportComment(idComment: number) {
   this.userService.refreshToken()
    this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioReport = resp
      
      this.pegarPeloId()
      this.pegarFeed()
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
    })
  }

  upvotePost(idPost: number) {
   this.userService.refreshToken()
    this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
      this.postLike = resp
      
      this.pegarPeloId()
      this.pegarFeed()

      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
    })
  }

  reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
      this.pegarFeed()

      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
      
    })
  }

  findByTituloPostagem() {

    if (this.tituloPost == '') {

      this.getAllPosts()
      this.comentarioNoPost = new Comment()
    } else {

      this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
        this.todosPosts = resp
        this.comentarioNoPost = new Comment()
      })

    }

    
  }

}
