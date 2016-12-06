import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './components/app.component';
import { GerarComponent } from './components/gerar.component';
import { TabelaService } from './services/tabela.service';

@NgModule({
  imports:      [ BrowserModule, FormsModule ],
  declarations: [ AppComponent, GerarComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
