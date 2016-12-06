import { Component } from '@angular/core';
import { GerarComponent } from './gerar.component'
import { BuscarComponent } from './buscar.component'
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
    indices: Tabela[];

    constructor(private tabelaService: TabelaService) {
        
    }
    
    mudarTela(id: Number): void{
        this.tela = id;
        if (this.tela === 1) {
            this.getTabelas();
        }
        if (this.tela === 2) {
            this.getIndices();
        }
    }

    ngOnInit(): void{
        this.getTabelas();
        this.getIndices();
    }

    getTabelas(): void {
        this.tabelaService.getTabelas(0).then(tabelas => this.tabelas = tabelas);
    }

    getIndices(): void {
        this.tabelaService.getTabelas(1).then(tabelas => this.tabelas = tabelas);
    }
    
}