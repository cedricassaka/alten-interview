import { Component } from '@angular/core';
import { RouterModule } from "@angular/router";
import { PanelMenuComponent } from 'app/shared/ui/panel-menu/panel-menu.component';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {
  title = "ALTEN SHOP";
}
