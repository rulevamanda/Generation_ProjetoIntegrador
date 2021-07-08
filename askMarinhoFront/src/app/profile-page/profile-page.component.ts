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

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

  idUser = environment.id

  temas: Tag[]
  usuario: User = new User()
  commentsUsuario: Comment[]
  postagensUser: Post[]
  tema: Tag = new Tag()
  idPostComentado: number
  tagAdicionada: Tag = new Tag()

  comentarioEnviado: Comment = new Comment()

  postagemDeletada: Post = new Post()

  idPostagemDelete: number

  idPostEditar: number
  postagemEditada: Post = new Post()
  postagemEnviar: Post = new Post()

  idCommentModif: number

  commentModif: Comment = new Comment()

  idTagDelete: number

  constructor(
    private homeService: HomeService,
    private profileService: ProfileService,
    private router: Router,
    private commentService: CommentService
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
      this.postagensUser = this.usuario.posts
      this.temas = this.usuario.favorites
      this.commentsUsuario = this.usuario.comments
    })
  }

  adicionarTag() {
    this.homeService.refreshToken()
    this.homeService.addFavorite(environment.id, this.tema.tagName).subscribe((resp: 
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
    this.idPostEditar = 0
    this.postagemEditada = new Post()
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

}
