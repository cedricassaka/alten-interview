import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { Subject, Observable, map } from "rxjs";
import { WishList } from "./wish-list.model";

@Injectable({
    providedIn: "root"
}) export class WishListService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/wish-lists/content";

    public wishList = new Subject<WishList>(); 


    public get(): Observable<WishList> {
        return this.http.get<WishList>(`${this.path}`).pipe(
            map((data) => {
                this.wishList.next(data);
                return data
            })
        )
    }

    public addProduct(product: Product): Observable<WishList> {
        return this.http.post<WishList>(this.path, {id: product.id}).pipe(
            map((data) => {
                this.wishList.next(data);
                return data
            })
        );
    }

    public removeProduct(product: Product): Observable<WishList> {
        return this.http.put<WishList>(`${this.path}/remove`, {id: product.id}).pipe(
            map((data) => {
                this.wishList.next(data);
                return data
            })
        )
    }
}