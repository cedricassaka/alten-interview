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
    ConfirmDialogModule
  ],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  confirmationService = inject(ConfirmationService);
  messageService = inject(MessageService);

  public products: Product[] = [];

  public isDialogVisible = false;
  public isCreation = false;
  public totalElements = 0;
  public page = 0;
  public first = 0;
  public size = 10;


  ngOnInit() {
    this.load(true);
  }


  load(initial: boolean): void {
    this.productsService.get(this.page, this.size).subscribe({
      next: (response) => {
        if (initial) this.totalElements = response.page.totalElements;
        this.products = response.content;
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

  public onPageChange(pageevent: PaginatorState) {
    this.page = pageevent.page!;
    this.size = pageevent.rows!;
    this.load(false)
  }
}
