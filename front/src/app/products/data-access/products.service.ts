import { Injectable, inject, signal } from "@angular/core";
import { emptyProduct, Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, map, Observable, of, Subject, tap } from "rxjs";
import { PageResponse } from "./page-response.model";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/products";

    public editedProduct = new Subject<Product>()

    public get(page: number, size: number): Observable<PageResponse> {
        return this.http.get<PageResponse>(`${this.path}?page=${page}&size=${size}`);
    }

    public create(product: Product): Observable<Product> {
        return this.http.post<Product>(this.path, product);
    }

    public update(product: Product): Observable<Product> {
        return this.http.patch<Product>(`${this.path}/${product.id}`, product);
    }

    public delete(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`);
    }
}