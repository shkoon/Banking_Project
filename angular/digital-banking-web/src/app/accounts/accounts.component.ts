import {Component, OnInit} from '@angular/core';
import {catchError, map, Observable, throwError} from "rxjs";
import {Account} from "../model/account.model";
import {AccountService} from "../services/account.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accounts$! : Observable<Account>
  errorMessage! : String
  accountFormGroup! : FormGroup
    currentPage=0
    pageSize=3

  constructor(private accountService:AccountService,
              private fb:FormBuilder) {
  }

  ngOnInit(): void {
      this.accountFormGroup = this.fb.group({
          accountId: this.fb.control("")
      })
  }

    handleSearchAccount() {
        let accountId = this.accountFormGroup.value.accountId
        this.accounts$ = this.accountService.getAccountById(accountId,this.currentPage,this.pageSize).pipe(
            catchError(err => {
                this.errorMessage = err.message
                return throwError(err)
            })
        )
    }

    goTo(page: number) {
        this.currentPage = page
        this.handleSearchAccount()
    }
}
