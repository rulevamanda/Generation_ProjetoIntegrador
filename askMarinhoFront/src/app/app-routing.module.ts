import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditPerfilComponent } from './edit-perfil/edit-perfil.component';
import { FooterComponent } from './footer/footer.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SignupPageComponent } from './signup-page/signup-page.component';

const routes: Routes = [
  {path: '', redirectTo:'login-page', pathMatch:'full'},
  {path: 'login-page', component: LoginPageComponent},
  {path: 'signup-page', component: SignupPageComponent},
  {path: 'edit-perfil', component: EditPerfilComponent},
  {path: 'footer', component: FooterComponent},
  {path: 'navbar', component: NavbarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }