import { Product } from "./product.model";

export interface PageResponse {
    content: Product[],
    page: {
        size: number;
        number: number,
        totalElements: number,
        totalPages: number
    }
}