import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { Subject, Observable, map } from "rxjs";
import { Cart } from "./cart.model";

@Injectable({
    providedIn: "root"
}) export class CartService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/carts/content";

    public cart = new Subject<Cart>(); 


    public get(): Observable<Cart> {
        return this.http.get<Cart>(`${this.path}`).pipe(
            map((data) => {
                this.cart.next(data);
                return data
            })
        );
    }

    public addProduct(product: Product, quantity: number): Observable<Cart> {
        return this.http.post<Cart>(this.path, {product, quantity}).pipe(
            map((data) => {
                this.cart.next(data);
                return data
            })
        );
    }

    public removeProduct(product: Product): Observable<Cart> {
        return this.http.put<Cart>(`${this.path}/remove`, {id: product.id}).pipe(
            map((data) => {
                this.cart.next(data);
                return data
            })
        );
    }
}