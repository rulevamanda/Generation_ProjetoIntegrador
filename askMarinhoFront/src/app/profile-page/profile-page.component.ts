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
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

  idUser = environment.id
  usuario: User = new User()

  temas: Tag[]
  tagAdicionada: Tag = new Tag()
  tagDelete: Tag = new Tag()
  tema: Tag = new Tag()
  idTagDelete: number
  tagFoiChamada = false
  
  postagensUser: Post[]
  idPostComentado: number
  idPostagemDelete: number
  postagemDeletada: Post = new Post()
  idPostEditar: number
  postagemEditada: Post = new Post()
  postagemEnviar: Post = new Post()

  comentarioEnviado: Comment = new Comment()
  commentsUsuario: Comment[]
  idCommentModif: number
  commentModif: Comment = new Comment()
  comentarioNoPost: Comment = new Comment()

  postLike: Post = new Post()
  comentarioLike: Comment = new Comment()
  postReport: Post = new Post()
  comentarioReport: Comment = new Comment()

  key = 'data'
  reverse = true
  
  constructor(
    private router: Router,
    private commentService: CommentService,
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
      this.userService.refreshToken()
      this.pegarPeloId()
    }
  }

  tagChamada() {
    return this.tagFoiChamada
  }

  pegarPeloId() {
    this.userService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
      this.commentsUsuario = this.usuario.comments
    })
  }

  adicionarTag() {
    this.tagService.refreshToken()
    this.userService.refreshToken()
    this.userService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        
        this.pegarPeloId()
        this.tema = new Tag()
        this.usuario = resp
      })
    this.alert.showAlertSuccess("Tag favorita adicionada com sucesso!")
  }

  chamou(idPost: number) {
    this.idPostComentado = idPost
  }

  adicionarNovoTema() {
    this.postService.addTagPostagem(this.tagAdicionada.tagName, this.idPostEditar).subscribe((resp: Post) => {
      this.alert.showAlertSuccess("Adicionada")
      this.tagAdicionada = new Tag()
      this.pegarPeloId()
      this.findByIdPost()
    })
  }

  idPostEdit(idPostagem: number) {
    this.idPostEditar = idPostagem
    this.findByIdPost()
  }

  findByIdPost() {
    this.postService.postagemFindById(this.idPostEditar).subscribe((resp: Post) => {
      this.postagemEditada = resp
    })
  }

  findByIdPostagem() {
    this.postService.postagemFindById(this.idPostagemDelete).subscribe((resp: Post) => {
      this.postagemDeletada = resp
    })
  }

  atualizarPostagem() {
    this.postagemEnviar.title = this.postagemEditada.title
    this.postagemEnviar.date = this.postagemEditada.date
    this.postagemEnviar.description = this.postagemEditada.description
    this.postagemEnviar.urlImage = this.postagemEditada.urlImage

    this.postService.putPostagem(this.idPostEditar, this.postagemEnviar).subscribe((resp: Post) => {
      console.log("Editada")
      this.postagemEditada = new Post()
      this.idPostEditar = 0
      this.pegarPeloId()
      this.alert.showAlertSuccess("Postagem editada com sucesso!")
    }, err => {
      this.alert.showAlertDanger("Há algum dado incorreto. Verifique e tente novamente.")
    }) 
  }

  removerTagPost(idTag: number) {
    this.postService.deleteTagPostagem(this.idPostEditar, idTag).subscribe((resp: Post) => {
      console.log("Oi")
      this.pegarPeloId()
      this.findByIdPost()
    }, err => {
      console.log("nao")
    })
  }

  limpar() {
    this.tema = new Tag()
    this.tagAdicionada = new Tag()
    this.tagDelete = new Tag()
    this.idTagDelete = 0
    this.tagFoiChamada = false

    this.idPostEditar = 0
    this.postagemEditada = new Post()
    this.idPostComentado = 0
    this.idPostagemDelete = 0
    this.postagemDeletada = new Post()
    this.postagemEnviar = new Post()

    this.comentarioEnviado = new Comment()
    this.idCommentModif = 0
    this.commentModif = new Comment()
  }

  idPostDelete(idPost: number) {
    this.idPostagemDelete = idPost
    this.findByIdPostagem()
  }

  deletarPostagem() {
    console.log(this.idPostagemDelete)
    this.postService.deletePostagem(this.idPostagemDelete).subscribe(() => {
      console.log("Excluiu")
      this.limpar()
      this.pegarPeloId()
      this.alert.showAlertSuccess("Postagem excluída com sucesso!")
    }, erro => {
      this.alert.showAlertSuccess("Postagem excluída com sucesso!")
      this.limpar()
      this.pegarPeloId()
    })
    
  }

  findByIdComment() {
    this.commentService.commentFindById(this.idCommentModif).subscribe((resp: Comment) => {
      this.commentModif = resp
    })
  }

  CommentModificado(idComentario: number) {
    this.idCommentModif = idComentario
    this.findByIdComment()
  }

  editarComment() {
    this.comentarioEnviado.text = this.commentModif.text
    console.log(this.comentarioEnviado)
   console.log(this.commentModif)
    this.commentService.putComment(this.idCommentModif, this.comentarioEnviado).subscribe((resp: Comment) => {
      console.log("Editou")
      this.alert.showAlertSuccess("Comentário editado!")
      this.pegarPeloId()
    }, erro => {
      console.log(this.commentModif)
      console.log(this.commentModif.text)
    })
  }

  deletarComment() {
    this.commentService.deleteComment(this.idCommentModif).subscribe(() => {
      console.log("Deletado")
      this.pegarPeloId()
      this.idCommentModif = 0
      this.commentModif = new Comment()
    }, erro => {
      console.log("Deletado tbm")
      this.pegarPeloId()
      this.idCommentModif = 0
      this.commentModif = new Comment()
    })
  }

  idTagFavorita(idTagFav: number) {
    this.idTagDelete = idTagFav
    this.tagService.tagFindById(this.idTagDelete).subscribe((resp: Tag) => {
      this.tagDelete = resp
      this.tagFoiChamada = true
      this.tagChamada()
    })
  }

  deleteFavoriteTag() {
    this.userService.deleteTag(environment.id, this.idTagDelete).subscribe(() => {
      
      
    }, objeto => {
      if(objeto.status == 202) {
        this.alert.showAlertSuccess("Tema favorito excluído")
        this.pegarPeloId()
      } else if (objeto.status == 200) {
        this.alert.showAlertYellow("Esse usuário não possui esse tema")
      } else if (objeto.status == 400) {
        this.alert.showAlertDanger("Tema e/ou usuário não existem")
      }
      
    })
  }

  upvoteComment(idComment: number) {
    this.userService.refreshToken()
    this.userService.postUpvoteComment(environment.id, idComment).subscribe((resp: Comment) => {
      this.comentarioLike = resp
      
       this.pegarPeloId()
      // this.pegarFeed()
      // this.getAllPosts()
    })
  }

  reportComment(idComment: number) {
    this.userService.refreshToken()
     this.userService.postReportComment(environment.id, idComment).subscribe((resp: Comment) => {
       this.comentarioReport = resp
       
        this.pegarPeloId()
      //  this.pegarFeed()
      //  this.getAllPosts()
     })
   }

   upvotePost(idPost: number) {
    this.userService.refreshToken()
     this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
       this.postLike = resp
       
        this.pegarPeloId()
      //  this.pegarFeed()
      //  this.getAllPosts()
     })
   }
 
   reportPost(idPost: number) {
     this.userService.refreshToken()
     this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
       this.postReport = resp
       
       this.pegarPeloId()
      //  this.pegarFeed()
      //  this.getAllPosts()
     })
   }

   comentar() {
    this.commentService.postComment(environment.id, this.idPostComentado, this.comentarioNoPost).subscribe((resp: Comment) => {
      this.comentarioNoPost = resp
      this.alert.showAlertSuccess("Comentário adicionado com sucesso!")
      
      this.pegarPeloId()
     
      this.comentarioNoPost = new Comment()
    })
  }

}
