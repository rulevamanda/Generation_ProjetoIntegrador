import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Comment } from '../model/Comment';
import { Post } from '../model/Post';
import { Tag } from '../model/Tag';
import { User } from '../model/User';
import { CommentService } from '../service/comment.service';
import { HomeService } from '../service/home.service';
import { ProfileService } from '../service/profile.service';
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
  

  constructor(
    private homeService: HomeService,
    private profileService: ProfileService,
    private router: Router,
    private commentService: CommentService,
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

    }
  }

  tagChamada() {
    return this.tagFoiChamada
  }

  pegarPeloId() {
    this.homeService.getUserById(environment.id).subscribe((resp: User) => {
      this.usuario = resp
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
      this.commentsUsuario = this.usuario.comments
    })
  }

  adicionarTag() {
    this.temaService.refreshToken()
    this.temaService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
      User) => {
        
        this.pegarPeloId()
        this.tema = new Tag()
        this.usuario = resp
      })
    alert("teste")
  }

  chamou(idPost: number) {
    this.idPostComentado = idPost
  }

  adicionarNovoTema() {
    this.profileService.addTagPostagem(this.tagAdicionada.tagName, this.idPostEditar).subscribe((resp: Post) => {
      alert("Adicionada")
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
    this.profileService.postagemFindById(this.idPostEditar).subscribe((resp: Post) => {
      this.postagemEditada = resp
    })
  }

  findByIdPostagem() {
    this.profileService.postagemFindById(this.idPostagemDelete).subscribe((resp: Post) => {
      this.postagemDeletada = resp
    })
  }

  atualizarPostagem() {
    this.postagemEnviar.title = this.postagemEditada.title
    this.postagemEnviar.date = this.postagemEditada.date
    this.postagemEnviar.description = this.postagemEditada.description
    this.postagemEnviar.urlImage = this.postagemEditada.urlImage

    this.profileService.putPostagem(this.idPostEditar, this.postagemEnviar).subscribe((resp: Post) => {
      console.log("Editada")
      this.postagemEditada = new Post()
      this.idPostEditar = 0
      this.pegarPeloId()
      alert("Postagem editada")
    }, err => {
      alert("algum dado está incorreto")
    }) 
  }

  removerTagPost(idTag: number) {
    this.profileService.deleteTagPostagem(this.idPostEditar, idTag).subscribe((resp: Post) => {
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
    this.profileService.deletePostagem(this.idPostagemDelete).subscribe(() => {
      console.log("Excluiu")
      this.limpar()
      this.pegarPeloId()
      alert("Postagem excluída")
    }, erro => {
      alert("Postagem excluída")
      this.limpar()
      this.pegarPeloId()
    })
    
  }

  findByIdComment() {
    this.profileService.commentFindById(this.idCommentModif).subscribe((resp: Comment) => {
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
      alert("Comentário editado!")
      this.pegarPeloId()
    }, erro => {
      console.log(this.commentModif)
      console.log(this.commentModif.text)
    })
  }

  deletarComment() {
    this.profileService.deleteComment(this.idCommentModif).subscribe(() => {
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
    this.profileService.tagFindById(this.idTagDelete).subscribe((resp: Tag) => {
      this.tagDelete = resp
      this.tagFoiChamada = true
      this.tagChamada()
    })
  }

  deleteFavoriteTag() {
    this.profileService.deleteTag(environment.id, this.idTagDelete).subscribe(() => {
      
      
    }, objeto => {
      if(objeto.status == 202) {
        alert("Tema favorito retirado")
        this.pegarPeloId()
      } else if (objeto.status == 200) {
        alert("Esse usuário não possui esse tema")
      } else if (objeto.status == 400) {
        alert("Tema e/ou usuário não existem")
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
      alert("comentado com sucesso")
      
      this.pegarPeloId()
     
      this.comentarioNoPost = new Comment()
    })
  }

}
