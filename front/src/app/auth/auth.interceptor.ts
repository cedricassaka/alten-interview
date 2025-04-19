import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  
  if (req.url.includes("/api/") && localStorage.getItem("token")) {
    const request = req.clone({
      headers: req.headers.append('Authorization', `Bearer ${localStorage.getItem("token")}`)
    });

    return next(request);
  }

  return next(req);
};
