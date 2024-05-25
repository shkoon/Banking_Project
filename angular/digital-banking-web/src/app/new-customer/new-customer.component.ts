import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {CustomerService} from "../services/customer.service";
import {catchError, throwError} from "rxjs";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit{
  newCustomerFormGroup! : FormGroup
  errorMessage! : string

  constructor(private fb:FormBuilder,
              private customerService:CustomerService,
              private route:Router) {

  }
  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control(null, [Validators.required, Validators.minLength(4)]),
      email: this.fb.control(null, [Validators.required, Validators.email])
    })
  }

  handleSaveCustomer() {
    let customer:Customer = this.newCustomerFormGroup.value
    this.customerService.saveCustomer(customer).subscribe({
      next: data => {
        alert("Customer has been saved")
        this.newCustomerFormGroup.reset()
        this.route.navigateByUrl("/customers")
      },
      error: err => {
        console.log(err)
      }
    })
  }

}
