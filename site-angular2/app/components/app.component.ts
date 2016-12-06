import { Component } from '@angular/core';
import { GerarComponent } from './gerar.component'
import { TabelaService } from '../services/tabela.service'
import { OnInit } from '@angular/core';
import { Tabela } from '../Objects/Tabela'

@Component({
    selector: 'my-app',
    providers: [TabelaService],
    templateUrl: 'app/components/htmls/app.component.html',
    styleUrls:['app/components/css/app.component.css']
})

export class AppComponent implements OnInit {
    tela: Number = 1;
    tabelas: Tabela[];

    constructor(private tabelaService: TabelaService) {
    
    }
    
    mudarTela(id: Number): void{
        this.tela = id;
    }

    ngOnInit(): void{
        this.getTabelas();
    }

    getTabelas(): void {
        this.tabelaService.getTabelas().then(tabelas => this.tabelas = tabelas);
    }
    
}