import { Injectable, inject } from "@angular/core";
import { HttpClient  } from "@angular/common/http";
import { TokenInfo } from "./token-info.model";
import { map, Observable } from "rxjs";


@Injectable({
    providedIn: "root"
}) export class AccountService {

    private readonly http = inject(HttpClient);
    private readonly path = "/token";


    public login(data: any): Observable<TokenInfo> {
        return this.http.post<TokenInfo>(this.path, data).pipe(
            map((res) => {
                localStorage.setItem("token", res.token)
                return res;
            })
        );
    }



    public authenticated(): boolean {
        return localStorage.getItem('token') !== null ;
    }

}