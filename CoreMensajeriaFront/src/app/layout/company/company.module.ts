import { Component, NgModule, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CompanyComponent } from './company.component';
import { CompanyRoutingModule } from './company-routing.module';
import { PageHeaderModule } from './../../shared';
import { Company } from '../../../model/company-model';
import { CreateCompanyComponent } from './create-company/create-company.component';
import { ModifyCompanyComponent } from './modify-company/modify-company.component';

@NgModule({
  imports: [
    CommonModule,
    CompanyRoutingModule,
    PageHeaderModule,
    FormsModule
  ],
  exports: [CompanyComponent],
  declarations: [CompanyComponent, CreateCompanyComponent, ModifyCompanyComponent]
})
export class CompanyModule implements OnInit {

  private companyList = Array<Company>();
  //private toast: Toast;
  private response: any;
  private empty: boolean;
  selectedCompany: Company;

  constructor() { }
 
  ngOnInit() {
  }

  onSelect(company: Company): void {
    this.selectedCompany = company;
    localStorage.setItem('idCompany', JSON.stringify(company._idCompany));
  }
}

