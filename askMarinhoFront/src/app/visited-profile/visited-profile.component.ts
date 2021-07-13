import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { Post } from '../model/Post';
import { User } from '../model/User';
import { AuthService } from '../service/auth.service';
import { HomeService } from '../service/home.service';
import { ProfileService } from '../service/profile.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-visited-profile',
  templateUrl: './visited-profile.component.html',
  styleUrls: ['./visited-profile.component.css']
})
export class VisitedProfileComponent implements OnInit {

  idUser: number
  user: User = new User()

  postagensUser: Post[]

  idPostComentado: number

  postLike: Post = new Post()
  comentarioLike: Comment = new Comment()

  postReport: Post = new Post()
  comentarioReport: Comment = new Comment()


  constructor(
    private router: Router,
    private authService: AuthService,
    private homeService: HomeService,
    private route: ActivatedRoute,
    private userService: UserService,
    private profileService: ProfileService,
  ) { }

  ngOnInit(){
    if (environment.token == '') {

      this.router.navigate(['/login-page'])
      
    } else {
      window.scroll(0,0)
      this.idUser = this.route.snapshot.params['id']
      this.findByIdUser(this.idUser)
    }
  }

  findByIdUser(id: number) {
    this.homeService.getUserById(id).subscribe((resp: User) => {
      this.user = resp
      console.log(this.user.userName)
    })
  }
  pegarPeloId() {
    this.homeService.getUserById(environment.id).subscribe((resp: User) => {
      this.user = resp
      this.postagensUser = this.user.posts
    })
  }

  chamou(idPost: number) {
    this.idPostComentado = idPost
  }

  upvotePost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postUpvotePost(environment.id, idPost).subscribe((resp: Post) => {
      this.postLike = resp
      
      this.pegarPeloId()
     })
   }
 
   reportPost(idPost: number) {
    this.userService.refreshToken()
    this.userService.postReportPost(environment.id, idPost).subscribe((resp: Post) => {
      this.postReport = resp
      
      this.pegarPeloId()
     })
   }

}
