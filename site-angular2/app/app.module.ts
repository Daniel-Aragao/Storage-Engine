import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppComponent } from './components/app.component';
import { GerarComponent } from './components/gerar.component';
import { BuscarComponent } from './components/buscar.component';
import { TabelaService } from './services/tabela.service';

@NgModule({
  imports:      [ BrowserModule, FormsModule, HttpModule ],
  declarations: [AppComponent, GerarComponent, BuscarComponent],
  providers: [ TabelaService ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
