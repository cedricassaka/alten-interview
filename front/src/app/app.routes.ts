import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { LoginComponent } from "./auth/features/login/login.component";
import { MainComponent } from "./main/main.component";
import { authGuard } from "./auth/auth.guard";

export const APP_ROUTES: Routes = [
  {
    path: "auth/login",
    component: LoginComponent
  },
  {
    path: "admin",
    component: MainComponent,
    canActivate: [authGuard],
    children: [
      {
        path: "home",
        component: HomeComponent,
      },
      {
        path: "products",
        loadChildren: () =>
          import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
      },
      {
        path: "contact",
        loadChildren: () =>
          import("./contact/contact.routes").then((m) => m.CONTAC_ROUTES)
      },
    ]
  },
  { path: "", redirectTo: "admin/home", pathMatch: "full" },
];
