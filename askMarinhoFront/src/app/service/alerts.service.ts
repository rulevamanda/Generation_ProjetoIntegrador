import { Injectable } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal'
import { AlertComponent } from '../alert/alert.component';

@Injectable({
  providedIn: 'root'
})
export class AlertsService {

  constructor(
    private bsModalService: BsModalService
  ) { }

  private showAlert(message: string, tipo: string){
    const bsModalRef: BsModalRef = this.bsModalService.show(AlertComponent)
    bsModalRef.content.type = tipo
    bsModalRef.content.message = message
  }

  //alert vermelho
  showAlertDanger(message: string){
    this.showAlert(message, 'danger')
  }

  //alert verde
  showAlertSuccess(message: string){
    this.showAlert(message, 'success')
  }

  //alert azul
  showAlertInfo(message: string){
    this.showAlert(message, 'info')
  }

  //alert amarelo
  showAlertYellow(message: string){
    this.showAlert(message, 'warning')
  }
}
