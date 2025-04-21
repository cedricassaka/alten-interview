import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { CartItem } from 'app/cart/data-access/cart-item.model';
import { CartService } from 'app/cart/data-access/cart.service';
import { Product } from 'app/products/data-access/product.model';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DataViewModule } from 'primeng/dataview';
import { PaginatorModule } from 'primeng/paginator';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-cart-content',
  standalone: true,
  imports: [
    CardModule,
    DataViewModule,
    PaginatorModule,
    ButtonModule,
    CommonModule,
    ToastModule
  ],
  templateUrl: './cart-content.component.html',
  styleUrl: './cart-content.component.scss'
})
export class CartContentComponent implements OnInit {

  messageService = inject(MessageService);
  cartService: CartService = inject(CartService);
  loading = false;
  cartItems: CartItem[] = [];

  ngOnInit(): void {
    this.cartService.get().subscribe({
      next: (value) => {
        this.cartItems = value ? value.items : [];
      }
    })

    this.cartService.cart.subscribe({
      next: (value) => {
        this.cartItems = value ? value.items : [];
      }
    })
  }


  public removeFromCart(item: CartItem) {
    this.cartService.removeProduct(item.product).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: '', detail: 'Produit retiré du panier', life: 3000 });
      }
    })
  }

}
