import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../model/customer.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost = "http://localhost:8082" // on peut l'ajouter dans environment
  constructor(private http:HttpClient) {
  }

  public getCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.backendHost+"/customers")
  }
  public searchCustomers(kw: string):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.backendHost+"/customers/search?keyword="+kw)
  }

  public saveCustomer(customer: Customer):Observable<Customer>{
    return this.http.post<Customer>(this.backendHost+"/customers",customer)
  }

  public deleteCustomer(id: string){
    return this.http.delete(this.backendHost+"/customers/"+id)
  }
}
