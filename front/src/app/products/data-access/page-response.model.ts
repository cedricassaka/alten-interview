import { CartItem } from "app/cart/data-access/cart-item.model";
import { Product } from "./product.model";

export interface PageResponse {
    content: Product[] | CartItem[],
    page: {
        size: number;
        number: number,
        totalElements: number,
        totalPages: number
    }
}