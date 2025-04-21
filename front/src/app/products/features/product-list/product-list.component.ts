import { Component, OnInit, inject, signal } from "@angular/core";
import { emptyProduct, Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { PaginatorModule, PaginatorState } from 'primeng/paginator';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CommonModule } from "@angular/common";
import { TagModule } from 'primeng/tag';
import { CartService } from "app/cart/data-access/cart.service";
import { WishListService } from "app/cart/data-access/wish-list.service";
import { WishList } from "app/cart/data-access/wish-list.model";
import { Cart } from "app/cart/data-access/cart.model";


@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [
    DataViewModule, 
    CardModule, 
    ButtonModule, 
    DialogModule, 
    ProductFormComponent, 
    PaginatorModule, 
    ToastModule,
    ConfirmDialogModule,
    CommonModule,
    TagModule
    
  ],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);
  private readonly wishListService: WishListService = inject(WishListService);

  confirmationService = inject(ConfirmationService);
  messageService = inject(MessageService);

  public products: Product[] = [];

  public isDialogVisible = false;
  public isCreation = false;
  public totalElements = 0;
  public page = 0;
  public size = 5;
  public loading = false;

  wishList: WishList | null = null;
  cart: Cart | null = null;


  ngOnInit() {
    this.load(true);

    this.wishListService.wishList.subscribe({
      next: (value) => this.wishList = value
    })

    this.cartService.cart.subscribe({
      next: (value) => this.cart = value
    })
  }


  load(initial: boolean): void {
    this.productsService.get(this.page, this.size).subscribe({
      next: (response) => {
        if (initial) this.totalElements = response.page.totalElements;
        this.products = (response.content  as Product[]);
      }
    });
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.productsService.editedProduct.next(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.productsService.editedProduct.next(product);
  }

  public onDelete(product: Product) {
    this.confirmationService.confirm({
      message: 'Voulez vous supprimer?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass:"p-button-danger p-button-text",
      rejectButtonStyleClass:"p-button-text p-button-text",
      acceptIcon:"none",
      rejectIcon:"none",

      accept: () => {
        this.productsService.delete(product.id).subscribe({
          next: () => {
            this.page = 0;
            this.load(true);
            this.messageService.add({ severity: 'success', summary: 'Confirmed', detail: 'Produit supprimé' })
          },
          error: () => this.messageService.add({ severity: 'danger', summary: 'Rejected', detail: 'Echec de la suppression' })
        });
          
      },
      reject: () => {}
    });
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.messageService.add({ severity: 'success', summary: '', detail: 'Nouveau produit ajouté', life: 3000 });
    } else {
      this.messageService.add({ severity: 'success', summary: '', detail: 'Projet modifié avec succès', life: 3000 });
    }
    this.page = 0;
    this.load(true);
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public onPageChange(pageevent: any) {
    this.size = Number(pageevent.rows)
    this.page = pageevent.first / pageevent.rows;
    this.load(pageevent.first === 0);
  }

  getSeverity(product: Product): "success" | "secondary" | "info" | "warning" | "danger" | "contrast" | undefined {
    switch (product.inventoryStatus) {
      case 'INSTOCK':
        return 'success';

      case 'LOWSTOCK':
        return 'warning';

      case 'OUTOFSTOCK':
        return 'danger';

      default:
        return undefined;
    }
  };

  checkProductInWishList(product: Product): boolean {
    if (this.wishList === null) return false;
    return this.wishList.products.find(x => x.id === product.id) !== undefined;
  }

  public addToWishList(product: Product) {
    this.wishListService.addProduct(product).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: '', detail: 'Produit enrigistré dans la liste', life: 3000 });
      }
    })
  }

  public removeFromWishList(product: Product) {
    this.wishListService.removeProduct(product).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: '', detail: 'Produit retiré de la liste', life: 3000 });
      }
    })
  }

  wishListAction(product: Product) {
    if (this.checkProductInWishList(product)) {
      this.removeFromWishList(product)
      return;
    }
    this.addToWishList(product);
  }

  public addToCart(product: Product) {
    this.cartService.addProduct(product, this.getProductQuantityInCart(product) + 1).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: '', detail: 'Produit ajouté au panier', life: 3000 });
      }
    })
  }

  getProductQuantityInCart(product: Product): number {
    if (this.cart === null) return 0;
    const item = this.cart.items.find(x => x.product.id === product.id);
    if (item === undefined) return 0;
    return item.quantity;
  }
}
