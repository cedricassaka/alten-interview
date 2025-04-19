import { HttpErrorResponse, HttpEventType, HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, filter, tap, throwError } from 'rxjs';

export const unauthorizedInterceptor: HttpInterceptorFn = (req, next) => {
  let router = inject(Router);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('Erreur de la requête:', error);
      if (error.status === 401 && req.url.includes("/api/")) {
        localStorage.clear()
        router.navigate(["/auth/login"]);
      }
      return throwError(error);
    })
  );
}