import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from "primeng/button";
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputTextModule } from 'primeng/inputtext';
import { AccountService } from 'app/auth/data-access/account.service';
import { MessageModule } from 'primeng/message';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CardModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextareaModule,
    InputTextModule,
    MessageModule, 
    RouterModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  fb = inject(FormBuilder);
  accountService = inject(AccountService)
  router = inject(Router)
  
  form: FormGroup = this.fb.group({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.min(300)])
  })

  loading: boolean = false;
  

  onSubmit() {
    if (!this.form.valid) return;
    this.loading = true;
    this.accountService.login(this.form.value).subscribe({
      next: (resp) => {
        this.router.navigate(["/admin/home"])
      },
      error: (error) =>  {
        this.loading = false;
      },
      complete: () => this.loading = false
    }) 
  }
}
