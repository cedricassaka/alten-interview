import {
  Component,
  computed,
  effect,
  EventEmitter,
  inject,
  input,
  OnInit,
  Output,
  ViewEncapsulation,
} from "@angular/core";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { emptyProduct, Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { MessageService, SelectItem } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";
import { InputNumberModule } from "primeng/inputnumber";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RatingModule } from 'primeng/rating';

@Component({
  selector: "app-product-form",
  templateUrl: './product-form.component.html',
  styleUrls: ["./product-form.component.scss"],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    InputTextareaModule,
    DropdownModule,
    RatingModule,
  ],
  encapsulation: ViewEncapsulation.None
})
export class ProductFormComponent implements OnInit {



  @Output() cancel = new EventEmitter<void>();
  @Output() save = new EventEmitter<Product>();

  fb = inject(FormBuilder);
  productService = inject(ProductsService);
  messageService = inject(MessageService)

  form: FormGroup = this.fb.group({
    internalReference: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    category: new FormControl(''),
    image: new FormControl('', ),
    price: new FormControl(0, [Validators.required, Validators.min(1)]),
    quantity: new FormControl(0, [Validators.required, Validators.min(0)]),
    rating: new FormControl(0, [Validators.required, Validators.min(0.0), Validators.max(5.0)]),
  });

  product = Object.assign(emptyProduct, {});


  loading: boolean = false;

  public readonly categories: SelectItem[] = [
    { value: "Accessories", label: "Accessories" },
    { value: "Fitness", label: "Fitness" },
    { value: "Clothing", label: "Clothing" },
    { value: "Electronics", label: "Electronics" },
  ];


  ngOnInit(): void {
    this.productService.editedProduct.subscribe({
      next: (value) => {
        this.product = value;
        this.form.patchValue({
          ...this.product
        })
      }
    });
  }

  onCancel() {
    this.cancel.emit();
  }

  onSave() {
    this.loading = true;
    if (this.product.id !== 0) {
      this.productService.update({
        ...this.form.value, id: this.product.id
      }).subscribe({
        next: (resp) => {
          this.save.emit(resp);
        },
        error: () => {
          this.loading = false
          this.messageService.add({ severity: 'danger', summary: '', detail: 'Ajout du produit echoué', life: 3000 });
        },
        complete: () => this.loading = false
      })
    } else {
      this.productService.create({
        ...this.form.value
      }).subscribe({
        next: (resp) => {
          this.save.emit(resp);
        },
        error: () => {
          this.loading = false
          this.messageService.add({ severity: 'danger', summary: '', detail: 'Ajout du produit echoué', life: 3000 });
        },
        complete: () => this.loading = false
      })
    }
    // ;
  }
}
