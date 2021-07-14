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
  postsByTags: Post[]
  todosPosts: Post[]
  postLike: Post = new Post()
  comentarioLike: Comment = new Comment()
  postReport: Post = new Post()
  comentarioReport: Comment = new Comment()

  comentarioNoPost: Comment = new Comment()

  idPostComentado: number

  tituloPost: string
  tagNameFeed: string
  tagNamePost: string

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
      this.tituloPost = ''
      this.tagNameFeed = ''
      this.tagNamePost = ''
      this.pegarPeloId()
      this.getAllPosts()
      this.pegarFeed()
      this.getAllPostsByAllTags()
      
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

    }, err => {
      if (err.status == 500) {
        this.alert.showAlertInfo("Por favor atualize a página")
      }
    })
  }

  pegarPeloId() {
    this.userService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      this.temas = this.usuario.favorites
    } , err => {
      if (err.status == 500) {
        this.alert.showAlertInfo("Por favor atualize a página")
      }
    })
  }

  adicionarTag() {
    this.tagService.refreshToken()
    this.userService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        this.alert.showAlertSuccess("Tag favorita adicionada com sucesso!")
        this.pegarPeloId()
        if(this.tagNameFeed == '') {

          this.pegarFeed()
          
        } else {
    
            this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
              this.postsFeed = resp
        
              this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
            })
        }
        if (this.tituloPost != '') {

          this.getAllPosts()
          
        } else {
    
          this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
            this.todosPosts = resp
            this.comentarioNoPost = new Comment()
          })
    
        }

        if (this.tagNamePost == '') {

          this.getAllPostsByAllTags()
    
        } else {
          
          this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
            this.postsByTags = resp
          })
    
        }
        
        this.tema = new Tag()
        this.usuario = resp
      }, err => {
        if (err.status == 500) {
          this.alert.showAlertInfo("Por favor atualize a página")
        } else if (err.status == 403) {
          this.alert.showAlertDanger("A tag não pode conter caracteres especiais")
        } else if (err.status == 400) {
          this.alert.showAlertDanger("Usuário não existe, por favor recarregue a página")
        }
      })
    
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
        if(this.tagNameFeed == '') {

          this.pegarFeed()
    
        } else {
    
        this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
          this.postsFeed = resp
    
          this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
        })
      }

      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()

      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })

      }
        this.novoPost = new Post()
        this.temaParaPost = new Tag()
    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      } else if (err.status == 303) {
        this.alert.showAlertDanger("O nome da tag não pode ter caracteres especiais")
      } else if (err.status == 403) {
        this.alert.showAlertDanger("O título não pode ser vazio")
      } else if (err.status == 405) {
        this.alert.showAlertDanger("A descrição não pode ser vazia")
      } else if (err.status == 400) {
        this.alert.showAlertDanger("Usuário não existe, por favor atualize a página")
      }
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
        if(this.tagNameFeed == '') {

          this.pegarFeed()
    
        } else {
    
        this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
          this.postsFeed = resp
    
          this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
        })
      }

      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()

      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })

      }
        this.comentarioNoPost = new Comment()
    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      } else if (err.status == 403) {
        this.alert.showAlertDanger("O texto não pode ser vazio")
      } else if (err.status == 400) {
        this.alert.showAlertDanger("Postagem não existe, por favor atualize a página")
      } else if (err.status == 404) {
        this.alert.showAlertDanger("Usuário não existe, por favor atualize a página")
      }
    })
  }

  upvoteComment(idComment: number) {
    this.userService.refreshToken()
    this.userService.postUpvoteComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioLike = resp
      
      this.pegarPeloId()
      if(this.tagNameFeed == '') {

        this.pegarFeed()
  
      } else {
  
      this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
        this.postsFeed = resp
  
        this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
      })
    }
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }

      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()
  
      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })
  
      }
    }, err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
    })
  }

  reportComment(idComment: number) {
   this.userService.refreshToken()
    this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioReport = resp
      
      this.pegarPeloId()
      if(this.tagNameFeed == '') {

        this.pegarFeed()
  
      } else {
  
      this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
        this.postsFeed = resp
  
        this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
      })
    }
      
      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }

      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()
  
      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })
  
      }
    } , err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
    })
  }

  upvotePost(idPost: number) {
   this.userService.refreshToken()
    this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
      this.postLike = resp
      
      this.pegarPeloId()
      if(this.tagNameFeed == '') {

        this.pegarFeed()
  
      } else {
  
      this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
        this.postsFeed = resp
  
        this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
      })
    }

      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }
      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()
  
      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })
  
      }
    } , err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
    })
  }

  reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
      if(this.tagNameFeed == '') {

        this.pegarFeed()
  
      } else {
  
      this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
        this.postsFeed = resp
  
        this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
      })
    }

      if (this.tituloPost == '') {

        this.getAllPosts()
        this.comentarioNoPost = new Comment()
      } else {
  
        this.postService.getByTituloPostagem(this.tituloPost).subscribe((resp: Post[]) => {
          this.todosPosts = resp
          this.comentarioNoPost = new Comment()
        })
  
      }

      if (this.tagNamePost == '') {

        this.getAllPostsByAllTags()
  
      } else {
        
        this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
          this.postsByTags = resp
        })
  
      }
      
    } , err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
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
      } , err => {
        if (err.status == 500) {
          this.alert.showAlertDanger("Por favor atualize a página")
        }
      })

    }

  }

  findPostsByTagNameFeed() {

    if(this.tagNameFeed == '') {

      this.pegarFeed()

    } else {

      this.postService.getPostByTagNameFeed(environment.id, this.tagNameFeed).subscribe((resp: Post[]) => {
        this.postsFeed = resp

        this.postsFeed.sort((a, b) => (a.date < b.date) ? -1 : 1)
      } , err => {
        if (err.status == 500) {
          this.alert.showAlertDanger("Por favor atualize a página")
        }
      })
    }
  }

  getAllPostsByAllTags() {
    this.postService.getPostByAllTags().subscribe((resp: Post[]) => {
      this.postsByTags = resp
    } , err => {
      if (err.status == 500) {
        this.alert.showAlertDanger("Por favor atualize a página")
      }
    })
  }

  getAllPostsByTagNames() {

    if (this.tagNamePost == '') {

      this.getAllPostsByAllTags()

    } else {
      
      this.postService.getPostByTagNames(this.tagNamePost).subscribe((resp: Post[]) => {
        this.postsByTags = resp
      } , err => {
        if (err.status == 500) {
          this.alert.showAlertDanger("Por favor atualize a página")
        }
      })

    }
    
  }

}
