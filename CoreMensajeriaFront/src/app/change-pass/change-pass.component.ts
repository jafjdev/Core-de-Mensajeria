import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from '../shared/services/rest.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-change-pass',
  templateUrl: './change-pass.component.html',
  styleUrls: ['./change-pass.component.scss']
})
export class ChangePassComponent implements OnInit {
  @Input() _verifCode = ''; _newPass = ''; _newPass1 = '';
  constructor(public router: Router, public rest: RestService, private toastr: ToastrService) {

   }

  ngOnInit() {
  }

  
  handleVerification(){
    if (this._verifCode.length > 0 && this._newPass.length > 0 && this._newPass === this._newPass1){
      
      
    }else if (this._verifCode.length == 0 && this._newPass.length == 0 && this._newPass === this._newPass1){
      this.toastr.error('Los campos no pueden estar vacíos')
    }else if (this._verifCode.length == 0 ) {
      this.toastr.error('Debe ingresar un codigo de verificación');
    }else if (this._newPass != this._newPass1 || this._newPass.length == 0 || this._newPass1.length == 0){
      this.toastr.error('Las contraseñas no pueden ser vacías y deben coincidir');}
  }

}
