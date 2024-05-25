import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private backendHost = "http://localhost:8082"

  constructor(private http:HttpClient) { }

  public getAccountById(accountId: string, page: number, size: number):Observable<Account>{
    return this.http.get<Account>(`${this.backendHost}/bankaccount/${accountId}/history?page=${page}&size=${size}`);
  }
}
