import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from "@angular/router";
import { PanelMenuComponent } from 'app/shared/ui/panel-menu/panel-menu.component';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { BadgeModule } from 'primeng/badge';
import { CartService } from 'app/cart/data-access/cart.service';
import { WishListService } from 'app/cart/data-access/wish-list.service';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit {
  
  cartService: CartService = inject(CartService);
  wishListService: WishListService = inject(WishListService);
  title = "ALTEN SHOP";
  cartSize = 0;

  ngOnInit(): void {
    this.wishListService.get().subscribe();
    this.cartService.get().subscribe();
    
    this.cartService.cart.subscribe({
      next: (data) => {
        this.cartSize = data.items.length;
      }
    })
  }
}
