import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';

const endpoint = 'http://localhost:8080/CoreMensajeria_war_exploded/M02_Companies/';
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })};

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { 
      
   }
   
   private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  getCompanies(): Observable<any> {
    return this.http.get(endpoint + 'GetCompanies?id=1').pipe(
      map(this.extractData));
  }
  
  addCompany (company): Observable<any> {
    return this.http.post<any>(endpoint + 'M02_Company/AddCompany', JSON.stringify(company), httpOptions).pipe(
      tap((company) => console.log(`Company added w/ ${company._name}`)),
    );
  }
}