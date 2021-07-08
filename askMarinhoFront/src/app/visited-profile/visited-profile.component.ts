import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import { User } from '../model/User';
import { AuthService } from '../service/auth.service';
import { HomeService } from '../service/home.service';

@Component({
  selector: 'app-visited-profile',
  templateUrl: './visited-profile.component.html',
  styleUrls: ['./visited-profile.component.css']
})
export class VisitedProfileComponent implements OnInit {

  idUser: number
  user: User = new User()

  constructor(
    private router: Router,
    private authService: AuthService,
    private homeService: HomeService,
    private route: ActivatedRoute
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

}
